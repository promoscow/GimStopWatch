package ru.xpendence.development.gimstopwatch.util;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.TreeMap;

import ru.xpendence.development.gimstopwatch.MainActivity;
import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.data.FoodDbHelper;
import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodsArchiveObject;
import ru.xpendence.development.gimstopwatch.fragments.FragmentCharts;

/**
 * Created by promoscow on 04.06.17.
 * Adapter to fill calories/nutrients charts.
 */

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ChartHolder> {

    private final String TAG = this.getClass().getSimpleName();

    private FragmentCharts fragmentCharts;

    /**
     * Создаём два архива.
     * Первый — содержит объекты, значения которых потом пойдут в таблицу и Bitmap.
     * Второй — содержит Bitmap'ы.
     */
    private ArrayList<GoodsArchiveObject> archivesForCharts = new ArrayList<>();
    private ArrayList<Bitmap> bitmapsForCharts = new ArrayList<>();

    public ChartAdapter(FragmentCharts fragmentCharts, FragmentActivity activity) {
        this.fragmentCharts = fragmentCharts;
        int index = 0;
        TreeMap<String, GoodsArchiveObject> goods = new TreeMap<>(FoodStuffsData.archiveStrings);
        for (String s : goods.keySet()) {
            System.out.println(s);
        }
        for (String s : goods.keySet()) {
            System.out.println(s);
        }
        Log.d(TAG, String.valueOf(goods.size()));
        for (String key : FoodStuffsData.archiveStrings.keySet()) {
            Log.d(TAG, "index - " + String.valueOf(index));
            archivesForCharts.add(index, goods.get(key));
            if (goods.get(key).getCalories() > 0) {
                bitmapsForCharts.add(index++,
                        new ChartsGraphicsFactory(activity).createChartImage(goods.get(key)));
            } else {
                Log.e(goods.get(key).getDate(), goods.get(key).toString());
                bitmapsForCharts.add(index++,
                        new ChartsGraphicsFactory(activity).createBlankImage(goods.get(key)));
            }
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
            holder.chartDataImage.setImageBitmap(MainActivity.transparentBitmap);
        } else {
//            holder.chartDataImage.setImageResource(FoodStuffsData.setImageResourseForCharts(position));
            try {
                holder.chartDataImage.setImageBitmap(bitmapsForCharts.get(position - 3));
                holder.chartDataImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 06.06.17 Проблема в том, что я инициализирую таблицу последней позицией.
                        // TODO: 06.06.17 Отправлять весь архив и работать с ним.
                        fragmentCharts.setChartHeader(archivesForCharts.get(position - 3).getDate());
                        fragmentCharts.showTable(archivesForCharts, position - 3);
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
        }
    }
}
