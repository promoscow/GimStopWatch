package ru.xpendence.development.gimstopwatch.util;

/**
 * Created by promoscow on 24.05.17.
 * All user personal data is here.
 */

public class PersonalData {
    /**
     * Constant personal data.
     */
    private static String name;
    private static int age;
    private static double weight;

    /**
     * Dinamic personal data.
     */
    static int dailyCalories;
    static int goalCalories;

    /**
     * Daily nutrients data.
     */
    private static double dailyProteins;
    private static double dailyFats;
    private static double dailyCarbohydrates;
    private static double totalDailyNutrients;

    /**
     * Temporary static hardcode block.
     */
    static {
        dailyCalories = 0;
        goalCalories = 2500;

        dailyProteins = 392;
        dailyFats = 198;
        dailyCarbohydrates = 980;
    }

    /**
     * Constant static block.
     */
    static {
        totalDailyNutrients = dailyProteins + dailyFats + dailyCarbohydrates;
    }

    public static double getTotalDailyNutrients() {
        return totalDailyNutrients;
    }

    public static double getDailyProteins() {
        return dailyProteins;
    }

    public static double getDailyFats() {
        return dailyFats;
    }

    public static double getDailyCarbohydrates() {
        return dailyCarbohydrates;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        PersonalData.name = name;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        PersonalData.age = age;
    }

    public static double getWeight() {
        return weight;
    }

    public static void setWeight(double weight) {
        PersonalData.weight = weight;
    }

    public static int getDailyCalories() {
        return dailyCalories;
    }

    public static void setDailyCalories(int dailyCalories) {
        PersonalData.dailyCalories = dailyCalories;
    }

    public static int getGoalCalories() {
        return goalCalories;
    }

    public static void setGoalCalories(int goalCalories) {
        PersonalData.goalCalories = goalCalories;
    }
}
