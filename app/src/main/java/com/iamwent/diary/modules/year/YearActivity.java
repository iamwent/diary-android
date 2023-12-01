package com.iamwent.diary.modules.year;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.iamwent.diary.R;
import com.iamwent.diary.data.DiaryRepository;
import com.iamwent.diary.modules.month.MonthActivity;
import com.iamwent.diary.widget.OnRecyclerItemClickedListener;

import java.util.Collections;
import java.util.List;


public class YearActivity extends Activity {

    private RecyclerView recyclerView;
    private YearListAdapter adapter;
    List<Integer> years = Collections.emptyList();

    public static void start(Context context) {
        Intent starter = new Intent(context, YearActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);

        recyclerView = (RecyclerView) findViewById(R.id.year);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickedListener(YearActivity.this) {
            @Override
            protected void onItemClick(View view, int position) {
                MonthActivity.start(YearActivity.this, years.get(position));
            }
        });
        adapter = new YearListAdapter(this, years);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        years = DiaryRepository.getInstance(this).queryYears();
        adapter = new YearListAdapter(this, years);
        recyclerView.setAdapter(adapter);
    }

}
