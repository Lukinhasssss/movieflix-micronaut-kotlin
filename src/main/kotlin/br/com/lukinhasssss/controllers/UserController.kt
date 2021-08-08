package br.com.lukinhasssss.controllers

import br.com.lukinhasssss.config.security.BCryptPasswordEncoder
import br.com.lukinhasssss.dto.request.NewUserRequest
import br.com.lukinhasssss.dto.response.UserResponse
import br.com.lukinhasssss.repositories.UserRepository
import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
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
    fun findAll(): List<UserResponse> {
        return userRepository.findAll().map {
            user -> UserResponse(user)
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