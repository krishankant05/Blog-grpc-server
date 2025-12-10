import com.google.protobuf.gradle.*

plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "2.2.21"
	id("com.google.protobuf") version "0.9.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"
val grpcKotlinVersion = "1.4.1"
val grpcVersion = "1.56.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("javax.inject:javax.inject:1")
	implementation("com.zaxxer:HikariCP:5.1.0")
	implementation("mysql:mysql-connector-java:8.0.33")

	implementation("net.devh:grpc-server-spring-boot-starter:3.0.0.RELEASE")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
	implementation("org.postgresql:postgresql:42.7.2")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	implementation("jakarta.inject:jakarta.inject-api:2.0.1")

	implementation("org.springframework.security:spring-security-crypto:6.3.3")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll(
			"-Xjsr305=strict",
			"-Xannotation-default-target=param-property"
		)
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
	annotation("jakarta.inject.Named")
	annotation("jakarta.inject.Singleton")
	annotation("jakarta.inject.Inject")
	annotation("org.springframework.stereotype.Component")
	annotation("org.springframework.stereotype.Service")
	annotation("org.springframework.stereotype.Repository")

}

tasks.withType<Test> {
	useJUnitPlatform()
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.25.3"
	}
	plugins {
		id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion" }
		id("grpckt") { artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar" }
	}
	generateProtoTasks {
		all().forEach { task ->
			task.plugins {
				id("grpc")
				id("grpckt")
			}
		}
	}
}

sourceSets {
	val main by getting {
		proto.srcDir("src/main/proto")
		resources {
			exclude("**/*.proto")
		}
	}
}

tasks.withType<ProcessResources>().configureEach {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
