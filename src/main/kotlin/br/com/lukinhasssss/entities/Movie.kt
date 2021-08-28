package br.com.lukinhasssss.entities

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tb_movie")
data class Movie(

    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = null,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val subtitle: String,

    @Column(nullable = false)
    val year: Int,

    @Column(nullable = false)
    val imgUrl: String,

    @Column(nullable = false)
    val synopsis: String,

    @ManyToOne
    val genre: Genre,

    @OneToMany(mappedBy = "movie")
    val reviews: List<Review> = ArrayList<Review>(),

    val createdAt: LocalDateTime = LocalDateTime.now()

)
