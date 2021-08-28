package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.Review
import io.micronaut.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, String>