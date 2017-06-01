package ru.xpendence.development.gimstopwatch.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.xpendence.development.gimstopwatch.AppActivity;
import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.util.BitmapHelper;
import ru.xpendence.development.gimstopwatch.util.CommonSettings;
import ru.xpendence.development.gimstopwatch.util.PersonalData;

/**
 * Created by promoscow on 25.05.17.
 * Account fragment.
 */

public class FragmentAccount extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    public ImageView fragmentFillDayRatioImage;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        Log.d(TAG, "enter");

        TextView userName = (TextView) view.findViewById(R.id.user_name);
        userName.setTypeface(CommonSettings.getRobotoCondLight());

        TextView imt = (TextView) view.findViewById(R.id.imt_text);
        imt.setTypeface(CommonSettings.getRobotoCondLight());

        TextView fillCalories = (TextView) view.findViewById(R.id.fill_calories);
        fillCalories.setTypeface(CommonSettings.getRobotoCondLight());

        TextView fillCaloriesGoal = (TextView) view.findViewById(R.id.fill_calories_goal);
        fillCaloriesGoal.setTypeface(CommonSettings.getRobotoCondLight());
        String sFillCaloriesGoal = FoodStuffsData.dailyCaloriesSummary + " / "
                + PersonalData.getGoalCalories() + " гр.";
        fillCaloriesGoal.setText(sFillCaloriesGoal);

        TextView nutrients = (TextView) view.findViewById(R.id.nutrients);
        nutrients.setTypeface(CommonSettings.getRobotoCondLight());

//        Button addFoodButton = (Button) view.findViewById(R.id.add_food_button);
//        addFoodButton.setBackgroundResource(R.drawable.roundbutton);
//
//        Button editButton = (Button) view.findViewById(R.id.edit_food_button);
//        editButton.setBackgroundResource(R.drawable.roundbutton);
//
//        Button removeButton = (Button) view.findViewById(R.id.remove_food_button);
//        removeButton.setBackgroundResource(R.drawable.roundbutton);

//        fragmentFillDayRatioImage = (ImageView) view.findViewById(R.id.calories_fill_ratio_image);
//        fragmentFillDayRatioImage.setImageResource(BitmapHelper.getCalContainerFill());

        showFragmentFilldayRatio(FragmentFillDayRateForAccount.newInstance());
        showFragmentNutrientsPie(FragmentNutrientsRatio.newInstance());

        return view;
    }

    private void showFragmentFilldayRatio(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.calories_fill_ratio_image, fragment)
                .commit();
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentAccount fragment = new FragmentAccount();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentAccount() {}

    public void onStop() {
        super.onStop();
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        ViewGroup viewGroup = (FrameLayout) this.getActivity().findViewById(R.id.main_fragment_view);
        viewGroup.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,
                (int) (250 * displayMetrics.density)));
    }

    void showFragmentNutrientsPie(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nutrients_in_account, fragment)
                .commit();
    }

    public void onResume() {
        super.onResume();
    }
}
