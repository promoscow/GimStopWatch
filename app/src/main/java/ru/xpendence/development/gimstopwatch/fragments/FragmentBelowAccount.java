package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.xpendence.development.gimstopwatch.R;

/**
 * Created by promoscow on 25.05.17.
 */

public class FragmentBelowAccount extends Fragment {

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_below_account, container, false);

//        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
//        view.setLayoutParams(new ViewGroup.LayoutParams(displayMetrics.widthPixels, 0));
        return view;
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentBelowAccount fragment = new FragmentBelowAccount();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBelowAccount() {}
}
