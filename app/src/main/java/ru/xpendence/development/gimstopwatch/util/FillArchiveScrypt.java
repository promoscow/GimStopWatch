package ru.xpendence.development.gimstopwatch.util;

import android.util.Log;

import java.util.Date;
import java.util.Random;

import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodInDayRation;

import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.archiveRations;
import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.dailyGoods;

/**
 * Created by promoscow on 04.06.17.
 * Temporary scrypt. Delete before release.
 */

// TODO: 04.06.17 Удалить перед релизом.

public class FillArchiveScrypt {

    public static void fillArchiveWithDefaultData() {

        Random random = new Random();

        for (int i = 0; i < 15;) {
            for (int j = 0; j < 3; j++) {
//                int index = random.nextInt(4000);
//                Log.d("index", String.valueOf(index));
//                String a = FoodStuffsData.goods.get(String.valueOf(index)).getName();
//                Log.d("key", a);
                GoodInDayRation goodInDayRation = new GoodInDayRation("Вареники от Ильиной с вишней", (random.nextInt(7) + 1) * 50);
                FoodStuffsData.dailyGoods.add(goodInDayRation);
            }
            String s = "0" + ++i + "_06_2017";
            Log.d("dailyGoods", s + " / " + dailyGoods.toString());
            archiveRations.put(s, dailyGoods);
        }
    }
}
