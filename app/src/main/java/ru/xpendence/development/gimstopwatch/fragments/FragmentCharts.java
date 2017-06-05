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
import ru.xpendence.development.gimstopwatch.util.ChartAdapter;
import ru.xpendence.development.gimstopwatch.util.CommonSettings;

/**
 * Created by promoscow on 03.06.17.
 * Fragment for charts (nutrients, calories) by days.
 */

public class FragmentCharts extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    TextView chartDetailsHeader;
    TableLayout chartsTable;
    TextView calories;
    TextView proteins;
    TextView fats;
    TextView carbohydrates;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);

        RecyclerView rationsChart = (RecyclerView) view.findViewById(R.id.rations_chart);
        rationsChart.setHasFixedSize(true);

        LinearLayoutManager rationsChartLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                true);
        rationsChart.setLayoutManager(rationsChartLayoutManager);

        ChartAdapter chartAdapter = new ChartAdapter(this);
        rationsChart.setAdapter(chartAdapter);

        chartDetailsHeader = (TextView) view.findViewById(R.id.chart_details_heading);
        chartDetailsHeader.setTypeface(CommonSettings.getRobotoCondLight());

        chartsTable = (TableLayout) view.findViewById(R.id.charts_table);
        calories = (TextView) view.findViewById(R.id.calories_value);
        proteins = (TextView) view.findViewById(R.id.proteins_value);
        fats = (TextView) view.findViewById(R.id.fats_value);
        carbohydrates = (TextView) view.findViewById(R.id.carbohydrates_value);

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

    public void setChartHeader(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.US);
        String s = dateFormat.format(date);
        System.out.println(s);
        System.out.println(chartDetailsHeader);
        chartDetailsHeader.setText(s);
    }

    public void showTable(ArrayList<GoodInDayRation> list) {
        String[] result = getValues(list);
        calories.setText(result[0]);
        proteins.setText(result[1]);
        fats.setText(result[2]);
        carbohydrates.setText(result[3]);
        chartsTable.setVisibility(View.VISIBLE);
    }

    private String[] getValues(ArrayList<GoodInDayRation> list) {
        double calories = 0;
        double proteins = 0;
        double fats = 0;
        double carbons = 0;

        for (GoodInDayRation ration : list) {
            calories += ration.getCalories();
            proteins += ration.getProteins();
            fats += ration.getFats();
            carbons += ration.getCarbohydrates();
        }
        Log.e(TAG, String.format("%d, %d, %d, %d", (int) calories, (int) proteins, (int) fats, (int) carbons));

        return new String[] {String.valueOf((int) calories),
                String.valueOf((int) proteins),
                String.valueOf((int) fats),
                String.valueOf((int) carbons)
        };
    }
}

