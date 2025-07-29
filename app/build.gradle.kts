plugins {
    id("com.android.application") version "8.11.1"// à adapter
    kotlin("android") version "2.2.0"
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.2.0"

}



android {
    namespace = "com.example.pocadyen"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pocadyen"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Jetpack Compose
    implementation(platform(libs.compose.bom.v20240401))
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // Adyen Mobile SDK - debug version (test only)
    val adyenVersion = "2.2.0" // ou la dernière testée stable

    debugImplementation("com.adyen.ipp:pos-mobile-debug:$adyenVersion")
    debugImplementation("com.adyen.ipp:payment-tap-to-pay-debug:$adyenVersion")
    debugImplementation("com.adyen.ipp:payment-card-reader-debug:$adyenVersion")
    // Pour l'authentification du marchand
    implementation("com.adyen.ipp.authentication:authentication:$adyenVersion")




    // JSON
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.json)
    // OkHttp (client HTTP)
    implementation("com.squareup.okhttp3:okhttp:5.1.0")


}