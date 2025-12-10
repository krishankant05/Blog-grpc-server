package com.example.blog_grpc.user

interface UserService{
    fun createUser(username: String, email: String, passwordHash: String): User
    fun findById(id: Long): User?
    fun findByUsername(username: String): User?
}
