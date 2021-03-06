package br.com.lukinhasssss.entities

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tb_review")
data class Review(

    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = null,

    @Column(nullable = false)
    val text: String,

    @ManyToOne
    val user: User,

    @ManyToOne
    val movie: Movie,

    val createdAt: LocalDateTime = LocalDateTime.now()

)
