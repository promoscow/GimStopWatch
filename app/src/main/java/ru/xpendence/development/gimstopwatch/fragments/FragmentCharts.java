package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.xpendence.development.gimstopwatch.R;

/**
 * Created by promoscow on 03.06.17.
 */

public class FragmentCharts extends Fragment {
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);

        return view;
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentCharts fragment = new FragmentCharts();
        fragment.setArguments(args);
        return fragment;
    }
}

