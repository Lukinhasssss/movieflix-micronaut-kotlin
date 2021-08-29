package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.User
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, String> {

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun findByUsername(username: String): Optional<User>

}