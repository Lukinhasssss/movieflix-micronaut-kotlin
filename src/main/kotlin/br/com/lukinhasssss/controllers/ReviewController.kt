package br.com.lukinhasssss.controllers

import br.com.lukinhasssss.dto.request.NewReviewRequest
import br.com.lukinhasssss.dto.response.ReviewResponse
import br.com.lukinhasssss.repositories.MovieRepository
import br.com.lukinhasssss.repositories.ReviewRepository
import br.com.lukinhasssss.repositories.UserRepository
import io.micronaut.context.annotation.Value
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import java.security.Principal
import javax.annotation.security.RolesAllowed
import javax.validation.Valid

@Validated
@Controller("/movies/{movieId}/reviews")
class ReviewController(
    val movieRepository: MovieRepository,
    val userRepository: UserRepository,
    val reviewRepository: ReviewRepository
) {

    @Value("\${app.url}")
    lateinit var appUrl: String

    @Get
    @RolesAllowed(value = ["VISITOR", "MEMBER", "ADMIN"])
    fun findAll(movieId: String, pageable: Pageable): HttpResponse<Page<ReviewResponse>> {
        val reviews = reviewRepository.findByMovieId(movieId, pageable).map { ReviewResponse(it) }
        return HttpResponse.ok(reviews)
    }

    @Post
    @RolesAllowed(value = ["MEMBER", "ADMIN"])
    fun registry(movieId: String, @Valid @Body request: NewReviewRequest, loggedUser: Principal): HttpResponse<Any> {
        val movie = movieRepository.findById(movieId)
        val user = userRepository.findByUsername(loggedUser.name)

        val review = request.toEntity(movie.get(), user.get())
        reviewRepository.save(review)

        HttpResponse.uri("$appUrl/movies/$movieId/reviews/${review.id}").let {
            return HttpResponse.created(it)
        }
    }

    @Delete("/reviewId")
    @RolesAllowed(value = ["MEMBER", "ADMIN"])
    fun delete(reviewId: String, loggedUser: Principal): HttpResponse<Unit> {
        val review = reviewRepository.findById(reviewId).get()

        if (review.user.username != loggedUser.name)
            return HttpResponse.status(HttpStatus.FORBIDDEN)

        return HttpResponse.noContent()
    }

}