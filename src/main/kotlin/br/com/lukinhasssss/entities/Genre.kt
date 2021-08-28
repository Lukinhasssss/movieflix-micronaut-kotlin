package br.com.lukinhasssss.entities

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tb_genre")
data class Genre(

    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = null,

    @Column(nullable = false, unique = true)
    val name: String,

    val createdAt: LocalDateTime = LocalDateTime.now()

)
