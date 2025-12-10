package com.example.blog_grpc.post

import com.example.blog_grpc.user.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.inject.Singleton

@Named
@Singleton
class PostServiceImpl @Inject constructor(
    private val repo: PostRepository,
    private val userRepo: UserRepository
) : PostService {

    @Transactional
    override fun createPost(authorId: Long, title: String, content: String): Post {
        if (!userRepo.existsById(authorId)) throw NoSuchElementException("author not found")
        val post = Post(title = title, content = content, authorId = authorId)
        return repo.save(post)
    }

    override fun getPostById(postId: Long): Post? {
        return repo.findById(postId).orElse(null)
    }

    @Transactional(readOnly = true)
    override fun listPublished(page: Int, size: Int, query: String?): org.springframework.data.domain.Page<Post> {
        val pageable = PageRequest.of(page, size)
        return if (query.isNullOrBlank()) repo.findByPublishedTrue(pageable)
        else repo.findByTitleContainingIgnoreCaseAndPublishedTrue(query, pageable)
    }


    @Transactional
    override fun publishPost(postId: Long) {
        val post = repo.findById(postId).orElseThrow { NoSuchElementException("post not found") }
        post.published = true
        post.updatedAt = java.time.Instant.now()
        repo.save(post)
    }

    @Transactional
    override fun deletePost(postId: Long) {
        val post = repo.findById(postId).orElseThrow { NoSuchElementException("post not found") }
        post.published = false
        repo.save(post)
    }

    @Transactional
    override fun incrementViewCount(postId: Long) {
        val post = repo.findById(postId).orElseThrow { NoSuchElementException("post not found") }
        post.viewCount += 1
        repo.save(post)
    }
}
