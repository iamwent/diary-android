package com.iamwent.diary.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iamwent.diary.data.bean.Diary;

/**
 * Created by iamwent on 9/20/16.
 *
 * @author iamwent
 * @since 9/20/16
 */

public final class DiaryOpenHelper extends SQLiteOpenHelper {
    private static final String NAME = "diary.db";
    private static final int VERSION = 1;

    private static final String CREATE_DIARIES = ""
            + "CREATE TABLE " + Diary.TABLE + "("
            + Diary.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Diary.YEAR + " INTEGER NOT NULL,"
            + Diary.MONTH + " INTEGER NOT NULL,"
            + Diary.TITLE + " TEXT NOT NULL,"
            + Diary.CONTENT + " TEXT NOT NULL,"
            + Diary.LOCATION + " TEXT NOT NULL,"
            + Diary.CREATED_AT + " LONG NOT NULL"
            + ");";

    public DiaryOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARIES);

        db.insert(Diary.TABLE, null, make(2016, 8, "断章", "你站在桥上看风景\n看风景的人在楼上看你\n明月装饰了你的窗子\n你装饰了别人的梦"));
        db.insert(Diary.TABLE, null, make(2016, 8, "门前", "草在结它的种子\n风在摇它的叶子\n我们站着，不说话\n就十分美好"));
        db.insert(Diary.TABLE, null, make(2016, 8, "北岛", "那时我们有梦\n关于文学\n关于爱情\n关于穿越世界的旅行\n\n如今我们深夜饮酒\n杯子碰到一起\n都是梦破碎的声音"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // todo
    }

    private ContentValues make(int year, int month, String title, String content) {
        ContentValues values = new ContentValues();
        values.put(Diary.YEAR, year);
        values.put(Diary.MONTH, month);
        values.put(Diary.TITLE, title);
        values.put(Diary.CONTENT, content);
        values.put(Diary.LOCATION, "于 深圳");
        values.put(Diary.CREATED_AT, System.currentTimeMillis());
        return values;
    }
}
