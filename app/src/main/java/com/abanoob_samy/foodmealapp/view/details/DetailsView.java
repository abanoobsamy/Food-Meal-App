package com.abanoob_samy.foodmealapp.view.details;

import com.abanoob_samy.foodmealapp.models.Meals;

import java.util.List;

public interface DetailsView {

    void showLoading();
    void hideLoading();
    void setMeal(Meals.Meal meal);
    void onErrorLoading(String message);
}
