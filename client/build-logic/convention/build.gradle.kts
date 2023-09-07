plugins{
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.build)
    compileOnly(libs.kotlin.gradle)
}

gradlePlugin {
    plugins {
        create("android-application") {
            id = "com.healthc.application"
            implementationClass = "com.healthc.plugin.AndroidApplicationPlugin"
        }
        create("android-hilt") {
            id = "com.healthc.hilt"
            implementationClass = "com.healthc.plugin.AndroidHiltPlugin"
        }
        create("android") {
            id = "com.healthc.android"
            implementationClass = "com.healthc.plugin.AndroidPlugin"
        }
        create("android-navigation") {
            id = "com.healthc.navigation"
            implementationClass = "com.healthc.plugin.AndroidNavigationPlugin"
        }
        create("google-services") {
            id = "com.healthc.google.services"
            implementationClass = "com.healthc.plugin.GoogleServicePlugin"
        }
        create("android-kotlin") {
            id = "com.healthc.kotlin"
            implementationClass = "com.healthc.plugin.AndroidKotlinPlugin"
        }
    }
}
