import java.io.FileInputStream
import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

val localPropertiesFile = rootProject.file("local.properties")
val properties = Properties()
if (localPropertiesFile.exists()) {
    properties.load(FileInputStream(localPropertiesFile))
}

val weatherApiKey = properties.getProperty("WEATHER_API_KEY")
    ?: throw GradleException("WEATHER_API_KEY is missing from local.properties")

android {
    namespace = "ru.yandex.buggyweatherapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.yandex.buggyweatherapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "WEATHER_API_KEY", "\"$weatherApiKey\"")
    }

buildTypes {

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"  // Суффикс для ID (опционально)
            versionNameSuffix = "-DEBUG"     // Суффикс версии (опционально)
        }

    release {
        isMinifyEnabled = true
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlinOptions {
    jvmTarget = "11"
}
buildFeatures {
    compose = true
    buildConfig = true
}
}

dependencies {
// Android core libraries
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.lifecycle.runtime.ktx)
implementation(libs.androidx.activity.compose)
implementation(libs.androidx.compose.runtime.livedata)

// Compose
implementation(platform(libs.androidx.compose.bom))
implementation(libs.androidx.ui)
implementation(libs.androidx.ui.graphics)
implementation(libs.androidx.ui.tooling.preview)
implementation(libs.androidx.material3)

// Network
implementation(libs.retrofit)
implementation(libs.retrofit.converter.gson)
implementation(libs.okhttp)
implementation(libs.okhttp.logging.interceptor)

// Coroutines
implementation(libs.kotlinx.coroutines.android)
implementation(libs.kotlinx.coroutines.core)

// ViewModel
implementation(libs.androidx.lifecycle.viewmodel.ktx)
implementation(libs.androidx.lifecycle.viewmodel.compose)

// Location
implementation(libs.play.services.location)

// Coil image loading
implementation(libs.coil.compose)

// Hilt
implementation(libs.hilt.android)
kapt(libs.hilt.compiler)
implementation(libs.hilt.navigation.compose)

// Activity/Fragment
implementation(libs.androidx.activity.ktx)
implementation(libs.androidx.fragment.ktx)

// Testing
testImplementation(libs.junit)
androidTestImplementation(libs.androidx.junit)
androidTestImplementation(libs.androidx.espresso.core)
androidTestImplementation(platform(libs.androidx.compose.bom))
androidTestImplementation(libs.androidx.ui.test.junit4)
debugImplementation(libs.androidx.ui.tooling)
debugImplementation(libs.androidx.ui.test.manifest)
}