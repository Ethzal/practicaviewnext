plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.practicaviewnext"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.practicaviewnext"
        minSdk = 24
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

    // Habilitar View Binding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.filament.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit
    implementation(libs.retrofit)
    // Retrofit with Scalar Converter
    implementation(libs.converter.scalars)
    // Retrofit Converter Gson
    implementation(libs.converter.gson)

    // RecyclerView
    implementation(libs.recyclerview)
    // For control over item selection of both touch and mouse driven selection
    implementation(libs.recyclerview.selection)

    // Retromock
    implementation(libs.retromock)
    implementation(libs.okhttp)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)
    // LiveData
    implementation(libs.lifecycle.livedata)
    // Lifecycles only (without ViewModel or LiveData)
    implementation(libs.lifecycle.runtime)

    // Saved state module for ViewModel
    implementation(libs.lifecycle.viewmodel.savedstate)

    // Annotation processor
    annotationProcessor(libs.lifecycle.compiler)
}