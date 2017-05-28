package ru.xpendence.development.gimstopwatch.foodstuffs;

import java.util.Date;

/**
 * Created by promoscow on 28.05.17.
 */

public class GoodInDayRation {
    private String name;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private int calories;
    private String category;
    private int amount;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins, int amount) {
        this.proteins = proteins / 100 * amount;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats, int amount) {
        this.fats = fats / 100 * amount;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates, int amount) {
        this.carbohydrates = carbohydrates / 100 * amount;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories, int amount) {
        this.calories = calories / 100 * amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodInDayRation that = (GoodInDayRation) o;

        if (Double.compare(that.proteins, proteins) != 0) return false;
        if (Double.compare(that.fats, fats) != 0) return false;
        if (Double.compare(that.carbohydrates, carbohydrates) != 0) return false;
        if (calories != that.calories) return false;
        if (amount != that.amount) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null)
            return false;
        return date != null ? date.equals(that.date) : that.date == null;

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
        result = 31 * result + amount;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GoodInDayRation{" +
                "name='" + name + '\'' +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbohydrates=" + carbohydrates +
                ", calories=" + calories +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
