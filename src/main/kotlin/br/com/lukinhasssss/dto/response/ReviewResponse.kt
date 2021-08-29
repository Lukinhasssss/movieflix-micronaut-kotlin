package br.com.lukinhasssss.dto.response

import br.com.lukinhasssss.entities.Review

data class ReviewResponse(

    val username: String,
    val text: String,

) {

    constructor(review: Review) : this(
        username = review.user.username,
        text = review.text
    )

}
