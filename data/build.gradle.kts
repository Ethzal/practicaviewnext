plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
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

    // Dagger / Hilt
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.androidx.hilt.lifecycle.viewmodel)
    kapt(libs.androidx.hilt.compiler)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
