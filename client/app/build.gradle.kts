import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.healthc.application")
    id("com.healthc.hilt")
    id("com.healthc.kotlin")
    id("com.healthc.google.services")
    id("com.healthc.navigation")
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.healthc.app"

    defaultConfig {
        applicationId = "com.example.healthc"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "SPOON_API_INGREDIENT_URL", getLocalProperty("SPOON_API_INGREDIENT_URL"))
        buildConfigField("String", "SPOON_API_PRODUCT_URL", getLocalProperty("SPOON_API_PRODUCT_URL"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

fun getLocalProperty(property: String): String {
    return gradleLocalProperties(rootDir).getProperty(property)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    // androidX
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.androidx.camerax)
    implementation(libs.androidx.splash)

    // material
    implementation(libs.google.material)

    // timber, glide, systemUiController
    implementation(libs.timber)
    implementation(libs.glide)
    implementation(libs.systemuicontroller)

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}