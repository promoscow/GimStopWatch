package ru.xpendence.development.gimstopwatch.util;

import android.util.Log;

/**
 * Created by promoscow on 23.05.17.
 * Helper for FragmentNutrientsRatio.
 */

public class NutrientsHelper {

    private static final int BLUE = 0xFF00BFE8;
    private static final int YELLOW = 0xFFFFD55A;
    private static final int GREEN = 0xFF86D55A;
    private static final int RED = 0xFFFF4560;

    private static final int LIGHT_BLUE = 0x3300BFE8;
    private static final int LIGHT_YELLOW = 0x33FFD55A;
    private static final int LIGHT_GREEN = 0x3386D55A;

    private static final int TEXT_BACKGROUND = 0x55FFFFFF;

    public float proteinsAngle;
    public float fatsAngle;
    public float carboHydratesAngle;

    public NutrientsHelper(float proteinsDayNorm, float fatsDayNorm, float carboHydratesDayNorm) {
        float totalNutrients;
        float angleRatio;
        totalNutrients = proteinsDayNorm + fatsDayNorm + carboHydratesDayNorm;
        Log.e("totalNutrients", String.valueOf(totalNutrients));
        angleRatio = 360 / totalNutrients;
        Log.e("angleRatio", String.valueOf(angleRatio));
        proteinsAngle = proteinsDayNorm * angleRatio;
        Log.e("proteinsDayNorm", String.valueOf(proteinsDayNorm));
        Log.e("proteinsAngle", String.valueOf(proteinsAngle));
        fatsAngle = fatsDayNorm * angleRatio;
        carboHydratesAngle = carboHydratesDayNorm * angleRatio;
    }

    public static int getRED() {
        return RED;
    }

    public static int getTextBackground() {
        return TEXT_BACKGROUND;
    }

    public static int getLightBlue() {
        return LIGHT_BLUE;
    }

    public static int getLightYellow() {
        return LIGHT_YELLOW;
    }

    public static int getLightGreen() {
        return LIGHT_GREEN;
    }

    public float getProteinsAngle() {
        return proteinsAngle;
    }

    public float getFatsAngle() {
        return fatsAngle;
    }

    public float getCarboHydratesAngle() {
        return carboHydratesAngle;
    }


    public static int getBLUE() {
        return BLUE;
    }

    public static int getYELLOW() {
        return YELLOW;
    }

    public static int getGREEN() {
        return GREEN;
    }

    public static int getProteinsPercent() {
        return (int) (PersonalData.getDailyProteins() / PersonalData.getTotalDailyNutrients() * 100);
    }

    public static int getFatsPercent() {
        return (int) (PersonalData.getDailyFats() / PersonalData.getTotalDailyNutrients() * 100);
    }

    public static int getCarbohydratesPercent() {
        return (int) (PersonalData.getDailyCarbohydrates() / PersonalData.getTotalDailyNutrients() * 100);
    }
}
