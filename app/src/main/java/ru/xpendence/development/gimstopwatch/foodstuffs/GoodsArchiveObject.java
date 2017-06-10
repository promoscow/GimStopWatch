package ru.xpendence.development.gimstopwatch.foodstuffs;

/**
 * Created by promoscow on 09.06.17.
 * Class to store string data, received from database.
 */

public class GoodsArchiveObject {
    private double proteins;
    private double fats;
    private double carbohydrates;
    private int calories;
    private String date;

    public GoodsArchiveObject(double proteins, double fats,
                              double carbohydrates, int calories, String date) {
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.date = date;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodsArchiveObject that = (GoodsArchiveObject) o;

        return Double.compare(that.proteins, proteins) == 0
                && Double.compare(that.fats, fats) == 0
                && Double.compare(that.carbohydrates, carbohydrates) == 0
                && calories == that.calories
                && (date != null ? date.equals(that.date) : that.date == null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(proteins);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fats);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(carbohydrates);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + calories;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GoodsArchiveObject{" +
                "proteins=" + proteins +
                ", fats=" + fats +
                ", carbohydrates=" + carbohydrates +
                ", calories=" + calories +
                ", date='" + date + '\'' +
                '}';
    }
}
