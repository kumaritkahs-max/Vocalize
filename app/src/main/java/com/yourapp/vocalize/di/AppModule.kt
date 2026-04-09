package com.yourapp.vocalize.di

import android.content.Context
import androidx.room.Room
import com.yourapp.vocalize.data.repository.CategoryRepository
import com.yourapp.vocalize.data.repository.MemoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "vocalize.db").build()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(database: AppDatabase): CategoryRepository {
        return CategoryRepository(database.categoryDao())
    }

    @Provides
    @Singleton
    fun provideAudioRecorderManager(@ApplicationContext context: Context): AudioRecorderManager {
        return AudioRecorderManager(context)
    }

    @Provides
    @Singleton
    fun provideAudioPlayerManager(@ApplicationContext context: Context): AudioPlayerManager {
        return AudioPlayerManager(context)
    }
}
