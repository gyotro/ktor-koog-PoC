plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
}

group = "com.ai"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

tasks {
    shadowJar {
        manifest {
            attributes(
                "Main-Class" to "io.ktor.server.netty.EngineMain"
            )
        }
        // This is needed to avoid "Invalid signature file digest" errors
        // when using dependencies that are signed
        exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
        
        // Use the classifier 'all' to avoid conflicts with the default jar task
        archiveClassifier.set("all")
        
        // Use the base name 'app' for the generated JAR
        archiveBaseName.set("app")
        
        // Make the archive name more descriptive
        archiveVersion.set("")
    }
}



dependencies {
    // Koog AI dependencies
    implementation(libs.koog.ktor)
    implementation(libs.koog.agents.core)
    implementation(libs.koog.prompt.core)
    implementation(libs.koog.prompt.ollama)
    
    // Ktor dependencies
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.config.yaml)
    
    // Logging
    implementation(libs.logback.classic)
    
    // Test dependencies
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
