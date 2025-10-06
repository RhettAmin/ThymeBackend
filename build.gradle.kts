
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    kotlin("plugin.serialization") version "2.1.0"
}

group = "com.thyme"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.7.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-serialization:3.0.2")
    implementation("io.ktor:ktor-server-default-headers:3.0.2")
    implementation("io.ktor:ktor-server-cors:3.0.2")
    implementation("io.ktor:ktor-server-status-pages:3.0.2")
    implementation("io.ktor:ktor-server-call-logging:3.0.2")
    implementation("io.ktor:ktor-server-content-negotiation:3.0.2")
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    implementation("ch.qos.logback:logback-classic:1.5.16")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.0.0")
    implementation("org.mongodb:bson-kotlinx:5.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
}
