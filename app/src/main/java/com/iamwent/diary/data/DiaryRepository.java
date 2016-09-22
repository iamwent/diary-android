package com.iamwent.diary.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iamwent.diary.data.bean.Diary;
import com.iamwent.diary.data.local.DiaryOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iamwent on 9/20/16.
 *
 * @author iamwent
 * @since 9/20/16
 */
public class DiaryRepository {

    private static volatile DiaryRepository INSTANCE = null;

    private SQLiteDatabase db;

    public static DiaryRepository getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (DiaryRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DiaryRepository(ctx.getApplicationContext());
                }
            }
        }

        return INSTANCE;
    }

    private DiaryRepository(Context ctx) {
        db = new DiaryOpenHelper(ctx).getWritableDatabase();
    }

    public List<Integer> queryYears() {
        String[] columns = {Diary.YEAR};
        String orderBy = Diary.YEAR + " ASC";
        Cursor cursor = db.query(Diary.TABLE, columns, null, null, Diary.YEAR, null, orderBy, null);

        List<Integer> years = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            years.add(cursor.getInt(cursor.getColumnIndex(Diary.YEAR)));
        }
        cursor.close();

        return years;
    }

    public List<Integer> queryMonthsByYear(int year) {
        String[] columns = {Diary.YEAR, Diary.MONTH};
        String selection = String.format("%s = ?", Diary.YEAR);
        String[] selectionArgs = {String.valueOf(year)};
        String orderBy = Diary.MONTH + " ASC";
        Cursor cursor = db.query(Diary.TABLE, columns, selection, selectionArgs, Diary.MONTH, null, orderBy, null);

        List<Integer> months = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            months.add(cursor.getInt(cursor.getColumnIndex(Diary.MONTH)));
        }
        cursor.close();

        return months;
    }

    public Diary queryDiary(long id) {
        String[] columns = {Diary.ID, Diary.YEAR, Diary.MONTH, Diary.TITLE, Diary.CONTENT, Diary.LOCATION, Diary.CREATED_AT};
        String selection = String.format("%s = ?", Diary.ID);
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(Diary.TABLE, columns, selection, selectionArgs, null, null, null, null);

        Diary diary = null;
        if (cursor.moveToNext()) {
            diary = make(cursor);
        }
        cursor.close();

        return diary;
    }

    public List<Diary> queryDaysByYearAndMonth(int year, int month) {
        String[] columns = {Diary.ID, Diary.YEAR, Diary.MONTH, Diary.TITLE, Diary.CONTENT, Diary.LOCATION, Diary.CREATED_AT};
        String selection = String.format("%s = ? AND %s = ?", Diary.YEAR, Diary.MONTH);
        String[] selectionArgs = {String.valueOf(year), String.valueOf(month)};
        String orderBy = Diary.CREATED_AT + " ASC";
        Cursor cursor = db.query(Diary.TABLE, columns, selection, selectionArgs, null, null, orderBy, null);

        List<Diary> diaries = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            diaries.add(make(cursor));
        }
        cursor.close();

        return diaries;
    }

    public void insert(Diary diary) {
        ContentValues values = new ContentValues();
        values.put(Diary.YEAR, diary.year);
        values.put(Diary.MONTH, diary.month);
        values.put(Diary.TITLE, diary.title);
        values.put(Diary.CONTENT, diary.content);
        values.put(Diary.LOCATION, diary.location);
        values.put(Diary.CREATED_AT, diary.createdAt);

        db.insert(Diary.TABLE, null, values);
    }

    public void update(Diary diary) {
        ContentValues values = new ContentValues();
        values.put(Diary.YEAR, diary.year);
        values.put(Diary.MONTH, diary.month);
        values.put(Diary.TITLE, diary.title);
        values.put(Diary.CONTENT, diary.content);
        values.put(Diary.LOCATION, diary.location);
        values.put(Diary.CREATED_AT, diary.createdAt);

        String whereClause = String.format("%s = ?", Diary.ID);
        String[] whereArgs = {String.valueOf(diary.id)};
        db.update(Diary.TABLE, values, whereClause, whereArgs);
    }

    public void delete(long id) {
        String whereClause = String.format("%s = ?", Diary.ID);
        String[] whereArgs = {String.valueOf(id)};
        db.delete(Diary.TABLE, whereClause, whereArgs);
    }

    private Diary make(final Cursor cursor) {
        Diary diary = new Diary();

        diary.id = cursor.getInt(cursor.getColumnIndex(Diary.ID));
        diary.year = cursor.getInt(cursor.getColumnIndex(Diary.YEAR));
        diary.month = cursor.getInt(cursor.getColumnIndex(Diary.MONTH));
        diary.title = cursor.getString(cursor.getColumnIndex(Diary.TITLE));
        diary.content = cursor.getString(cursor.getColumnIndex(Diary.CONTENT));
        diary.location = cursor.getString(cursor.getColumnIndex(Diary.LOCATION));
        diary.createdAt = cursor.getLong(cursor.getColumnIndex(Diary.CREATED_AT));

        return diary;
    }
}