package com.example.blog_grpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication (scanBasePackages = ["com.example.blog_grpc"])
class BlogGrpcApplication

fun main(args: Array<String>) {
	runApplication<BlogGrpcApplication>(*args)
	print("Hello, Blog gRPC Application is running!")
}
