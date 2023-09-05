import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.androidx.navigation.safeargs)
}

android {
    namespace = "com.example.healthc"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.healthc"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Local Properties
        buildConfigField("String", "SPOON_API_KEY",  getLocalProperty("SPOON_API_KEY"))
        buildConfigField("String", "SPOON_BASE_URL",  getLocalProperty("SPOON_BASE_URL"))
        buildConfigField("String", "SPOON_API_INGREDIENT_URL", getLocalProperty("SPOON_API_INGREDIENT_URL"))
        buildConfigField("String", "SPOON_API_PRODUCT_URL", getLocalProperty("SPOON_API_PRODUCT_URL"))
        buildConfigField("String", "DATA_GO_PRODUCT_BASE_URL", getLocalProperty("DATA_GO_PRODUCT_BASE_URL"))
        buildConfigField("String", "DATA_GO_PRODUCT_API_KEY", getLocalProperty("DATA_GO_PRODUCT_API_KEY"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }
}

fun getLocalProperty(property: String): String {
    return gradleLocalProperties(rootDir).getProperty(property)
}

dependencies {
    // androidX
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.bundles.androidx.camerax)
    implementation(libs.androidx.splash)

    // kotlin & coroutine
    implementation(libs.kotlin)
    implementation(libs.kotlin.coroutines)

    // firebase
    implementation(platform(libs.google.firebase))
    implementation(libs.bundles.firebase)

    // material & text recognition
    implementation(libs.google.material)
    implementation(libs.bundles.text.recognition)

    // retrofit & okhttp
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)

    // hilt & compiler
    implementation(libs.hilt)
    ksp(libs.hilt.ksp)

    // timber, glide, systemUiController
    implementation(libs.timber)
    implementation(libs.glide)
    implementation(libs.systemuicontroller)

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}