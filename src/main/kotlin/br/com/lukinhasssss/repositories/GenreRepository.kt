package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.Genre
import io.micronaut.data.jpa.repository.JpaRepository

interface GenreRepository : JpaRepository<Genre, String>