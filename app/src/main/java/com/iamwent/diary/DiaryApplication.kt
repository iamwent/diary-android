package com.iamwent.diary

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
@HiltAndroidApp
class DiaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
