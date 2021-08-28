package br.com.lukinhasssss.config.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.inject.Singleton
import javax.validation.constraints.NotBlank


@Singleton
class BCryptPasswordEncoder : PasswordEncoder {

    private val passwordEncoder = BCryptPasswordEncoder()

    override fun encode(rawPassword: @NotBlank CharSequence): String {
        return passwordEncoder.encode(rawPassword)
    }

    override fun matches(
        rawPassword: @NotBlank CharSequence,
        encodedPassword: @NotBlank String
    ): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }

}