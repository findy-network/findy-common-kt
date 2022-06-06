plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":stub"))
    runtimeOnly("io.grpc:grpc-netty:${rootProject.ext["grpcVersion"]}")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
