package com.example.blog_grpc.post

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "posts")
data class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var title: String,
    @Lob
    @Column(columnDefinition = "TEXT")
    var content: String,
    var authorId: Long,
    var published: Boolean = false,
    var viewCount: Long = 0,
    var createdAt: Instant = Instant.now(),
    var updatedAt: Instant = Instant.now()
)