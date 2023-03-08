package com.example.healthc.presentation.utils

import android.os.Build
import android.os.ext.SdkExtensions.getExtensionVersion

object PhotoPickerAvailabilityChecker {

    private const val ANDROID_R_REQUIRED_EXTENSION_VERSION = 2

    fun isPhotoPickerAvailable(): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> true
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                getExtensionVersion(Build.VERSION_CODES.R) >= ANDROID_R_REQUIRED_EXTENSION_VERSION
            }
            else -> false
        }
    }
}