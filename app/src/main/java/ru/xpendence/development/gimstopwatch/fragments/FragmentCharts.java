package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodInDayRation;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodsArchiveObject;
import ru.xpendence.development.gimstopwatch.util.ChartAdapter;
import ru.xpendence.development.gimstopwatch.util.CommonSettings;

/**
 * Created by promoscow on 03.06.17.
 * Fragment for charts (nutrients, calories) by days.
 */

public class FragmentCharts extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    /**
     * Table
     */
    TextView chartDetailsHeader;
    TableLayout chartsTable;

    /**
     * Table labels
     */
    TextView cal;
    TextView pro;
    TextView fat;
    TextView car;

    /**
     * Table values
     */
    TextView calories;
    TextView proteins;
    TextView fats;
    TextView carbohydrates;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);

        RecyclerView rationsChart = (RecyclerView) view.findViewById(R.id.rations_chart);
        rationsChart.setHasFixedSize(true);

        LinearLayoutManager rationsChartLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                true);
        rationsChart.setLayoutManager(rationsChartLayoutManager);

        ChartAdapter chartAdapter = new ChartAdapter(this, getActivity());
        rationsChart.setAdapter(chartAdapter);

        chartDetailsHeader = (TextView) view.findViewById(R.id.chart_details_heading);
        chartDetailsHeader.setTypeface(CommonSettings.getRobotoCondLight());

        cal = (TextView) view.findViewById(R.id.calories);
        cal.setTypeface(CommonSettings.getRobotoCondLight());
        pro = (TextView) view.findViewById(R.id.proteins);
        pro.setTypeface(CommonSettings.getRobotoCondLight());
        fat = (TextView) view.findViewById(R.id.fats);
        fat.setTypeface(CommonSettings.getRobotoCondLight());
        car = (TextView) view.findViewById(R.id.carbohydrates);
        car.setTypeface(CommonSettings.getRobotoCondLight());

        chartsTable = (TableLayout) view.findViewById(R.id.charts_table);
        calories = (TextView) view.findViewById(R.id.calories_value);
        calories.setTypeface(CommonSettings.getRobotoCondLight());
        proteins = (TextView) view.findViewById(R.id.proteins_value);
        proteins.setTypeface(CommonSettings.getRobotoCondLight());
        fats = (TextView) view.findViewById(R.id.fats_value);
        fats.setTypeface(CommonSettings.getRobotoCondLight());
        carbohydrates = (TextView) view.findViewById(R.id.carbohydrates_value);
        carbohydrates.setTypeface(CommonSettings.getRobotoCondLight());

        chartsTable.setVisibility(View.GONE);

        System.out.println("view created");
        return view;
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentCharts fragment = new FragmentCharts();
        fragment.setArguments(args);
        return fragment;
    }

    public void setChartHeader(String date) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.US);
//        String s = dateFormat.format(date);
//        System.out.println(s);

        char[] c = date.toCharArray();
        String s = String.format("%s%s.%s%s.%s%s%s%s", c[6], c[7], c[4], c[5], c[0], c[1], c[2], c[3]);
        chartDetailsHeader.setText(s);
    }

    public void showTable(ArrayList<GoodsArchiveObject> archivesForCharts, int i) {
        System.out.println(i);

        System.out.println(archivesForCharts.get(i));

        String cal = String.valueOf(archivesForCharts.get(i).getCalories());
        calories.setText(cal);
        String pro = String.valueOf((int) archivesForCharts.get(i).getProteins());
        proteins.setText(pro);
        String fat = String.valueOf((int) archivesForCharts.get(i).getFats());
        fats.setText(fat);
        String car = String.valueOf((int) archivesForCharts.get(i).getCarbohydrates());
        carbohydrates.setText(car);
        chartsTable.setVisibility(View.VISIBLE);
    }

//    private String[] getValues(ArrayList<GoodsArchiveObject> list) {
//        double calories = 0;
//        double proteins = 0;
//        double fats = 0;
//        double carbons = 0;
//
//        for (GoodInDayRation ration : list) {
//            calories += ration.getCalories();
//            proteins += ration.getProteins();
//            fats += ration.getFats();
//            carbons += ration.getCarbohydrates();
//        }
//        Log.e(TAG, String.format("%d, %d, %d, %d", (int) calories, (int) proteins, (int) fats, (int) carbons));
//
//        return new String[] {String.valueOf((int) calories),
//                String.valueOf((int) proteins),
//                String.valueOf((int) fats),
//                String.valueOf((int) carbons)
//        };
//    }
}

