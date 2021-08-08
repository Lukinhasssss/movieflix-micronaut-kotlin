package br.com.lukinhasssss.dto.request

import br.com.lukinhasssss.config.validations.UserExists
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class UpdateUserRequest(

    @NotBlank(message = "username cannot be empty or blank")
    @UserExists(message = "there is already a user with this username")
    val username: String

)
