package ru.xpendence.development.gimstopwatch.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.xpendence.development.gimstopwatch.R;

/**
 * Created by promoscow on 23.05.17.
 * Fragment with text below timer.
 */

public class FragmentBelowTimer extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_below_timer, container, false);
    }


    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentBelowTimer fragment = new FragmentBelowTimer();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBelowTimer() {}
}
