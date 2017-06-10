package ru.xpendence.development.gimstopwatch.foodstuffs;

/**
 * Created by promoscow on 26.05.17.
 */

public class Good {

    private String name;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private int calories;
    private String category;

    public Good(String name, double proteins, double fats, double carbohydrates, int calories,
                String category) {
        this.name = name;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.category = category;
    }

    public Good(String string, String format, String format1, String format2, int anInt, String string1) {
    }

    @Override
    public String toString() {
        return "Good{" +
                "name='" + name + '\'' +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbohydrates=" + carbohydrates +
                ", calories=" + calories +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Good good = (Good) o;

        if (Double.compare(good.proteins, proteins) != 0) return false;
        if (Double.compare(good.fats, fats) != 0) return false;
        if (Double.compare(good.carbohydrates, carbohydrates) != 0) return false;
        if (calories != good.calories) return false;
        if (name != null ? !name.equals(good.name) : good.name != null) return false;
        return category != null ? category.equals(good.category) : good.category == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(proteins);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fats);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(carbohydrates);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + calories;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
