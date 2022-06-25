package com.abanoob_samy.foodmealapp.view.favorite;

import com.abanoob_samy.foodmealapp.Utils;
import com.abanoob_samy.foodmealapp.models.Meals;

import retrofit2.Call;

public class FavoritePresenter {

    private FavoriteView favoriteView;

    public FavoritePresenter(FavoriteView favoriteView) {
        this.favoriteView = favoriteView;
    }

    public void getMealsFavorite() {

//        Call<Meals.Meal> mealCall = Utils.getFoodApi().getMeals();
    }
}
