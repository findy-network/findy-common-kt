import java.io.File

plugins {
    id("com.google.protobuf") version "0.8.15" apply false
    kotlin("jvm") version "1.4.32" apply false
    id("com.github.jk1.dependency-license-report") version "2.0"
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

licenseReport {
    this.allowedLicensesFile = File("$projectDir/config/allowed-licenses.json")
}

