plugins {
    id("com.healthc.android")
    id("com.healthc.kotlin")
    id("com.healthc.hilt")
}

android {
    namespace = "com.healthc.data"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        // Local Properties
        buildConfigField("String", "SPOON_API_KEY",  getLocalProperty("SPOON_API_KEY"))
        buildConfigField("String", "SPOON_BASE_URL",  getLocalProperty("SPOON_BASE_URL"))
        buildConfigField("String", "SPOON_API_INGREDIENT_URL", getLocalProperty("SPOON_API_INGREDIENT_URL"))
        buildConfigField("String", "SPOON_API_PRODUCT_URL", getLocalProperty("SPOON_API_PRODUCT_URL"))
        buildConfigField("String", "DATA_GO_PRODUCT_BASE_URL", getLocalProperty("DATA_GO_PRODUCT_BASE_URL"))
        buildConfigField("String", "DATA_GO_PRODUCT_API_KEY", getLocalProperty("DATA_GO_PRODUCT_API_KEY"))
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
}

fun getLocalProperty(property: String): String {
    return com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty(property)
}

dependencies {
    implementation(project(":domain"))

    // text recognition
    implementation(libs.bundles.text.recognition)

    // retrofit & okhttp
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)

    // firebase
    implementation(platform(libs.google.firebase))
    implementation(libs.bundles.firebase)

    // pytorch
    implementation(libs.bundles.pytorch)

    // timber
    implementation(libs.timber)
}