package com.iamwent.diary.modules.year;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamwent.diary.R;
import com.iamwent.diary.data.bean.Diary;
import com.iamwent.diary.utils.LunarUtil;

import java.util.List;

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
class YearListAdapter extends RecyclerView.Adapter<YearListAdapter.ViewHolder> {

    private Context ctx;
    private List<Integer> years;

    YearListAdapter(Context ctx, List<Integer> years) {
        this.ctx = ctx;
        this.years = years;
    }

    private
    @LayoutRes
    int provideItemLayout() {
        return R.layout.item_year;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(provideItemLayout(), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(years.get(position));
    }

    @Override
    public int getItemCount() {
        return years == null ? 0 : years.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvYear;

        ViewHolder(View itemView) {
            super(itemView);
            tvYear = (TextView) itemView.findViewById(R.id.tv_year);
        }

        void bind(int year) {
            tvYear.setText(String.format("%s年", LunarUtil.year2Chinese(year)));
        }
    }
}
