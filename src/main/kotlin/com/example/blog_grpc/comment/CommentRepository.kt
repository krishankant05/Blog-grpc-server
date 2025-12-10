package com.example.blog_grpc.comment

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long>{
    fun findByPostIdAndDeletedFalse(postId: Long, pageable: Pageable): Page<Comment>
    fun findByAuthorIdAndDeletedFalse(authorId: Long, pageable: Pageable): Page<Comment>
}