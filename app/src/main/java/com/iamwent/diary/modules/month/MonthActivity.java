package com.iamwent.diary.modules.month;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iamwent.diary.R;
import com.iamwent.diary.data.DiaryRepository;
import com.iamwent.diary.modules.day.DayActivity;
import com.iamwent.diary.modules.editor.EditorActivity;
import com.iamwent.diary.utils.LunarUtil;
import com.iamwent.diary.widget.OnRecyclerItemClickedListener;

import java.util.Collections;
import java.util.List;


public class MonthActivity extends Activity {

    private static final String EXTRA_YEAR = "year";

    private RecyclerView recyclerView;
    private MonthListAdapter adapter;
    private List<Integer> months = Collections.emptyList();
    private int year;

    private TextView titleYear;

    public static void start(Context context, int year) {
        Intent starter = new Intent(context, MonthActivity.class);
        starter.putExtra(EXTRA_YEAR, year);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        year = getIntent().getIntExtra(EXTRA_YEAR, 0);

        recyclerView = (RecyclerView) findViewById(R.id.month);
        titleYear = (TextView) findViewById(R.id.tv_year);

        adapter = new MonthListAdapter(this, months);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickedListener(MonthActivity.this) {
            @Override
            protected void onItemClick(View view, int position) {
                DayActivity.start(MonthActivity.this, year, months.get(position));
            }
        });

        titleYear.setText(String.format("%s年", LunarUtil.year2Chinese(year)));

        findViewById(R.id.btn_compose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditorActivity.start(MonthActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        months = DiaryRepository.getInstance(this).queryMonthsByYear(year);
        adapter = new MonthListAdapter(this, months);
        recyclerView.setAdapter(adapter);
    }

}
