package br.com.lukinhasssss.controllers

import br.com.lukinhasssss.config.security.BCryptPasswordEncoder
import br.com.lukinhasssss.dto.request.NewUserRequest
import br.com.lukinhasssss.dto.request.UpdateUserRequest
import br.com.lukinhasssss.dto.response.UserResponse
import br.com.lukinhasssss.repositories.RoleRepository
import br.com.lukinhasssss.repositories.UserRepository
import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/users")
class UserController(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val passwordEncoder: BCryptPasswordEncoder
) {

    @Value("\${app.url}")
    lateinit var appUrl: String

    @Get
    @RolesAllowed(value = ["ADMIN"])
    fun findAll(): HttpResponse<List<UserResponse>> {
        val users = userRepository.findAll().map { user -> UserResponse(user) }
        return HttpResponse.ok(users)
    }

    @Get("/{id}")
    @RolesAllowed(value = ["ADMIN"])
    fun findById(@PathVariable id: String): HttpResponse<Any> {
        userRepository.findById(id).let {
            if (it.isEmpty)
                return HttpResponse.notFound(mapOf(Pair("mensagem", "usuário não encontrado!")))
            return HttpResponse.ok(UserResponse(it.get()))
        }
    }

    @Post
    @PermitAll
    fun registry(@Valid @Body request: NewUserRequest): HttpResponse<Unit> {
        request.toEntity(passwordEncoder, roleRepository).let {
            userRepository.save(it)
            val uri = HttpResponse.uri("$appUrl/users/${it.id}")
            return HttpResponse.created(uri)
        }
    }

    @Put("/{id}")
    @RolesAllowed(value = ["ADMIN"])
    fun update(@PathVariable id: String, @Valid @Body request: UpdateUserRequest): HttpResponse<Any> {
        userRepository.findById(id).let {
            if (it.isEmpty)
                return HttpResponse.notFound(mapOf(Pair("mensagem", "usuário não encontrado!")))
            val user = it.get().copy(role = it.get().role.copy(name = request.role))
            userRepository.update(it.get())
            return HttpResponse.ok(UserResponse(it.get()))
        }
    }

    @Delete("/{id}")
    @RolesAllowed(value = ["ADMIN"])
    fun delete(@PathVariable id: String): HttpResponse<Any> {
        userRepository.findById(id).let {
            if (it.isEmpty)
                return HttpResponse.notFound(mapOf(Pair("mensagem", "usuário não encontrado!")))
            userRepository.deleteById(id)
            return HttpResponse.noContent()
        }
    }

}