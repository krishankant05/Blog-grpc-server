package com.example.blog_grpc

import com.example.blog_grpc.comment.CommentService
import com.example.blog_grpc.proto.AddCommentRequest
import com.example.blog_grpc.proto.AddCommentResponse
import com.example.blog_grpc.proto.Comment
import com.example.blog_grpc.proto.CommentServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService
import jakarta.inject.Inject

@GrpcService
class CommentGrpcService @Inject constructor(
    private val commentService: CommentService
) : CommentServiceGrpcKt.CommentServiceCoroutineImplBase() {


    override suspend fun addComment(request: AddCommentRequest): AddCommentResponse {
        val c = commentService.addComment(request.postId, request.authorId, request.content)
        val protoComment = Comment.newBuilder()
            .setId(c.id)
            .setPostId(c.postId)
            .setAuthorId(c.authorId)
            .setContent(c.content)
            .build()
        return AddCommentResponse.newBuilder().setComment(protoComment).build()
    }
}

