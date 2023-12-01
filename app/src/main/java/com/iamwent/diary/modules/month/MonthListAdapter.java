package com.iamwent.diary.modules.month;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamwent.diary.R;
import com.iamwent.diary.utils.LunarUtil;

import java.util.List;

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
class MonthListAdapter extends RecyclerView.Adapter<MonthListAdapter.ViewHolder> {

    private Context ctx;
    private List<Integer> months;

    MonthListAdapter(Context ctx, List<Integer> diaries) {
        this.ctx = ctx;
        this.months = diaries;
    }

    private
    @LayoutRes
    int provideItemLayout() {
        return R.layout.item_month;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(provideItemLayout(), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(months.get(position));
    }

    @Override
    public int getItemCount() {
        return months == null ? 0 : months.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvYear;

        ViewHolder(View itemView) {
            super(itemView);
            tvYear = (TextView) itemView.findViewById(R.id.tv_month);
        }

        void bind(int month) {
            tvYear.setText(String.format("%s月", LunarUtil.month2Chinese(month)));
        }
    }
}
