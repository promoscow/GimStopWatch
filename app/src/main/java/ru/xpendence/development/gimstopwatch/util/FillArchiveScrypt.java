package ru.xpendence.development.gimstopwatch.util;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.Random;

import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodInDayRation;

import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.archiveCharts;
import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.archiveRations;
import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.dailyGoods;

/**
 * Created by promoscow on 04.06.17.
 * Temporary scrypt. Delete before release.
 */

// TODO: 04.06.17 Удалить перед релизом.

public class FillArchiveScrypt {
    Context context;

    public FillArchiveScrypt(Context baseContext) {
        context = baseContext;
    }

    public void fillArchiveWithDefaultData() {

        Random random = new Random();
        String[] goods = {"Букатини", "Верблюжатина вареная", "Гейнер IronMaxx Titan V.2.0",
        "Гейнер Optimum Pro Complex Gainer", "Горбуша горячего копчения", "Гречневая каша Nestle молочная",
        "Гусь", "Десерт Чизкейк Нью Йорк Клубника KFC", "Дыня", "Жир рыбий", "Зубатка отварная",
        "Йогурт Агуша детский с черносливом 2.6%", "Йогурт Активиa Клубника", "Йогурт Растишка с клубникой и печеньем",
        "Кабачковая икра", "Каймак", "Капуста романеско Бондюэль", "Картофель фиолетовый",
        "Кетчуп Heinz острый", "Кинза", "Клубника", "Колбаса варено-копченая любительская",
        "Колбаса варено-копченая сервелат российский", "Колбаса сырокопченая зернистая",
        "Корлан", "Кофейный напиток iCafe Iced French Vanilla Coffe"};

        int x = 0;

        for (int i = 0; i < 5;) {
            dailyGoods.clear();
            for (int j = 0; j < 5; j++) {
//                int index = random.nextInt(4000);
//                Log.d("index", String.valueOf(index));
//                String a = FoodStuffsData.goods.get(String.valueOf(index)).getName();
//                Log.d("key", a);
                GoodInDayRation goodInDayRation = new GoodInDayRation(context, goods[x++], 100);
                System.out.println(goodInDayRation.toString());
                FoodStuffsData.dailyGoods.add(goodInDayRation);
            }
            String s = "0" + ++i + "_06_2017";
            Log.d("dailyGoods", s + " / " + dailyGoods.toString());
            archiveRations.put(s, dailyGoods);
            archiveCharts.put(s, new ChartsGraphicsFactory(context).createChartImage(dailyGoods));
        }
    }
}
