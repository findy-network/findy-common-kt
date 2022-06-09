import java.io.File

plugins {
    id("com.google.protobuf") version "0.8.15" apply false
    kotlin("jvm") version "1.4.32" apply false
    id("com.github.jk1.dependency-license-report") version "2.0"
    id("maven-publish")
}

ext["grpcVersion"] = "1.37.0"
ext["grpcKotlinVersion"] = "1.1.0"
ext["protobufVersion"] = "3.15.8"

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}

group = "org.findy_network"
version = "0.0.4"

subprojects {

    apply {
        plugin("maven-publish")
    }

   group = rootProject.group
   version = rootProject.version

    configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/findy-network/findy-common-kt")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
                }
            }
        }
    }
    extensions.getByType<PublishingExtension>().publications {
        create<MavenPublication>("maven") {
            pom {
                url.set("https://github.com/findy-network/findy-common-kt")

                scm {
                    connection.set("scm:git:https://github.com/findy-network/findy-common-kt.git")
                    developerConnection.set("scm:git:git@github.com:findy-network/findy-common-kt.git")
                    url.set("https://github.com/findy-network/findy-common-kt")
                }

                licenses {
                    license {
                        name.set("Apache 2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }

                developers {
                    developer {
                        id.set("findy-network.org")
                        name.set("Findy Agency")
                        url.set("https://findy-network.github.io/")
                    }
                }
            }
        }
    }
}

licenseReport {
    this.allowedLicensesFile = File("$projectDir/config/allowed-licenses.json")
}

