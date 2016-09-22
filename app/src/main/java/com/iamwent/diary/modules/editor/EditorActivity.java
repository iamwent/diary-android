package com.iamwent.diary.modules.editor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.iamwent.diary.R;
import com.iamwent.diary.data.DiaryRepository;
import com.iamwent.diary.data.bean.Diary;
import com.iamwent.diary.utils.LunarUtil;

import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditorActivity extends Activity {

    private static final String EXTRA_DIARY = "diary";

    private EditText title;
    private EditText content;
    private EditText location;

    private Diary diary;

    public static void start(Context context) {
        start(context, 0L);
    }

    public static void start(Context context, long id) {
        Intent starter = new Intent(context, EditorActivity.class);
        starter.putExtra(EXTRA_DIARY, id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        long id = getIntent().getLongExtra(EXTRA_DIARY, 0L);
        diary = DiaryRepository.getInstance(this).queryDiary(id);
        if (diary == null) {
            diary = newDiary();
        }

        init();
    }

    private Diary newDiary() {
        Diary newDiary = new Diary();
        Calendar c = Calendar.getInstance();

        newDiary.id = 0L;
        newDiary.year = c.get(Calendar.YEAR);
        newDiary.month = c.get(Calendar.MONTH);
        newDiary.title = String.format("%s 日", LunarUtil.day2Chinese(c.get(Calendar.DATE)));
        newDiary.content = "";
        newDiary.location = "于 深圳";
        newDiary.createdAt = System.currentTimeMillis();

        return newDiary;
    }

    private void init() {
        title = (EditText) findViewById(R.id.et_title);
        content = (EditText) findViewById(R.id.et_content);
        location = (EditText) findViewById(R.id.et_location);

        title.setText(diary.title);
        content.setText(diary.content);
        location.setText(diary.location);

        content.setSingleLine(false);
        content.requestFocus();
        content.setSelection(diary.content.length());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        diary.content = content.getEditableText().toString();
        if (diary.content.length() == 0) {
            return;
        }
        diary.title = title.getEditableText().toString();
        if (diary.title.length() == 0) {
            diary.title = String.format("%s 日", LunarUtil.day2Chinese(Calendar.getInstance().get(Calendar.DATE)));
        }
        diary.location = location.getEditableText().toString();

        if (diary.id != 0L) {
            DiaryRepository.getInstance(this).update(diary);
        } else {
            DiaryRepository.getInstance(this).insert(diary);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
