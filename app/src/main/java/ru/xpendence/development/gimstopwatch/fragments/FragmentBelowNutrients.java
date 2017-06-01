package ru.xpendence.development.gimstopwatch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.util.CommonSettings;
import ru.xpendence.development.gimstopwatch.util.NutrientsHelper;
import ru.xpendence.development.gimstopwatch.util.PersonalData;
import ru.xpendence.development.gimstopwatch.util.TextHelper;

/**
 * Created by promoscow on 24.05.17.
 * Fragment with text below balance of nutrients.
 */

public class FragmentBelowNutrients extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_below_nutrients, container, false);

        if (PersonalData.getTotalDailyNutrients() > 0) {
            final TextView nutrientsHeader = (TextView) view.findViewById(R.id.nutrients_header);
            nutrientsHeader.setTypeface(CommonSettings.getRobotoCondLight());
            nutrientsHeader.setText(R.string.nutrients_daily);

            final TextView nutrientsProteins = (TextView) view.findViewById(R.id.nutrients_proteins);
            nutrientsProteins.setTypeface(CommonSettings.getRobotoCondLight());

            final TextView nutrientsFats = (TextView) view.findViewById(R.id.nutrients_fat);
            nutrientsFats.setTypeface(CommonSettings.getRobotoCondLight());

            final TextView nutrientsCarbohydrates = (TextView) view.findViewById(R.id.nutrients_carbohydrates);
            nutrientsCarbohydrates.setTypeface(CommonSettings.getRobotoCondLight());

            final TextView nutrientsLower = (TextView) view.findViewById(R.id.nutrients_lower);
            nutrientsLower.setTypeface(CommonSettings.getRobotoCondLight());

            final TextView dailyCaloriesText;

            String getProteins = TextHelper.getProteins();
            String result = String.format("%s  гр. (%d%% от общей нормы нутриентов)",
                    getProteins, NutrientsHelper.getProteinsPercent());
            dailyCaloriesText = (TextView) view.findViewById(R.id.nutrients_proteins_values);
            dailyCaloriesText.setTextSize(16);
            dailyCaloriesText.setTypeface(CommonSettings.getRobotoCondLight());
            dailyCaloriesText.setText(result);

            final TextView caloriesPercentText;

            String getFats = TextHelper.getFats();
            String resultFats = String.format("%s  гр. (%d%% от общей нормы нутриентов)",
                    getFats, NutrientsHelper.getFatsPercent());
            caloriesPercentText = (TextView) view.findViewById(R.id.nutrients_fat_values);
            caloriesPercentText.setTextSize(16);
            caloriesPercentText.setTypeface(CommonSettings.getRobotoCondLight());
            caloriesPercentText.setText(resultFats);

            final TextView goalCaloriesText;

            String getCarbons = TextHelper.getCarboHydrates();
            String resultCarbons = String.format("%s  гр. (%d%% от общей нормы нутриентов)",
                    getCarbons, NutrientsHelper.getCarbohydratesPercent());
            goalCaloriesText = (TextView) view.findViewById(R.id.nutrients_carbohydrates_values);
            goalCaloriesText.setTextSize(16);
            goalCaloriesText.setTypeface(CommonSettings.getRobotoCondLight());
            goalCaloriesText.setText(resultCarbons);
        } else {
            final TextView nutrientsHeader = (TextView) view.findViewById(R.id.nutrients_header);
            nutrientsHeader.setTypeface(CommonSettings.getRobotoCondLight());
            nutrientsHeader.setText(R.string.no_data);

            TableLayout nutrientsTable = (TableLayout) view.findViewById(R.id.nutrients_table);
            nutrientsTable.setVisibility(View.GONE);

            final TextView nutrientsLower = (TextView) view.findViewById(R.id.nutrients_lower);
            nutrientsLower.setTypeface(CommonSettings.getRobotoCondLight());
        }

        return view;
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentBelowNutrients fragment = new FragmentBelowNutrients();
        fragment.setArguments(args);
        return fragment;
    }
}
