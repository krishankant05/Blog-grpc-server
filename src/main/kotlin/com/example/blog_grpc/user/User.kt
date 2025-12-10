package com.example.blog_grpc.user

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var username: String,
    var email: String,
    var passwordHash: String,
    var createdAt: Instant = Instant.now()
)