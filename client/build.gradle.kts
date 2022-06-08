plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":stub"))
    runtimeOnly("io.grpc:grpc-netty:${rootProject.ext["grpcVersion"]}")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

publishing {
    publications {
        named<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set("Findy Agency gRPC Kotlin helper library")
                artifactId = "findy-common-kt-client"
                description.set("Kotlin-based helper library for gRPC services")
            }
        }
    }
}