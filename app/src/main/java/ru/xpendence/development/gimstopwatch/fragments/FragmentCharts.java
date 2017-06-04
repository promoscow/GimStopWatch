package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.util.ChartAdapter;

/**
 * Created by promoscow on 03.06.17.
 * Fragment for charts (nutrients, calories) by days.
 */

public class FragmentCharts extends Fragment {
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);

        RecyclerView rationsChart = (RecyclerView) view.findViewById(R.id.rations_chart);
        rationsChart.setHasFixedSize(true);

        LinearLayoutManager rationsChartLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                true);
        rationsChart.setLayoutManager(rationsChartLayoutManager);

        ChartAdapter chartAdapter = new ChartAdapter();
        rationsChart.setAdapter(chartAdapter);

        return view;
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentCharts fragment = new FragmentCharts();
        fragment.setArguments(args);
        return fragment;
    }
}

