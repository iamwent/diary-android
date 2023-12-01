package com.iamwent.diary.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
@Parcelize
data class Diary(
    val id: Long = 0,
    val year: Int = 0,
    val month: Int = 0,
    val title: String? = null,
    val content: String? = null,
    val location: String? = null,
    val createdAt: Long = 0,
) : Parcelable {
    companion object {
        const val TABLE = "diaries"
        const val ID = "_id"
        const val YEAR = "year"
        const val MONTH = "month"
        const val LOCATION = "location"
        const val TITLE = "title"
        const val CONTENT = "content"
        const val CREATED_AT = "created_at"
    }
}
