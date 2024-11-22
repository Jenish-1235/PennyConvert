import java.util.Properties
import java.io.FileInputStream
import java.io.File

// Helper function to read a property from local.properties
fun getLocalProperty(propertyName: String): String {
    val properties = Properties()
    val localPropertiesFile = File(rootProject.rootDir, "local.properties")
    if (localPropertiesFile.exists()) {
        FileInputStream(localPropertiesFile).use { properties.load(it) }
        return properties.getProperty(propertyName, "")
    }
    return ""
}

plugins {
    id("com.android.application")
}

android {
    namespace = "com.jenish.pennyconvert"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.jenish.pennyconvert"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiKey = getLocalProperty("API_KEY")

        // Inject the API key into BuildConfig as a String field
        buildConfigField("String", "API_KEY", "\"$apiKey\"")

    }
    buildFeatures {
        buildConfig = true // Ensure BuildConfig is enabled
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.gridlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation ("com.squareup.retrofit2:retrofit:2.8.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.8.0")
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")

    // Required for one-shot operations (to use `ListenableFuture` from Guava Android)
    implementation("com.google.guava:guava:31.0.1-android")

    // Required for streaming operations (to use `Publisher` from Reactive Streams)
    implementation("org.reactivestreams:reactive-streams:1.0.4")

    implementation ("androidx.core:core:1.12.0")

    implementation("com.google.android.gms:play-services-basement:18.2.0")


}
