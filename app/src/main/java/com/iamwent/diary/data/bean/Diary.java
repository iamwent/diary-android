package com.iamwent.diary.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
public class Diary implements Parcelable {
    public static final String TABLE = "diaries";
    public static final String ID = "_id";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String LOCATION = "location";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String CREATED_AT = "created_at";

    public long id;
    public int year;
    public int month;
    public String title;
    public String content;
    public String location;
    public long createdAt;

    public Diary() {

    }

    public Diary(Parcel in) {
        id = in.readLong();
        year = in.readInt();
        month = in.readInt();
        title = in.readString();
        content = in.readString();
        location = in.readString();
        createdAt = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(location);
        dest.writeLong(createdAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Diary> CREATOR = new Creator<Diary>() {
        @Override
        public Diary createFromParcel(Parcel in) {
            return new Diary(in);
        }

        @Override
        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };
}
