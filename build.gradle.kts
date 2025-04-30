// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.jreleaser) apply false
}

dependencyResolutionManagement {
    repositories {
        maven(url = "https://jitpack.io")
        google()
        mavenCentral()
    }
}


// allprojects {
//    version = findProperty('VERSION_NAME') ?: "1.0.0"
//    group = findProperty('GROUP') ?: "io.legere"
// }
