package com.example.blog_grpc.post
import org.springframework.data.domain.Page

interface PostService{
    fun createPost(authorId: Long, title: String, content: String): Post
    fun getPostById(postId: Long): Post?
    fun listPublished(page: Int, size: Int, query: String? = null): Page<Post>
    fun publishPost(postId: Long)
    fun deletePost(postId: Long)
    fun incrementViewCount(postId: Long)
}