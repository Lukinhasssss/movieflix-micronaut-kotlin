package br.com.lukinhasssss.dto.request

import br.com.lukinhasssss.config.security.BCryptPasswordEncoder
import br.com.lukinhasssss.config.validations.CheckIfExists
import br.com.lukinhasssss.entities.User
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class NewUserRequest(

    @field:NotBlank(message = "username cannot be empty or blank")
    @field:CheckIfExists(domainClass = User::class, fieldName = "username", message = "there is already a user with this username")
    val username: String,

    @field:NotBlank(message = "email cannot be empty or blank")
    @field:CheckIfExists(domainClass = User::class, fieldName = "email", message = "there is already a user with this email")
    val email: String,

    @field:NotBlank(message = "password cannot be empty or blank")
    @field:Size(min = 8, message = "password need to have at least 8 characters")
    val password: String

) {
    fun convertToUser(passwordEncoder: BCryptPasswordEncoder): User {
        return User(
            username = username,
            email = email,
            password = passwordEncoder.encode(password)
        )
    }

}
