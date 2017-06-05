package ru.xpendence.development.gimstopwatch.foodstuffs;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.xpendence.development.gimstopwatch.R;

/**
 * Created by promoscow on 28.05.17.
 * Class to store all information about foods.
 */

public class FoodStuffsData {

    private static final String TAG = "FoodStuffData";

    /** Каталог продуктов */
    public static Map<String, Good> goods;

    /** Список продуктов для поиска */
    public static List<String> goodsList = new ArrayList<>();

    /** Здесь хранятся рационы за сегодняшний день. */
    public static ArrayList<GoodInDayRation> dailyGoods = new ArrayList<>();

    // TODO: 05.06.17 Поменять на Map<Bitmap, GoodInDayRation>. Дата как имя Bitmap.
    /** Сюда складываем архивные рационы.
     * String — дата
     * ArrayList — дневной рацион
     */
    public static Map<String, ArrayList<GoodInDayRation>> archiveRations = new HashMap<>();
    public static Map<String, Bitmap> archiveCharts = new HashMap<>();

    public static int count = 0;

    public static int getDailyCaloriesSummary() {
        return dailyCaloriesSummary;
    }

    public static int dailyCaloriesSummary = 0;

    public static void setDailyCaloriesSummary(int calories) {
        dailyCaloriesSummary += calories;
        Log.d(TAG, String.valueOf(dailyCaloriesSummary));
    }


    public static int setImageResourseForCharts(int position) {
        switch (position) {
            case 0:
                return R.drawable.chart_01_06_2017;
            case 1:
                return R.drawable.chart_02_06_2017;
            case 2:
                return R.drawable.chart_03_06_2017;
            case 3:
                return R.drawable.chart_01_06_2017;
            case 4:
                return R.drawable.chart_02_06_2017;
            case 5:
                return R.drawable.chart_03_06_2017;
            case 6:
                return R.drawable.chart_01_06_2017;
            case 7:
                return R.drawable.chart_02_06_2017;
            case 8:
                return R.drawable.chart_03_06_2017;
            case 9:
                return R.drawable.chart_01_06_2017;
            case 10:
                return R.drawable.chart_02_06_2017;
            case 11:
                return R.drawable.chart_03_06_2017;
            case 12:
                return R.drawable.chart_01_06_2017;
            case 13:
                return R.drawable.chart_02_06_2017;
            case 14:
                return R.drawable.chart_03_06_2017;
        }
        return R.drawable.testing1;
    }
}
