package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.Review
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

@Repository
interface ReviewRepository : JpaRepository<Review, String> {

    fun findByMovieId(movieId: String, pageable: Pageable): Page<Review>

}