package br.com.lukinhasssss.dto.response

import br.com.lukinhasssss.entities.Movie

data class MovieResponse(

    val title: String,

    val subtitle: String,

    val year: Int,

    val imgUrl: String,

    val synopsis: String,

    val genre: String

) {

    constructor(movie: Movie) : this(
        title = movie.title,
        subtitle = movie.subtitle,
        year = movie.year,
        imgUrl = movie.imgUrl,
        synopsis = movie.synopsis,
        genre = movie.genre.name
    )

}