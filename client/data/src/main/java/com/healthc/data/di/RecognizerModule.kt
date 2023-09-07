package com.healthc.data.di

import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object RecognizerModule {

    @KoreanRecognizer
    @Provides
    fun providesKoreanRecognizer(): TextRecognizer {
        return TextRecognition.getClient(
            KoreanTextRecognizerOptions.Builder().build()
        )
    }

    @EnglishRecognizer
    @Provides
    fun providesEnglishRecognizer(): TextRecognizer{
        return TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS
        )
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class KoreanRecognizer

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class EnglishRecognizer