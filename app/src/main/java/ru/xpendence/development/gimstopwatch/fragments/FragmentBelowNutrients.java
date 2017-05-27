package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.util.NutrientsHelper;
import ru.xpendence.development.gimstopwatch.util.TextHelper;

/**
 * Created by promoscow on 24.05.17.
 * Fragment with text below balance of nutrients.
 */

public class FragmentBelowNutrients extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_below_nutrients, container, false);

        final TextView dailyCaloriesText;

        String getProteins = TextHelper.getProteins();
        String result = String.format("%s  гр. (%d%% от общей нормы нутриентов)",
                getProteins, NutrientsHelper.getProteinsPercent());
        dailyCaloriesText = (TextView) view.findViewById(R.id.nutrients_proteins_values);
        dailyCaloriesText.setTextSize(16);
        dailyCaloriesText.setText(result);

        final TextView caloriesPercentText;

        String getFats = TextHelper.getFats();
        String resultFats = String.format("%s  гр. (%d%% от общей нормы нутриентов)",
                getFats, NutrientsHelper.getFatsPercent());
        caloriesPercentText = (TextView) view.findViewById(R.id.nutrients_fat_values);
        caloriesPercentText.setTextSize(16);
        caloriesPercentText.setText(resultFats);

        final TextView goalCaloriesText;

        String getCarbons = TextHelper.getCarboHydrates();
        String resultCarbons = String.format("%s  гр. (%d%% от общей нормы нутриентов)",
                getCarbons, NutrientsHelper.getCarbohydratesPercent());
        goalCaloriesText = (TextView) view.findViewById(R.id.nutrients_carbohydrates_values);
        goalCaloriesText.setTextSize(16);
        goalCaloriesText.setText(resultCarbons);

        return view;
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentBelowNutrients fragment = new FragmentBelowNutrients();
        fragment.setArguments(args);
        return fragment;
    }
}
