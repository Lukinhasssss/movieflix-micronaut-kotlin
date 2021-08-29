package br.com.lukinhasssss.config.security

import br.com.lukinhasssss.repositories.UserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import javax.inject.Singleton
import javax.validation.constraints.NotBlank

@Singleton
class UserAuthenticationProvider(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : AuthenticationProvider {

    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>
    ): Publisher<AuthenticationResponse> {
        val user = userRepository.findByUsername(authenticationRequest.identity as @NotBlank String)

        if (user.isPresent && bCryptPasswordEncoder.matches(authenticationRequest.secret.toString(), user.get().password))
            return Flowable.just(UserDetails(user.get().username, listOf(user.get().role.name))) // second parameter is the roles list of user

        return Flowable.error(AuthenticationException(AuthenticationFailed(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)))
//        return Flowable.error(AuthenticationException(AuthenticationFailed("Usuário ou senha inválidos!")))
    }

}