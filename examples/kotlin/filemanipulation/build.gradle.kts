import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

val compileKotlin: KotlinCompile by tasks
// Supported versions: 1.6, 1.8, 9, 10, 11, 12, 13, 14, 15, 16
compileKotlin.kotlinOptions.jvmTarget = "11"

repositories {
    mavenCentral()
}

dependencies {
    // this has "-jdk8" appended to it, to enable .with() support on AutoClosable interfaces
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.xerial:sqlite-jdbc:3.36.0.1")
}
