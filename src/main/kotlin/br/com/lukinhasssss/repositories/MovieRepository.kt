package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.Movie
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import java.util.*

@Repository
interface MovieRepository : JpaRepository<Movie, String> {

    override fun findAll(pageable: Pageable): Page<Movie>

    fun findByGenreNameContains(genre: String, pageable: Pageable): Page<Movie>

}