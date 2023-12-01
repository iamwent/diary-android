package com.iamwent.diary.data.bean

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
@Entity(tableName = Diary.TABLE)
@Parcelize
data class Diary(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long = 0,

    @ColumnInfo(name = YEAR)
    val year: Int = 0,

    @ColumnInfo(name = MONTH)
    val month: Int = 0,

    @ColumnInfo(name = TITLE)
    val title: String? = null,

    @ColumnInfo(name = CONTENT)
    val content: String? = null,

    @ColumnInfo(name = LOCATION)
    val location: String? = null,

    @ColumnInfo(name = CREATED_AT)
    val createdAt: Long = 0,
) : Parcelable {
    companion object {
        const val TABLE = "diaries"
        const val ID = "id"
        const val YEAR = "year"
        const val MONTH = "month"
        const val LOCATION = "location"
        const val TITLE = "title"
        const val CONTENT = "content"
        const val CREATED_AT = "created_at"
    }
}
