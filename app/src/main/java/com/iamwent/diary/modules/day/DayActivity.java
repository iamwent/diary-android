package com.iamwent.diary.modules.day;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iamwent.diary.R;
import com.iamwent.diary.data.DiaryRepository;
import com.iamwent.diary.data.bean.Diary;
import com.iamwent.diary.modules.editor.EditorActivity;
import com.iamwent.diary.modules.preview.PreviewActivity;
import com.iamwent.diary.utils.LunarUtil;
import com.iamwent.diary.widget.OnRecyclerItemClickedListener;

import java.util.Collections;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DayActivity extends Activity {
    private static final String EXTRA_YEAR = "year";
    private static final String EXTRA_MONTH = "month";

    private RecyclerView recyclerView;
    private List<Diary> diaries = Collections.emptyList();
    private DayListAdapter adapter;
    private int year;
    private int month;

    public static void start(Context context, int year, int month) {
        Intent starter = new Intent(context, DayActivity.class);
        starter.putExtra(EXTRA_YEAR, year);
        starter.putExtra(EXTRA_MONTH, month);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        year = getIntent().getIntExtra(EXTRA_YEAR, 0);
        month = getIntent().getIntExtra(EXTRA_MONTH, 0);

        TextView titleYear = (TextView) findViewById(R.id.tv_year);
        TextView titleMonth = (TextView) findViewById(R.id.tv_month);
        recyclerView = (RecyclerView) findViewById(R.id.day);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickedListener(DayActivity.this) {
            @Override
            protected void onItemClick(View view, int position) {
                PreviewActivity.start(DayActivity.this, diaries.get(position).id);
            }
        });
        adapter = new DayListAdapter(this, diaries);
        recyclerView.setAdapter(adapter);

        titleYear.setText(String.format("%s年", LunarUtil.year2Chinese(year)));
        titleMonth.setText(String.format("%s月", LunarUtil.month2Chinese(month)));

        findViewById(R.id.btn_compose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditorActivity.start(DayActivity.this);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        diaries = DiaryRepository.getInstance(this).queryDaysByYearAndMonth(year, month);
        adapter = new DayListAdapter(this, diaries);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
