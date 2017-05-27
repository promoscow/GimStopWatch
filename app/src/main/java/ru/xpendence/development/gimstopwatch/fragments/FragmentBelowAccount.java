package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.xpendence.development.gimstopwatch.R;

/**
 * Created by promoscow on 25.05.17.
 * Second fragment in activity, not displayed.
 */

public class FragmentBelowAccount extends Fragment {

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_below_account, container, false);
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentBelowAccount fragment = new FragmentBelowAccount();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBelowAccount() {}
}
