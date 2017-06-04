package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.util.RVAdapter;

/**
 * Created by promoscow on 02.06.17.
 * Information about food, eaten today.
 */

public class FragmentFoodsInfo extends Fragment {
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foods_info, container, false);

        RecyclerView foodsInfo = (RecyclerView) view.findViewById(R.id.foods_info);
        foodsInfo.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        foodsInfo.setLayoutManager(linearLayoutManager);

        RVAdapter adapter = new RVAdapter();
        foodsInfo.setAdapter(adapter);

        return view;
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentFoodsInfo fragment = new FragmentFoodsInfo();
        fragment.setArguments(args);
        return fragment;
    }
}
