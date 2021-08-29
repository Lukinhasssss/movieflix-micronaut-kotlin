package br.com.lukinhasssss.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UpdateMovieRequest(

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
    /* TODO: annotation to validate if genre exists */
    val genre: String

)