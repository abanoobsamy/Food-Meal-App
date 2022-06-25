package com.abanoob_samy.foodmealapp.view.category;

import com.abanoob_samy.foodmealapp.models.Meals;
import java.util.List;

public interface CategoryView {

    void showLoading();
    void hideLoading();
    void setMeal(List<Meals.Meal> meal);
    void onErrorLoading(String message);
}
