plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.5.21"
}

android {
    compileSdk = 31
    buildToolsVersion = "31.0.0"

    defaultConfig {
        applicationId = "be.kuleuven.howlongtobeat"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // --- defaults
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.activity:activity-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // --- kotlinx extras
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")

    // --- navigation
    // see https://developer.android.com/guide/navigation/navigation-getting-started
    val nav_version = "2.3.5"
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha05")

    // --- room db (WARNING apply plugin kotlin-kapt!)
    val room_version = "2.3.0"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    kapt("org.xerial:sqlite-jdbc:3.34.0") // only for M1 Mac users!

    // --- Volley for HTTP requests
    implementation("com.android.volley:volley:1.2.0")

    // --- Google Vision API specific dependencies
    // firebase specific
    //implementation("com.google.firebase:firebase-bom:28.3.1")

    // OAUth play services
    //implementation("com.google.android.gms:play-services-auth:19.2.0")
    //implementation("com.google.android.gms:play-services-base:17.6.0")


    // Vision itself
    // comes with conflicts, exclude http client using https://docs.gradle.org/current/userguide/dependency_downgrade_and_exclude.html
    implementation("com.google.api-client:google-api-client-android:1.32.1") {
        exclude(module = "httpclient")
    }
    implementation("com.google.http-client:google-http-client-gson:1.39.2") {
        exclude(module ="httpclient")
    }
    implementation("com.google.apis:google-api-services-vision:v1-rev451-1.25.0") {
        exclude(module = "httpclient")
    }
}