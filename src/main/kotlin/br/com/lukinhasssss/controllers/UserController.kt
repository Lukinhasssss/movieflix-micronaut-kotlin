package br.com.lukinhasssss.controllers

import br.com.lukinhasssss.config.security.BCryptPasswordEncoder
import br.com.lukinhasssss.dto.request.NewUserRequest
import br.com.lukinhasssss.dto.response.UserResponse
import br.com.lukinhasssss.repositories.UserRepository
import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/users")
class UserController(
    val userRepository: UserRepository,
    val passwordEncoder: BCryptPasswordEncoder
) {

    @Value("\${app.url}")
    lateinit var appUrl: String

    @Get
    fun findAll(): HttpResponse<List<UserResponse>> {
        val users = userRepository.findAll().map { user -> UserResponse(user) }
        return HttpResponse.ok(users)
    }

    @Get("/{id}")
    fun findById(@PathVariable id: String): HttpResponse<Any> {
        userRepository.findById(id).let {
            if (it.isEmpty)
                return HttpResponse.notFound(mapOf(Pair("message", "user not found")))
            return HttpResponse.ok(UserResponse(it.get()))
        }
    }

    @Post
    fun registry(@Valid @Body request: NewUserRequest): HttpResponse<Unit> {
        request.convertToUser(passwordEncoder).let {
            userRepository.save(it)
            val uri = UriBuilder.of("$appUrl/autores/{id}").expand(mutableMapOf(Pair("id", it.id)))
            return HttpResponse.created(uri)
        }
    }

}