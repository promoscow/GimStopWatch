package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import ru.xpendence.development.gimstopwatch.R;

/**
 * Created by promoscow on 01.06.17.
 */

public class FragmentSettings extends Fragment {

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);

        return view;
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentSettings fragment = new FragmentSettings();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentSettings() {}

//    public void onStop() {
//        super.onStop();
//        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
//        ViewGroup viewGroup = (FrameLayout) this.getActivity().findViewById(R.id.main_fragment_view);
//        viewGroup.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,
//                (int) (250 * displayMetrics.density)));
//    }
}
