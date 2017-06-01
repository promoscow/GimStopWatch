package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.util.CommonSettings;
import ru.xpendence.development.gimstopwatch.util.PersonalData;
import ru.xpendence.development.gimstopwatch.util.TextHelper;

/**
 * Created by promoscow on 23.05.17.
 * Fragment with text below fragment with calories daily filling.
 */

public class FragmentBelowFillDayRate extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_below_fill_day_rate, container, false);
        if (FoodStuffsData.getDailyCaloriesSummary() > 0) {
            final TextView dailyCaloriesText;
            dailyCaloriesText = (TextView) view.findViewById(R.id.daily_calories);
            dailyCaloriesText.setTextSize(16);
            dailyCaloriesText.setTypeface(CommonSettings.getRobotoCondLight());
            dailyCaloriesText.setText(TextHelper.getDailyCalories());

            final TextView caloriesPercentText;
            caloriesPercentText = (TextView) view.findViewById(R.id.calories_percent);
            caloriesPercentText.setTextSize(16);
            caloriesPercentText.setTypeface(CommonSettings.getRobotoCondLight());
            caloriesPercentText.setText(TextHelper.getCaloriesPercent());

            final TextView goalCaloriesText = (TextView) view.findViewById(R.id.goal_calories);
            goalCaloriesText.setTextSize(16);
            goalCaloriesText.setTypeface(CommonSettings.getRobotoCondLight());
            goalCaloriesText.setText(TextHelper.getGoalCalories());
        } else {
            final TextView dailyCaloriesText;
            dailyCaloriesText = (TextView) view.findViewById(R.id.daily_calories);
            dailyCaloriesText.setTextSize(16);
            dailyCaloriesText.setTypeface(CommonSettings.getRobotoCondLight());
            dailyCaloriesText.setText(R.string.no_data);
        }

        return view;
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentBelowFillDayRate fragment = new FragmentBelowFillDayRate();
        fragment.setArguments(args);
        return fragment;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
