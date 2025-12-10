package com.example.blog_grpc.user

import com.example.blog_grpc.post.PostRepository
import jakarta.transaction.Transactional
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.inject.Singleton

@Named
@Singleton
class UserServiceImpl @Inject constructor(
    private val repo: UserRepository,
    private val postRepo: PostRepository
) : UserService {

    @Transactional
    override fun createUser(username: String, email: String, passwordHash: String): User {
        if(repo.findByUsername(username).isPresent) throw IllegalArgumentException("user already exist")
        if(repo.findByEmail(email).isPresent) throw IllegalArgumentException("email already exist")
        val newUser = User(username = username, email = email, passwordHash = passwordHash)
        return repo.save(newUser)
    }

    override fun findByUsername(username: String) : User? {
        return repo.findByUsername(username).orElse(null)
    }

    override fun findById(id: Long): User? {
        return repo.findById(id).orElse(null)
    }
}

