buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
    dependencies {
        classpath("org.openjfx:javafx-plugin:0.0.+")
    }
}


plugins {
    kotlin("jvm") version "1.5.21"
    application
    // version "0.0.8" MUST be suffixed here: https://stackoverflow.com/questions/56510154/how-do-i-add-javafx-to-a-gradle-build-file-using-kotlin-dsl#60269903
    // if this does not load, the javafx{} and application {} sections will not load.
    id("org.openjfx.javafxplugin") version "0.0.8"
}

group = "be.kuleuven.walkerfx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("junit", "junit", "4.12")
}

application {
    mainClass.set("be.kuleuven.walkerfx.MainApp")
}
