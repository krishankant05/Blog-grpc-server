package com.example.blog_grpc.comment

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "comments")
data class Comment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    val postId: Long,
    var authorId: Long,
    @Lob
    @Column(columnDefinition = "TEXT")
    var content: String,
    var createdAt: Instant = Instant.now(),
    var deleted: Boolean = false
)