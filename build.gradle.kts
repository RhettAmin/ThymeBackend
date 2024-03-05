import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.21"
	kotlin("plugin.serialization") version "1.8.21"
	kotlin("plugin.spring") version "1.8.21"
	kotlin("plugin.jpa") version "1.8.21"
	kotlin("plugin.allopen") version "1.8.0"
	kotlin("kapt") version "1.8.0"
}

group = "com.rhett"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.Embeddable")
	annotation("jakarta.persistence.MappedSuperclass")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")
	implementation("com.google.cloud:google-cloud-storage:2.30.1")
	implementation("org.springframework.cloud:spring-cloud-gcp-starter:5.0.0")
	implementation("com.google.appengine:appengine-remote-api:2.0.23")
	implementation("com.google.appengine.tools:appengine-gcs-client:0.8.3")
	implementation("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x")
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.springframework.data:spring-data-mongodb")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "mockito-core")
	}
	testImplementation("io.mockk:mockk:1.13.4")
}

kapt {
	keepJavacAnnotationProcessors = true
}


tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
