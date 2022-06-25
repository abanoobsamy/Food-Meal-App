package com.abanoob_samy.foodmealapp.view.favorite;

import com.abanoob_samy.foodmealapp.models.Meals;

import java.util.List;

public interface FavoriteView {

    void showLoading();
    void hideLoading();
    void setMeal(List<Meals.Meal> meal);
    void onErrorLoading(String message);
}
