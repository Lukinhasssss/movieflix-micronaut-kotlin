package br.com.lukinhasssss.dto.request

import br.com.lukinhasssss.config.validations.CheckIfExists
import br.com.lukinhasssss.entities.User
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class UpdateUserRequest(

    @field:NotBlank(message = "Campo obrigatório!")
    val role: String

)
