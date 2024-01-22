package com.iamwent.diary.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iamwent.diary.data.bean.Diary

@Database(
    entities = [Diary::class],
    version = 2,
)
abstract class DiaryDatabase : RoomDatabase() {

    abstract fun diaryDao(): DiaryDao

    companion object {
        private const val NAME = "diary.db"

        fun get(context: Context): DiaryDatabase {
            return Room.databaseBuilder(context, DiaryDatabase::class.java, NAME)
                .build()
        }

    }

}
