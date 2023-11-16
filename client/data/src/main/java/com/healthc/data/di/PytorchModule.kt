package com.healthc.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.pytorch.LiteModuleLoader
import java.io.File
import java.io.FileOutputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PytorchModule {

    @Provides
    @Singleton
    fun providesPytorchModule(
        @ApplicationContext context: Context,
    ): org.pytorch.Module = LiteModuleLoader.load(
        assetFilePath(
            context,
            "food_640_4_500.torchscript.ptl"
        )
    )

    private fun assetFilePath(context: Context, assetName: String): String {
        val file = File(context.filesDir, assetName)

        if (file.exists() && file.length() > 0) {
            return file.absolutePath
        }

        context.assets.open(assetName).use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while ((inputStream.read(buffer).also { read = it }) != -1) {
                    outputStream.write(buffer, 0, read)
                }
                outputStream.flush()
            }
            return file.absolutePath
        }
    }
}