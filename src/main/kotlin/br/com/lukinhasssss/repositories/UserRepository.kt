package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.User
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface UserRepository : JpaRepository<User, String> {

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean

}