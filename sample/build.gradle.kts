plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation(project(":client"))
    implementation(project(":stub"))
}

tasks.register<JavaExec>("Sample") {
    dependsOn("classes")
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("org.findy_network.findy_common_kt.sample.SampleKt")
}

val sampleStartScripts = tasks.register<CreateStartScripts>("sampleStartScripts") {
    mainClass.set("org.findy_network.findy_common_kt.sample.SampleKt")
    applicationName = "sample"
    outputDir = tasks.named<CreateStartScripts>("startScripts").get().outputDir
    classpath = tasks.named<CreateStartScripts>("startScripts").get().classpath
}

tasks.named("startScripts") {
    dependsOn(sampleStartScripts)
}
