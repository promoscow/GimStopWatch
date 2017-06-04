package ru.xpendence.development.gimstopwatch.util;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Map;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodInDayRation;

/**
 * Created by promoscow on 04.06.17.
 */

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ChartHolder> {

    private Map<String, ArrayList<GoodInDayRation>> archivesForCharts;

    public ChartAdapter() {
        archivesForCharts = FoodStuffsData.archiveRations;
    }

    @Override
    public ChartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.charts_nutritions_layout, parent, false);
        return new ChartHolder(view);
    }

    @Override
    public void onBindViewHolder(ChartHolder holder, int position) {
        Log.d("position", String.valueOf(position));
        if (position < 3) {
            holder.chartDataImage.setImageResource(R.drawable.transparent);
        } else {
            holder.chartDataImage.setImageResource(FoodStuffsData.setImageResourseForCharts(position));
        }
    }

    @Override
    public int getItemCount() {
        return archivesForCharts.size() + 3;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ChartHolder extends RecyclerView.ViewHolder {

        CardView chartCardView;
        ImageView chartDataImage;

        public ChartHolder(View itemView) {
            super(itemView);
            chartCardView = (CardView) itemView.findViewById(R.id.charts_card_view);
            chartDataImage = (ImageView) itemView.findViewById(R.id.charts_nutrients_image);
        }
    }
}
