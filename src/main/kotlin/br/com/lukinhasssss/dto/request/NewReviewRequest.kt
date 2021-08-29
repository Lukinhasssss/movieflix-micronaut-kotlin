package br.com.lukinhasssss.dto.request

import br.com.lukinhasssss.entities.Movie
import br.com.lukinhasssss.entities.Review
import br.com.lukinhasssss.entities.User
import javax.validation.constraints.NotBlank

data class NewReviewRequest(

    @field:NotBlank
    val text: String

) {
    fun toEntity(movie: Movie, user: User): Review {
        return Review(
            text = text,
            user = user,
            movie = movie
        )
    }


}