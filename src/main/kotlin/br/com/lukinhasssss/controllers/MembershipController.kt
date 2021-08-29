package br.com.lukinhasssss.controllers

import br.com.lukinhasssss.repositories.UserRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import java.security.Principal
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed

@Validated
@Controller("/users/{id}/membership")
class MembershipController(
    private val userRepository: UserRepository
) {

    @Post
    @RolesAllowed(value = ["VISITOR", "MEMBER", "ADMIN"])
    fun updateMembership(id: String, loggedUser: Principal): HttpResponse<Unit> {
        val user = userRepository.findById(id)

        if (user.get().username != loggedUser.name)
            return HttpResponse.status(HttpStatus.FORBIDDEN)

        val userUpdated = user.get().copy(role = user.get().role.copy(name = "MEMBER"))
        userRepository.update(userUpdated)

        return HttpResponse.ok()
    }

}