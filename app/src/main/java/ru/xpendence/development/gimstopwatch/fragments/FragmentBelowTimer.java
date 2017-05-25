package ru.xpendence.development.gimstopwatch.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import ru.xpendence.development.gimstopwatch.R;

/**
 * Created by promoscow on 23.05.17.
 */

public class FragmentBelowTimer extends Fragment {

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_below_timer, container, false);
//        final DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
//        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            public void onGlobalLayout() {
//                Log.i(this.getClass().getSimpleName(), String.valueOf(displayMetrics.heightPixels));
//                view.setLayoutParams(new FrameLayout.LayoutParams(displayMetrics.widthPixels, 1));
//            }
//        });
        return view;
    }


    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentBelowTimer fragment = new FragmentBelowTimer();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBelowTimer() {}
}
