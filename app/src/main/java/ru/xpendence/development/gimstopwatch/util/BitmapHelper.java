package ru.xpendence.development.gimstopwatch.util;

/**
 * Created by promoscow on 17.05.17.
 */

import android.icu.text.LocaleDisplayNames;
import android.util.Log;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;

public class BitmapHelper {

    public static final int CANVAS_COLOR = 0xFF607D8B;
    public static final int BACKGROUND_COLOR = 0xffcde2ec;

    public static int getCalContainerFill() {
        double result = (double) FoodStuffsData.dailyCaloriesSummary / (double) PersonalData.goalCalories;
        Log.e("containerResult", String.valueOf(result));

        return (result == 0)
                ? R.drawable.cal_container_200 : (result < 0.15)
                ? R.drawable.cal_container_complete_10 : (result < 0.25)
                ? R.drawable.cal_container_complete_20 : (result < 0.35)
                ? R.drawable.cal_container_complete_30 : (result < 0.45)
                ? R.drawable.cal_container_complete_40 : (result < 0.55)
                ? R.drawable.cal_container_complete_50 : (result < 0.65)
                ? R.drawable.cal_container_complete_60 : (result < 0.75)
                ? R.drawable.cal_container_complete_70 : (result < 0.85)
                ? R.drawable.cal_container_complete_80 : (result < 0.95)
                ? R.drawable.cal_container_complete_90 : R.drawable.cal_container_complete_100;
    }

    public static int setFillcolor() {
        return ((100 * FoodStuffsData.dailyCaloriesSummary / PersonalData.getGoalCalories()) < 30)
                ? NutrientsHelper.getGREEN()
                : ((100 * FoodStuffsData.dailyCaloriesSummary / PersonalData.getGoalCalories()) < 70)
                ?  NutrientsHelper.getYELLOW()
                : NutrientsHelper.getRED();
    }
}
