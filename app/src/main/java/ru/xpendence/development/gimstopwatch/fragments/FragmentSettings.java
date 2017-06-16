package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.util.CommonSettings;
import ru.xpendence.development.gimstopwatch.util.PersonalData;

/**
 * Created by promoscow on 01.06.17.
 * Fragment of settings.
 */

public class FragmentSettings extends Fragment {

    PersonalData data;

    TextView userName;
    TextView userAge;
    TextView userWeight;
    TextView goalCalories;

    TextView userNameValue;
    TextView userAgeValue;
    TextView userWeightValue;
    TextView goalCaloriesValue;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);

        data = new PersonalData();

        userName = (TextView) view.findViewById(R.id.settings_user_name);
        userName.setTypeface(CommonSettings.getRobotoCondLight());
        userAge = (TextView) view.findViewById(R.id.settings_user_age);
        userAge.setTypeface(CommonSettings.getRobotoCondLight());
        userWeight = (TextView) view.findViewById(R.id.settings_user_weight);
        userWeight.setTypeface(CommonSettings.getRobotoCondLight());
        goalCalories = (TextView) view.findViewById(R.id.settings_goal_calories);
        goalCalories.setTypeface(CommonSettings.getRobotoCondLight());

        userNameValue = (TextView) view.findViewById(R.id.settings_user_name_value);
        userNameValue.setTypeface(CommonSettings.getRobotoCondLight());
        userAgeValue = (TextView) view.findViewById(R.id.settings_user_age_value);
        userAgeValue.setTypeface(CommonSettings.getRobotoCondLight());
        userWeightValue = (TextView) view.findViewById(R.id.settings_user_weight_value);
        userWeightValue.setTypeface(CommonSettings.getRobotoCondLight());
        goalCaloriesValue = (TextView) view.findViewById(R.id.settings_goal_calories_value);
        goalCaloriesValue.setTypeface(CommonSettings.getRobotoCondLight());

        userNameValue.setText(PersonalData.getName());
        userAgeValue.setText(String.valueOf(PersonalData.getAge()));
        userWeightValue.setText(String.valueOf(PersonalData.getWeight()));
        goalCaloriesValue.setText(String.valueOf(PersonalData.getGoalCalories()));

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
