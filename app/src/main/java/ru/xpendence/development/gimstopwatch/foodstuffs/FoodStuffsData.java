package ru.xpendence.development.gimstopwatch.foodstuffs;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by promoscow on 28.05.17.
 */

public class FoodStuffsData {

    /** Каталог продуктов */
    public static Map<String, Good> goods;

    /** Список продуктов для поиска */
    public static List<String> goodsList = new ArrayList<>();

    /** Здесь хранятся рационы за сегодняшний день. */
    public static SparseArray<GoodInDayRation> dailyGoods = new SparseArray<>();

    /** Сюда складываем архивные рационы. */
    public static Map<String, SparseArray<GoodInDayRation>> archiveRations = new HashMap<>();

    public static int count = 0;
}
