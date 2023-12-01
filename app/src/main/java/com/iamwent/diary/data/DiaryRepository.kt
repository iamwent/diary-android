package com.iamwent.diary.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.data.local.DiaryOpenHelper
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.Volatile

/**
 * Created by iamwent on 9/20/16.
 *
 * @author iamwent
 * @since 9/20/16
 */
@Singleton
class DiaryRepository @Inject constructor(ctx: Context) {

    private val db: SQLiteDatabase

    init {
        db = DiaryOpenHelper(ctx).writableDatabase
    }

    @SuppressLint("Range")
    fun queryYears(): List<Int> {
        val columns = arrayOf(Diary.YEAR)
        val orderBy: String = Diary.YEAR + " ASC"
        val cursor = db.query(Diary.TABLE, columns, null, null, Diary.YEAR, null, orderBy, null)
        val years: MutableList<Int> = ArrayList(cursor.count)
        while (cursor.moveToNext()) {
            years.add(cursor.getInt(cursor.getColumnIndex(Diary.YEAR)))
        }
        cursor.close()
        return years
    }

    @SuppressLint("Range")
    fun queryMonthsByYear(year: Int): List<Int> {
        val columns = arrayOf<String>(Diary.YEAR, Diary.MONTH)
        val selection = String.format("%s = ?", Diary.YEAR)
        val selectionArgs = arrayOf(year.toString())
        val orderBy: String = Diary.MONTH + " ASC"
        val cursor = db.query(
            Diary.TABLE,
            columns,
            selection,
            selectionArgs,
            Diary.MONTH,
            null,
            orderBy,
            null
        )
        val months: MutableList<Int> = ArrayList(cursor.count)
        while (cursor.moveToNext()) {
            months.add(cursor.getInt(cursor.getColumnIndex(Diary.MONTH)))
        }
        cursor.close()
        return months
    }

    fun queryDiary(id: Long): Diary? {
        val columns = arrayOf<String>(
            Diary.ID,
            Diary.YEAR,
            Diary.MONTH,
            Diary.TITLE,
            Diary.CONTENT,
            Diary.LOCATION,
            Diary.CREATED_AT
        )
        val selection = String.format("%s = ?", Diary.ID)
        val selectionArgs = arrayOf(id.toString())
        val cursor =
            db.query(Diary.TABLE, columns, selection, selectionArgs, null, null, null, null)
        var diary: Diary? = null
        if (cursor.moveToNext()) {
            diary = make(cursor)
        }
        cursor.close()
        return diary
    }

    fun queryDaysByYearAndMonth(year: Int, month: Int): List<Diary> {
        val columns = arrayOf(
            Diary.ID,
            Diary.YEAR,
            Diary.MONTH,
            Diary.TITLE,
            Diary.CONTENT,
            Diary.LOCATION,
            Diary.CREATED_AT
        )
        val selection = String.format("%s = ? AND %s = ?", Diary.YEAR, Diary.MONTH)
        val selectionArgs = arrayOf(year.toString(), month.toString())
        val orderBy: String = Diary.CREATED_AT + " ASC"
        val cursor =
            db.query(Diary.TABLE, columns, selection, selectionArgs, null, null, orderBy, null)
        val diaries: MutableList<Diary> = ArrayList(cursor.count)
        while (cursor.moveToNext()) {
            diaries.add(make(cursor))
        }
        cursor.close()
        return diaries
    }

    fun insert(diary: Diary?) {
        val values = ContentValues()
        values.put(Diary.YEAR, diary!!.year)
        values.put(Diary.MONTH, diary.month)
        values.put(Diary.TITLE, diary.title)
        values.put(Diary.CONTENT, diary.content)
        values.put(Diary.LOCATION, diary.location)
        values.put(Diary.CREATED_AT, diary.createdAt)
        db.insert(Diary.TABLE, null, values)
    }

    fun update(diary: Diary?) {
        val values = ContentValues()
        values.put(Diary.YEAR, diary!!.year)
        values.put(Diary.MONTH, diary.month)
        values.put(Diary.TITLE, diary.title)
        values.put(Diary.CONTENT, diary.content)
        values.put(Diary.LOCATION, diary.location)
        values.put(Diary.CREATED_AT, diary.createdAt)
        val whereClause = String.format("%s = ?", Diary.ID)
        val whereArgs = arrayOf(diary.id.toString())
        db.update(Diary.TABLE, values, whereClause, whereArgs)
    }

    fun delete(id: Long) {
        val whereClause = String.format("%s = ?", Diary.ID)
        val whereArgs = arrayOf(id.toString())
        db.delete(Diary.TABLE, whereClause, whereArgs)
    }

    @SuppressLint("Range")
    private fun make(cursor: Cursor): Diary {
        return Diary(
            id = cursor.getInt(cursor.getColumnIndex(Diary.ID)).toLong(),
            year = cursor.getInt(cursor.getColumnIndex(Diary.YEAR)),
            month = cursor.getInt(cursor.getColumnIndex(Diary.MONTH)),
            title = cursor.getString(cursor.getColumnIndex(Diary.TITLE)),
            content = cursor.getString(cursor.getColumnIndex(Diary.CONTENT)),
            location = cursor.getString(cursor.getColumnIndex(Diary.LOCATION)),
            createdAt = cursor.getLong(cursor.getColumnIndex(Diary.CREATED_AT)),
        )
    }
}