package com.example.blog_grpc

import com.example.blog_grpc.post.PostService
import com.example.blog_grpc.proto.CreatePostRequest
import com.example.blog_grpc.proto.CreatePostResponse
import com.example.blog_grpc.proto.GetPostRequest
import com.example.blog_grpc.proto.ListPostsRequest
import com.example.blog_grpc.proto.ListPostsResponse
import com.example.blog_grpc.proto.Post
import com.example.blog_grpc.proto.PostServiceGrpcKt
import com.example.blog_grpc.proto.PublishPostRequest
import io.grpc.Status
import net.devh.boot.grpc.server.service.GrpcService
import jakarta.inject.Inject

@GrpcService
class PostGrpcService @Inject constructor(
    private val postService: PostService
) : PostServiceGrpcKt.PostServiceCoroutineImplBase() {


    override suspend fun createPost(request: CreatePostRequest): CreatePostResponse {
        val p = postService.createPost(request.authorId, request.title, request.content)
        return CreatePostResponse.newBuilder().setId(p.id).build()
    }


    override suspend fun getPost(request: GetPostRequest): Post {
        val p = postService.getPostById(request.id) ?: throw Status.NOT_FOUND.asRuntimeException()
        return Post.newBuilder()
            .setId(p.id)
            .setTitle(p.title)
            .setContent(p.content)
            .setAuthorId(p.authorId)
            .setPublished(p.published)
            .setViewCount(p.viewCount)
            .build()
    }


    override suspend fun listPosts(request: ListPostsRequest): ListPostsResponse {
        val q = request.query.ifBlank { null }
        val page = postService.listPublished(request.page, request.size, q)

        val builder = ListPostsResponse.newBuilder()
            .setTotalPages(page.totalPages)

        page.content.forEach { p ->
            builder.addPosts(
                Post.newBuilder()
                    .setId(p.id)
                    .setTitle(p.title)
                    .setContent(p.content)
                    .setAuthorId(p.authorId)
                    .setPublished(p.published)
                    .setViewCount(p.viewCount)
                    .build()
            )
        }

        return builder.build()
    }


    override suspend fun publishPost(request: PublishPostRequest): Post {
        postService.publishPost(request.id)
        val p = postService.getPostById(request.id) ?: throw Status.NOT_FOUND.asRuntimeException()
        return Post.newBuilder()
            .setId(p.id)
            .setTitle(p.title)
            .setContent(p.content)
            .setAuthorId(p.authorId)
            .setPublished(p.published)
            .setViewCount(p.viewCount)
            .build()
    }
}