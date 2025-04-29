import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jreleaser.model.Active
import org.jreleaser.model.Signing


plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kover)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.jreleaser)
    `maven-publish`
}
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.add("-Xstring-concat=inline")
    }
}

android {
    namespace = "du.le.pdfiumandroid"
    compileSdk = 35

    ndkVersion = "28.0.12674087"

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        @Suppress("UnstableApiUsage")
        externalNativeBuild {
            cmake {
                cppFlags("")
            }
        }
    }
    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.annotation.jvm)

    testImplementation(libs.junit)

    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.espresso.core)
    testImplementation(libs.truth)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.core.testing)
}

fun isReleaseBuild(): Boolean = !findProject("VERSION_NAME").toString().contains("SNAPSHOT")

fun getReleaseRepositoryUrl(): String =
    if (rootProject.hasProperty("RELEASE_REPOSITORY_URL")) {
        rootProject.properties["RELEASE_REPOSITORY_URL"] as String
    } else {
        "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
    }

fun getSnapshotRepositoryUrl(): String =
    if (rootProject.hasProperty("SNAPSHOT_REPOSITORY_URL")) {
        rootProject.properties["SNAPSHOT_REPOSITORY_URL"] as String
    } else {
        "https://oss.sonatype.org/content/repositories/snapshots/"
    }

fun getRepositoryUrl(): String = if (isReleaseBuild()) getReleaseRepositoryUrl() else getSnapshotRepositoryUrl()

fun getRepositoryUsername(): String =
    if (rootProject.hasProperty("JRELEASER_MAVENCENTRAL_USERNAME")) {
        rootProject.properties["JRELEASER_MAVENCENTRAL_USERNAME"] as String
    } else {
        ""
    }

fun getRepositoryPassword(): String =
    if (rootProject.hasProperty("JRELEASER_MAVENCENTRAL_TOKEN")) {
        rootProject.properties["JRELEASER_MAVENCENTRAL_TOKEN"] as String
    } else {
        ""
    }

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["release"])
            groupId = "com.github.XuanDuLe" // JitPack uses this
            artifactId = "pdfiumandroid"
            version = "0.1"
        }
    }
}

jreleaser {}
