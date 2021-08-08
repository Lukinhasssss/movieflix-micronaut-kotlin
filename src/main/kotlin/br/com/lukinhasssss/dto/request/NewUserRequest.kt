package br.com.lukinhasssss.dto.request

import br.com.lukinhasssss.config.security.BCryptPasswordEncoder
import br.com.lukinhasssss.config.validations.UserExists
import br.com.lukinhasssss.entities.User
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class NewUserRequest(

    @NotBlank(message = "username cannot be empty or blank")
    @UserExists(message = "there is already a user with this username")
    val username: String,

    @NotBlank(message = "email cannot be empty or blank")
    @UserExists(message = "there is already a user with this email")
    val email: String,

    @NotBlank(message = "password cannot be empty or blank")
    @Size(min = 8, message = "password need to have at least 8 characters")
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
