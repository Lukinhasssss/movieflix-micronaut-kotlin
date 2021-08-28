package br.com.lukinhasssss.controllers

import br.com.lukinhasssss.dto.request.NewGenreRequest
import br.com.lukinhasssss.dto.request.UpdateGenreRequest
import br.com.lukinhasssss.repositories.GenreRepository
import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import java.util.function.Consumer
import javax.validation.Valid

@Validated
@Controller("/genres")
class GenreController(
    private val genreRepository: GenreRepository
) {

    @Value("\${app.url}")
    lateinit var appUrl: String

    @Get
    fun findAll(): HttpResponse<List<Map<String, String>>> {
        val genres = genreRepository.findAll().map { mapOf(Pair("name", it.name)) }

        return HttpResponse.ok(genres)
    }

    @Get("/{id}")
    fun findById(id: String): HttpResponse<Any> {
        val genre = genreRepository.findById(id)

        if (genre.isEmpty)
            return HttpResponse.notFound(mapOf(Pair("mensagem", "gênero não encontrado!")))

        return HttpResponse.ok(mapOf(Pair("name", genre.get().name)))
    }

    @Post
    fun registry(@Valid @Body request: NewGenreRequest): HttpResponse<Unit> {
        val genre = request.toEntity()
        genreRepository.save(genre)

        HttpResponse.uri("$appUrl/genres/${genre.id}").let {
            return HttpResponse.created(it)
        }
    }

    @Put("/{id}")
    fun update(id: String, @Valid @Body request: UpdateGenreRequest): HttpResponse<Any> {
        genreRepository.findById(id).let {
            if (it.isEmpty)
                return HttpResponse.notFound(mapOf(Pair("mensagem", "gênero não encontrado!")))

            val genre = it.get().copy(name = request.name)
            genreRepository.update(genre)
            return HttpResponse.ok(mapOf(Pair("name", genre.name)))
        }
    }

    @Delete("/{id}")
    fun delete(id: String): HttpResponse<Any> {
        genreRepository.findById(id).let {
            if (it.isEmpty)
                return HttpResponse.notFound(mapOf(Pair("mensagem", "gênero não encontrado!")))

            genreRepository.delete(it.get())
            return HttpResponse.noContent()
        }
    }

}