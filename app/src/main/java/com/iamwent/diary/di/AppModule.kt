package com.iamwent.diary.di

import android.content.Context
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.data.local.DiaryDatabase
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
        diaryDatabase: DiaryDatabase,
    ): DiaryRepository {
        return DiaryRepository(diaryDatabase)
    }

    @Singleton
    @Provides
    fun provideDiaryDatabase(
        @ApplicationContext context: Context
    ): DiaryDatabase {
        return DiaryDatabase.get(context)
    }

}
