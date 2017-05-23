package ru.xpendence.development.gimstopwatch.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.xpendence.development.gimstopwatch.R;

/**
 * Created by promoscow on 23.05.17.
 */

public class BelowTimer extends Fragment {

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.below_timer, container, false);
    }


    public static Fragment newInstance() {
        Bundle args = new Bundle();
        BelowTimer fragment = new BelowTimer();
        fragment.setArguments(args);
        return fragment;
    }

    public BelowTimer() {}
}