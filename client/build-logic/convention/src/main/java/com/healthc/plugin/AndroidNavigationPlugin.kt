package com.healthc.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidNavigationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("androidx.navigation.safeargs.kotlin")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies{
                "implementation"(libs.findBundle("androidx-navigation").get())
            }
        }
    }
}