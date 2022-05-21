rootProject.name = "findy-common-kt"

include("protos", "stub", "client")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

