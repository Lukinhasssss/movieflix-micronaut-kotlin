package br.com.lukinhasssss.dto.request

import br.com.lukinhasssss.entities.Movie
import br.com.lukinhasssss.repositories.GenreRepository
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class NewMovieRequest(

    @field:NotBlank(message = "Campo obrigatório!")
    val title: String,

    @field:NotBlank(message = "Campo obrigatório!")
    val subtitle: String,

    @field:NotNull(message = "Campo obrigatório!")
    val year: Int,

    @field:NotBlank(message = "Campo obrigatório!")
    val imgUrl: String,

    @field:NotBlank(message = "Campo obrigatório!")
    val synopsis: String,

    @field:NotBlank(message = "Campo obrigatório!")
    val genre: String

) {

    fun toEntity(genreRepository: GenreRepository): Optional<Movie> {
        val genre = genreRepository.findByName(genre)
        if (genre.isEmpty)
            return Optional.empty()

        return Optional.of(Movie(
            title = title,
            subtitle = subtitle,
            year = year,
            imgUrl = imgUrl,
            synopsis = synopsis,
            genre = genre.get()
        ))
    }

}