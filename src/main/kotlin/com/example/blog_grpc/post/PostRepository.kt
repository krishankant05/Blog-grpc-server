package com.example.blog_grpc.post

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {
    fun findByAuthorId(authorId: Long?): List<Post>
    fun findByPublishedTrue(pageable: Pageable) : Page<Post>
    fun findByTitleContainingIgnoreCaseAndPublishedTrue(title: String, pageable: Pageable) : Page<Post>
}