package ru.xpendence.development.gimstopwatch.util;

import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;

/**
 * Created by promoscow on 24.05.17.
 * Helps to generate all texts.
 */

public class TextHelper {

    public static String getDailyCalories() {
        return String.format("С начала дня Вы употребили %d кКал.", FoodStuffsData.dailyCaloriesSummary);
    }

    public static String getCaloriesPercent() {
        return String.format("Это составляет %d%% от Вашей суточной нормы.", ((int) ((double) FoodStuffsData.dailyCaloriesSummary / (double) PersonalData.goalCalories * 100)));
    }

    public static String getGoalCalories() {
        return String.format("Ваша дневная норма: %d кКал.", PersonalData.getGoalCalories());
    }

    public static String getProteins() {
        return String.valueOf((int) PersonalData.getDailyProteins());
    }

    public static String getFats() {
        return String.valueOf((int) PersonalData.getDailyFats());
    }

    public static String getCarboHydrates() {
        return String.valueOf((int) PersonalData.getDailyCarbohydrates());
    }
}
