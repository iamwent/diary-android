package com.iamwent.diary;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
public class DiaryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig config = new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/FZLongZhaoFW.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
        CalligraphyConfig.initDefault(config);
    }
}
