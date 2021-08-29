package br.com.lukinhasssss.controllers

import br.com.lukinhasssss.dto.request.NewMovieRequest
import br.com.lukinhasssss.dto.request.UpdateMovieRequest
import br.com.lukinhasssss.dto.response.MovieResponse
import br.com.lukinhasssss.repositories.GenreRepository
import br.com.lukinhasssss.repositories.MovieRepository
import io.micronaut.context.annotation.Value
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.annotation.security.RolesAllowed
import javax.validation.Valid

@Validated
@Controller("/movies")
class MovieController(
    private val movieRepository: MovieRepository,
    private val genreRepository: GenreRepository
) {

    @Value("\${app.url}")
    lateinit var appUrl: String

    @Get
    @RolesAllowed(value = ["VISITOR", "MEMBER", "ADMIN"])
    fun findAll(pageable: Pageable): HttpResponse<Page<MovieResponse>> {
        val movies = movieRepository.findAll(pageable).map { movie ->  MovieResponse(movie) }
        return HttpResponse.ok(movies)
    }

    @Get("/{id}")
    @RolesAllowed(value = ["VISITOR", "MEMBER", "ADMIN"])
    fun findById(id: String): HttpResponse<Any> {
        val movie = movieRepository.findById(id)

        if (movie.isEmpty)
            return HttpResponse.notFound(mapOf(Pair("mensagem", "filme não encontrado!")))

        return HttpResponse.ok(MovieResponse(movie.get()))
    }

    @Post
    @RolesAllowed(value = ["ADMIN"])
    fun registry(@Valid @Body request: NewMovieRequest): HttpResponse<Any> {
        val movie = request.toEntity(genreRepository)
        if (movie.isEmpty)
            return HttpResponse.badRequest(mapOf(Pair("genre", "Gênero inválido!")))

        movieRepository.save(movie.get())
        HttpResponse.uri("$appUrl/movies/${ movie.get().id }").let {
            return HttpResponse.created(it)
        }
    }

    @Put("/{id}")
    @RolesAllowed(value = ["ADMIN"])
    fun update(id: String, @Valid @Body request: UpdateMovieRequest): HttpResponse<Any> {
        movieRepository.findById(id).let {
            if (it.isEmpty)
                return HttpResponse.notFound(mapOf(Pair("mensagem", "filme não encontrado!")))

            val movie = it.get().copy(
                title = request.title,
                subtitle = request.subtitle,
                year = request.year,
                imgUrl = request.imgUrl,
                synopsis = request.synopsis,
                genre = it.get().genre.copy(name = request.genre),
            )
            movieRepository.update(movie)
            return HttpResponse.ok(MovieResponse(movie))
        }
    }

    @Delete("/{id}")
    @RolesAllowed(value = ["ADMIN"])
    fun delete(id: String): HttpResponse<Any> {
        movieRepository.findById(id).let {
            if (it.isEmpty)
                return HttpResponse.notFound(mapOf(Pair("mensagem", "filme não encontrado!")))

            movieRepository.delete(it.get())
            return HttpResponse.noContent()
        }
    }

}