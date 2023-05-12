plugins {
    id("com.android.application")
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

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.kotlin.stdlib)
    implementation(libs.coroutines)
    kapt(libs.kapt)

    // Compose
    implementation(libs.compose)
    implementation(libs.compose.bom)
    androidTestImplementation(libs.compose.bom)

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
    implementation(libs.gson)

    // Jetpack
    implementation(libs.lifecycle)
    implementation(libs.lifecycle.viewModel)

    implementation(libs.navigation)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.dataStore)
    implementation(libs.dataStore.preferences)

    implementation(libs.room)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso)
}