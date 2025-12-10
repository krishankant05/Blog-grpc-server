package com.example.blog_grpc.comment

import org.springframework.data.domain.Page

interface CommentService {
    fun addComment(postId: Long, authorId: Long, content: String) : Comment
    fun getPostComments(postId: Long, page: Int, size: Int): Page<Comment>
    fun getUserComments(authorId: Long, page: Int, size: Int): Page<Comment>
}