package ru.xpendence.development.gimstopwatch.util;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodInDayRation;
import ru.xpendence.development.gimstopwatch.fragments.FragmentCharts;

/**
 * Created by promoscow on 04.06.17.
 * Adapter to fill calories/nutrients charts.
 */

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ChartHolder> {

    private final String TAG = this.getClass().getSimpleName();

    private FragmentCharts fragmentCharts;

    private ArrayList<ArrayList<GoodInDayRation>> archivesForCharts = new ArrayList<>();
    private ArrayList<Bitmap> bitmapsForCharts = new ArrayList<>();

    public ChartAdapter(FragmentCharts fragmentCharts) {
        this.fragmentCharts = fragmentCharts;
        int index = 0;
        Map<String, ArrayList<GoodInDayRation>> goods = FoodStuffsData.archiveRations;
        Map<String, Bitmap> charts = FoodStuffsData.archiveCharts;
        Log.d(TAG, String.valueOf(goods.size()));
        Log.d(TAG, String.valueOf(charts.size()));
        for (String key : FoodStuffsData.archiveRations.keySet()) {
            Log.d(TAG, "index - " + String.valueOf(index));
            archivesForCharts.add(index, goods.get(key));
            bitmapsForCharts.add(index++, charts.get(key));
        }
    }

    @Override
    public ChartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.charts_nutritions_layout, parent, false);
        return new ChartHolder(view);
    }

    @Override
    public void onBindViewHolder(ChartHolder holder, final int position) {
        Log.d("position", String.valueOf(position));
        if (position < 3) {
            holder.chartDataImage.setImageResource(R.drawable.transparent);
        } else {
//            holder.chartDataImage.setImageResource(FoodStuffsData.setImageResourseForCharts(position));
            try {
                holder.chartDataImage.setImageBitmap(bitmapsForCharts.get(position - 3));
                holder.chartDataImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentCharts.setChartHeader(archivesForCharts.get(position - 3).get(0).getDate());
                        fragmentCharts.showTable(archivesForCharts.get(position - 3));
                    }
                });
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
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
//            chartDetailsHeading = (TextView) itemView.findViewById(R.id.chart_details_heading);
//            chartDetailsHeading.setTypeface(CommonSettings.getRobotoCondLight());
        }
    }
}
