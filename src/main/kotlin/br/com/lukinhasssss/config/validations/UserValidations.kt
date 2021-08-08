package br.com.lukinhasssss.config.validations

import br.com.lukinhasssss.repositories.UserRepository
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD

@MustBeDocumented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = [UserExistsValidator::class])
annotation class UserExists(
    val message: String = "O valor passado não é válido"
)

@Singleton
class UserExistsValidator : ConstraintValidator<UserExists, String> {

    @Inject
    lateinit var userRepository: UserRepository

    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<UserExists>,
        context: ConstraintValidatorContext
    ): Boolean {
        if (value == null) return true

        if (userRepository.existsByEmail(value))
            return false

        if (userRepository.existsByUsername(value))
            return false

        return true
    }

}