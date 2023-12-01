package com.iamwent.diary.di

import android.content.Context
import com.iamwent.diary.data.DiaryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDiaryRepository(
        @ApplicationContext context: Context
    ): DiaryRepository {
        return DiaryRepository(context)
    }
}
