package com.healthc.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class AndroidApplicationPlugin: Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            pluginManager.apply("com.android.application")

            extensions.getByType<BaseExtension>().apply {
                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

                buildFeatures.apply {
                    buildConfig = true
                    dataBinding.enable = true
                    viewBinding = true
                }

                setCompileSdkVersion(libs.findVersion("compileSdk").get().requiredVersion.toInt())

                defaultConfig {
                    versionCode = libs.findVersion("versionCode").get().requiredVersion.toInt()
                    versionName = libs.findVersion("versionName").get().requiredVersion

                    minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
                    targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
                }
            }
        }
    }
}