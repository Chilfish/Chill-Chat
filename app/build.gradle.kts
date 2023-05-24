plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    kotlin("android")
    kotlin("kapt")
}

val name = "top.chilfish.chillchat"

android {
    namespace = name
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = name
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    buildToolsVersion = "33.0.2"
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.kotlin.stdlib)
    implementation(libs.coroutines)
    implementation(libs.coroutines.core)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Compose
    implementation(libs.compose)

    val bom = platform(libs.compose.bom)
    implementation(bom)
    androidTestImplementation(bom)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.appcompat)
    implementation(libs.activity)
    implementation(libs.activity.compose)

    //UI
    implementation(libs.material)
    implementation(libs.material3)

    // Tool
    implementation(libs.coil)
    implementation(libs.coil.gif)

    implementation(libs.gson)
    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.insets.ui)

    // Jetpack
    implementation(libs.lifecycle)
    implementation(libs.lifecycle.viewModel)

    implementation(libs.navigation)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.dataStore)
    implementation(libs.dataStore.preferences)

    // room
    implementation(libs.room)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.androidx.hilt.compiler)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.github.liangjingkanji:Net:3.5.8")

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso)
}