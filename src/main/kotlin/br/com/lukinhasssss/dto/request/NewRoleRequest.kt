package br.com.lukinhasssss.dto.request

import br.com.lukinhasssss.config.validations.CheckIfExists
import br.com.lukinhasssss.entities.Role
import javax.validation.constraints.NotBlank

data class NewRoleRequest(

    @field:NotBlank(message = "Campo obrigatório!")
    @CheckIfExists(Role::class, "name", "Já existe um cargo com este nome!")
    val name: String

) {

    fun toEntity(): Role {
        return Role(name = name)
    }

}