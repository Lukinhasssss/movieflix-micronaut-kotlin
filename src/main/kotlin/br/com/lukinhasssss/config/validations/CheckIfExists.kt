package br.com.lukinhasssss.config.validations

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@MustBeDocumented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = [CheckIfExistsValidator::class])
annotation class CheckIfExists(

    val domainClass: KClass<*>,
    val fieldName: String,
    val message: String = "O valor passado não é válido",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []

)

@Singleton
open class CheckIfExistsValidator(val em: EntityManager) : ConstraintValidator<CheckIfExists, Any> {

    private lateinit var domainClass: String
    private lateinit var fieldName: String

    override fun initialize(constraintAnnotation: CheckIfExists?) {
        domainClass = constraintAnnotation!!.domainClass.simpleName.toString()
        fieldName = constraintAnnotation.fieldName
    }

    @Transactional
    override fun isValid(
        value: Any?,
        annotationMetadata: AnnotationValue<CheckIfExists>,
        context: ConstraintValidatorContext
    ): Boolean {

        return em.createQuery("select 1 from $domainClass where $fieldName = :value")
            .setParameter("value", value)
            .resultList.isEmpty()

//        if (userRepository.existsByEmail(value))
//            return false
//
//        if (userRepository.existsByUsername(value))
//            return false
    }

}