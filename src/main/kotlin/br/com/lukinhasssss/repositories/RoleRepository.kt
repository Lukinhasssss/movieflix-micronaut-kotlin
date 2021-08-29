package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.Role
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, String> {

    fun findByName(name: String): Optional<Role>

}