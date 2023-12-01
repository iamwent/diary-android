package com.iamwent.diary.modules.day;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamwent.diary.R;
import com.iamwent.diary.data.bean.Diary;

import java.util.List;

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder> {

    private Context ctx;
    private List<Diary> diaries;

    DayListAdapter(Context ctx, List<Diary> diaries) {
        this.ctx = ctx;
        this.diaries = diaries;
    }

    private
    @LayoutRes
    int provideItemLayout() {
        return R.layout.item_day;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(provideItemLayout(), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(diaries.get(position));
    }

    @Override
    public int getItemCount() {
        return diaries == null ? 0 : diaries.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay;

        ViewHolder(View itemView) {
            super(itemView);

            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
        }

        void bind(Diary diary) {
            tvDay.setText(diary.title);
        }
    }
}
