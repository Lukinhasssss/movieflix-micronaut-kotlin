package br.com.lukinhasssss.controllers

import br.com.lukinhasssss.dto.request.NewRoleRequest
import br.com.lukinhasssss.dto.request.UpdateGenreRequest
import br.com.lukinhasssss.dto.request.UpdateRoleRequest
import br.com.lukinhasssss.repositories.RoleRepository
import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.annotation.security.PermitAll
import javax.validation.Valid

@Validated
@Controller("/roles")
class RoleController(
    private val roleRepository: RoleRepository
) {

    @Value("\${app.url}")
    lateinit var appUrl: String

    @Get
    fun findAll(): HttpResponse<List<Map<String, String>>> {
        val roles = roleRepository.findAll().map { mapOf(Pair("name", it.name)) }

        return HttpResponse.ok(roles)
    }

    @Get("/{id}")
    fun findById(id: String): HttpResponse<Any> {
        val role = roleRepository.findById(id)

        if (role.isEmpty)
            return HttpResponse.notFound(mapOf(Pair("mensagem", "cargo não encontrado!")))

        return HttpResponse.ok(mapOf(Pair("name", role.get().name)))
    }

    @Post
    @PermitAll
    fun registry(@Valid @Body request: NewRoleRequest): HttpResponse<Unit> {
        val role = request.toEntity()
        roleRepository.save(role)

        HttpResponse.uri("$appUrl/roles/${role.id}").let {
            return HttpResponse.created(it)
        }
    }

    @Put("/{id}")
    fun update(id: String, @Valid @Body request: UpdateRoleRequest): HttpResponse<Any> {
        roleRepository.findById(id).let {
            if (it.isEmpty)
                return HttpResponse.notFound(mapOf(Pair("mensagem", "cargo não encontrado!")))

            val role = it.get().copy(name = request.name)
            roleRepository.update(role)
            return HttpResponse.ok(mapOf(Pair("name", role.name)))
        }
    }

    @Delete("/{id}")
    fun delete(id: String): HttpResponse<Any> {
        roleRepository.findById(id).let {
            if (it.isEmpty)
                return HttpResponse.notFound(mapOf(Pair("mensagem", "cargo não encontrado!")))

            roleRepository.delete(it.get())
            return HttpResponse.noContent()
        }
    }

}