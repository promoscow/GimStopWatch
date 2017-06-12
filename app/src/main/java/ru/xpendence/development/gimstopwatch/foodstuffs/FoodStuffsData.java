package ru.xpendence.development.gimstopwatch.foodstuffs;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import ru.xpendence.development.gimstopwatch.data.FoodDbHelper;
import ru.xpendence.development.gimstopwatch.fragments.FragmentFillDayRate;
import ru.xpendence.development.gimstopwatch.util.PersonalData;

/**
 * Created by promoscow on 28.05.17.
 * Class to store all information about foods.
 */

public class FoodStuffsData {

    private static final String TAG = "FoodStuffData";

    public static String date;

    static {
        if (date == null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
            date = dateFormat.format(new Date());
        }
    }

    /** Каталог продуктов */
    public static Map<String, Good> goods;

    /** Список продуктов для поиска */
    public static List<String> goodsList = new ArrayList<>();

    /** Здесь хранятся рационы за сегодняшний день. */
    public static ArrayList<GoodInDayRation> dailyGoods = new ArrayList<>();

    /** Сюда складываем архивные рационы.
     * String — дата
     * ArrayList — дневной рацион
     */
    public static Map<String, ArrayList<GoodInDayRation>> archiveRations = new TreeMap<>();
    public static Map<String, Bitmap> archiveCharts = new TreeMap<>();

    /** Архив истории, получаемый из БД */
    public static Map<String, GoodsArchiveObject> archiveStrings = new TreeMap<>();

    public static int count = 0;

    public static int getDailyCaloriesSummary() {
        return dailyCaloriesSummary;
    }

    public static int dailyCaloriesSummary = 0;

    public static void setDailyCaloriesSummary(int calories) {
        dailyCaloriesSummary += calories;
        Log.d(TAG, String.valueOf(dailyCaloriesSummary));
    }

    // TODO: 11.06.17 Прописать логи каждого шага!
    // TODO: 11.06.17 Смотреть логику скрипта и работать от неё!!!
    public static void checkDate(Context context) {
        Log.d(TAG, "enter checkDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
        String todayDate = dateFormat.format(new Date());
        if (!date.equals(todayDate)) {
            Log.d(TAG, "date not equals");
            Log.d(TAG, String.valueOf(dailyGoods.size()));
            for (GoodInDayRation good : dailyGoods) {
                String goodDate = dateFormat.format(good.getDate());
                if (!goodDate.equals(date)) {
                    Log.d(TAG, "dailyGoods.remove(good);");
                    dailyGoods.remove(good);
                }
            }
            Log.d(TAG, String.valueOf(dailyGoods.size()));
            ArrayList<GoodInDayRation> temp = (ArrayList<GoodInDayRation>) dailyGoods.clone();
            archiveRations.put(date, temp);
            FoodDbHelper.writeArchiveToDb(date, temp, context);
            dailyGoods.clear();
            date = todayDate;
        }
    }

    public static void updateSummaries(int i) {
        FoodStuffsData.setDailyCaloriesSummary(dailyGoods.get(i).getCalories());
        PersonalData.setDailyProteins(dailyGoods.get(i).getProteins());
        PersonalData.setDailyFats(dailyGoods.get(i).getFats());
        PersonalData.setDailyCarbohydrates(dailyGoods.get(i).getCarbohydrates());

        PersonalData.setTotalDailyNutrients();
        FragmentFillDayRate.isCaloriesChanged = true;
    }
}
