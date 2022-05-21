rootProject.name = "findy-common-kt"

include("protos", "stub", "client", "sample")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

