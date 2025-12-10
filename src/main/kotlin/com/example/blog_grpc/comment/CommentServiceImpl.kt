package com.example.blog_grpc.comment

import com.example.blog_grpc.post.PostRepository
import com.example.blog_grpc.user.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.inject.Singleton

@Named
@Singleton
class CommentServiceImpl @Inject constructor(
    private val repo: CommentRepository,
    private val userRepo: UserRepository,
    private val postRepo: PostRepository
) : CommentService{

    @Transactional
    override fun addComment(postId: Long, authorId: Long, content: String): Comment {
        if(!postRepo.findById(postId).isPresent) throw IllegalArgumentException("post not found")
        val comment = Comment(postId = postId, authorId = authorId, content = content)
        return repo.save(comment)
    }

    override fun getPostComments(postId: Long, page: Int, size: Int): Page<Comment> {
        val pageable = PageRequest.of(page,size)
        return repo.findByPostIdAndDeletedFalse(postId,pageable)
    }

    override fun getUserComments(authorId: Long, page: Int, size: Int): Page<Comment> {
        val pageable = PageRequest.of(page,size)
        return repo.findByAuthorIdAndDeletedFalse(authorId,pageable)
    }

}