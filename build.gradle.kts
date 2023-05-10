buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    }
}

apply(plugin = "org.jetbrains.kotlin.kapt")

plugins {
    id("com.android.application") version ("8.0.0") apply false
    id("com.android.library") version ("8.0.0") apply false
    kotlin("android") version ("1.8.10") apply false
}
