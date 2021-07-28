import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.6"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}
