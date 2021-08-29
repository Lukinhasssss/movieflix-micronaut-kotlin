package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.Genre
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface GenreRepository : JpaRepository<Genre, String> {

    fun findByName(genre: String): Optional<Genre>

}