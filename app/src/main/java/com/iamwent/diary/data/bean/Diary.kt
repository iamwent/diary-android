package com.iamwent.diary.data.bean

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.iamwent.diary.extension.ofEpochMilli
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

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
    val id: Long? = null,

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

    @get:Ignore
    val createdDateTime: LocalDateTime
        get() = ofEpochMilli(createdAt)

    companion object {
        const val TABLE = "diaries"
        const val ID = "id"
        const val YEAR = "year"
        const val MONTH = "month"
        const val TITLE = "title"
        const val CONTENT = "content"
        const val LOCATION = "location"
        const val CREATED_AT = "created_at"
    }
}
