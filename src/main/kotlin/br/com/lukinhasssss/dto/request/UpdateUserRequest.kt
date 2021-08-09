package br.com.lukinhasssss.dto.request

import br.com.lukinhasssss.config.validations.CheckIfExists
import br.com.lukinhasssss.entities.User
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class UpdateUserRequest(

    @NotBlank(message = "username cannot be empty or blank")
    @field:CheckIfExists(domainClass = User::class, fieldName = "username", message = "there is already a user with this username")
    val username: String

)
