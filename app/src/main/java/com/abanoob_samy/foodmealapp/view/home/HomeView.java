package com.abanoob_samy.foodmealapp.view.home;

import com.abanoob_samy.foodmealapp.models.Categories;
import com.abanoob_samy.foodmealapp.models.Meals;
import java.util.List;

public interface HomeView {

    void showLoading();
    void hideLoading();
    void setMealBySearch(List<Meals.Meal> meal);
    void setMeal(List<Meals.Meal> meal);
    void setCategory(List<Categories.Category> category);
    void onErrorLoading(String message);
}
