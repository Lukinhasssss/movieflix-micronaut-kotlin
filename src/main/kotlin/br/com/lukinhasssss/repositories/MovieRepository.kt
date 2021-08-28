package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.Movie
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface MovieRepository : JpaRepository<Movie, String>