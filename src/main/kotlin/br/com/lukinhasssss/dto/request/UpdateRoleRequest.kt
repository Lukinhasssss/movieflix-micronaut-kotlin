package br.com.lukinhasssss.dto.request

import br.com.lukinhasssss.config.validations.CheckIfExists
import br.com.lukinhasssss.entities.Genre
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class UpdateRoleRequest(

    @field:NotBlank(message = "Campo obrigatório!")
    @CheckIfExists(Genre::class, "name", "Já existe um cargo com este nome!")
    val name: String

)