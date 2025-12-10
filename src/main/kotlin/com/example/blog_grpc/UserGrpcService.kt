package com.example.blog_grpc

import com.example.blog_grpc.user.UserService
import com.example.blog_grpc.proto.CreateUserRequest
import com.example.blog_grpc.proto.CreateUserResponse
import com.example.blog_grpc.proto.UserServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService
import jakarta.inject.Inject


@GrpcService
class UserGrpcService @Inject constructor(
    private val userService: UserService
) : UserServiceGrpcKt.UserServiceCoroutineImplBase() {


    override suspend fun createUser(request: CreateUserRequest): CreateUserResponse {
        val u = userService.createUser(request.username, request.email, request.passwordHash)
        return CreateUserResponse.newBuilder().setId(u.id).build()
    }

}