
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.thyme"
version = "0.0.2"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.10.2")
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation("io.ktor:ktor-serialization:3.3.1")
    implementation("io.ktor:ktor-server-default-headers:3.3.1")
    implementation("io.ktor:ktor-server-cors:3.3.1")
    implementation("io.ktor:ktor-server-status-pages:3.3.1")
    implementation("io.ktor:ktor-server-call-logging:3.3.1")
    implementation(libs.ktor.server.content.negotiation)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    implementation(libs.logback.classic)
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.6.1")
    implementation("org.mongodb:bson-kotlinx:5.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
}
