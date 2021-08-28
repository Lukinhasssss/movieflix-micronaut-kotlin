package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.Movie
import io.micronaut.data.jpa.repository.JpaRepository

interface MovieRepository : JpaRepository<Movie, String>