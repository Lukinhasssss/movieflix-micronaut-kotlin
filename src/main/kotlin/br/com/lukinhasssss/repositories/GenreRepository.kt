package br.com.lukinhasssss.repositories

import br.com.lukinhasssss.entities.Genre
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface GenreRepository : JpaRepository<Genre, String>