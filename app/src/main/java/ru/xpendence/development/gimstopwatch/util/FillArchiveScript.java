package ru.xpendence.development.gimstopwatch.util;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodInDayRation;

import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.archiveCharts;
import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.archiveRations;
import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.dailyGoods;

/**
 * Created by promoscow on 04.06.17.
 * Temporary script. Delete before release.
 */

// TODO: 04.06.17 Удалить перед релизом.

public class FillArchiveScript {
    private Context context;
    private ArrayList<GoodInDayRation> dGoods;

    public FillArchiveScript(Context baseContext, ArrayList<GoodInDayRation> dailyGoods) {
        context = baseContext;
        dGoods = dailyGoods;
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
        "Корлан", "Кофейный напиток iCafe Iced French Vanilla Coffe", "Куриная печень",
        "Куриный рулет", "Лаваш шпинатный", "Ликёр Kahlua", "Мармелад жевательный Бон Пари Айс Крим",
        "Мармелад жевательный Бон Пари Кола", "Мармелад жевательный Фру-фру Цветные акулы",
        "Масло арахисовое", "Масло облепиховое", "Масло сливочное крестьянское несолёное 72,5%",
        "Мини-печенье Медвежонок Барни для завтрака медовое", "Мини круассаны 7 Days с кокосовым кремом",
        "Молоко 3.2%", "Молоко белковое", "Морепродукты консервированные", "Мороженое Nestle Maxibon Страчателла",
        "Мороженое молочное", "Морская капуста маринованная", "Мука пшеничная из твёрдых сортов Durum",
        "Мускатный цвет (мацис)", "Набор для глинтвейна Глинтмейстер Красная жара",
        "Наггетсы куриные Мираторг с ветчиной", "Напиток BSN Syntha-6 Protein Shake",
        "Напиток Nutridrink со вкусом ванили", "Овощная смесь Hortex лечо"};

        int x = 0;

        for (int i = 0; i < 9;) {
            for (int j = 0; j < 5; j++) {
//                int index = random.nextInt(4000);
//                Log.d("index", String.valueOf(index));
//                String a = FoodStuffsData.goods.get(String.valueOf(index)).getName();
//                Log.d("key", a);
                GoodInDayRation goodInDayRation = new GoodInDayRation(context, goods[x++], 100);
                System.out.println(goodInDayRation.toString());
                dGoods.add(goodInDayRation);
            }
            String s = "2017_06_0" + ++i;
            Log.d("dailyGoods", s + " / " + dGoods.toString());
            ArrayList<GoodInDayRation> temp = (ArrayList<GoodInDayRation>) dGoods.clone();
            archiveRations.put(s, temp);
            Log.i("ARCHIVE_RATIONS", String.valueOf(archiveRations.get(s)));
            archiveCharts.put(s, new ChartsGraphicsFactory(context).createChartImage(temp));
            // TODO: 06.06.17 Стирает запись из archiveRations!!!
//            for (int j = 0; j < dGoods.size(); j++) {
//                dGoods.remove(j);
//            }
            dGoods.clear();
            Log.i("ARCHIVE_RATIONS_AFTER", String.valueOf(archiveRations.get(s)));
            Log.e("dailyGoods.cleared", String.valueOf(dGoods));
        }
        Log.i("archiveRations.final", String.valueOf(archiveRations));
        Log.i("archiveCharts.final", String.valueOf(archiveCharts));
    }
}
