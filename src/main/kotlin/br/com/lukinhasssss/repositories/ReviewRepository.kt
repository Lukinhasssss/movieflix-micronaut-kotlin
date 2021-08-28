package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.Review
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ReviewRepository : JpaRepository<Review, String>