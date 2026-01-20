plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("com.google.dagger.hilt.android") version "2.58"
}

android {
    namespace = "com.viewnext.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(project(":domain"))

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.scalars)
    implementation(libs.converter.gson)

    // OkHttp & Retromock
    implementation(libs.okhttp)
    implementation(libs.retromock)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.common.jvm)
    implementation(libs.lifecycle.livedata.core)
    kapt(libs.room.compiler)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)

    implementation("com.google.dagger:dagger:2.58")
    kapt("com.google.dagger:dagger-compiler:2.58")

    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.3.0")
    kapt("androidx.hilt:hilt-compiler:1.3.0")

    implementation("com.google.dagger:hilt-android:2.58")
    kapt("com.google.dagger:hilt-compiler:2.58")
}
