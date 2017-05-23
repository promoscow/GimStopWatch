package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.xpendence.development.gimstopwatch.R;

/**
 * Created by promoscow on 23.05.17.
 */

public class FragmentBelowFillDayRate extends Fragment {

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_below_fill_day_rate, container, false);
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentBelowFillDayRate fragment = new FragmentBelowFillDayRate();
        fragment.setArguments(args);
        return fragment;
    }
}
