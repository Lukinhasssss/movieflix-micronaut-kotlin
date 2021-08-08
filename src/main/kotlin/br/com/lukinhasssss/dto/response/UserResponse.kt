package br.com.lukinhasssss.dto.response

import br.com.lukinhasssss.entities.User

data class UserResponse(
    val username: String,
    val email: String
) {

    constructor(user: User): this(
        username = user.username,
        email = user.email
    )

}