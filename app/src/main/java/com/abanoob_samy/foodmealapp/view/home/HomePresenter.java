package com.abanoob_samy.foodmealapp.view.home;

import com.abanoob_samy.foodmealapp.Utils;
import com.abanoob_samy.foodmealapp.models.Categories;
import com.abanoob_samy.foodmealapp.models.Meals;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {

    private HomeView homeView;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    public void getMealsBySearch(String mealName) {

        homeView.showLoading();

        Call<Meals> mealsCall = Utils.getFoodApi().getMealBySearch(mealName);
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {

                //whatever the result, only this process will happen.
                homeView.hideLoading();

                if (response.isSuccessful() && response.body() != null) {

                    homeView.setMealBySearch(response.body().getMeals());
                }
                else {
                    homeView.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {

                //here should hide.
                homeView.hideLoading();

                homeView.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    public void getMeals() {

        homeView.showLoading();

        Call<Meals> mealsCall = Utils.getFoodApi().getMeals();
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {

                //whatever the result, only this process will happen.
                homeView.hideLoading();

                if (response.isSuccessful() && response.body() != null) {

                    homeView.setMeal(response.body().getMeals());
                }
                else {
                    homeView.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {

                //here should hide.
                homeView.hideLoading();

                homeView.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    public void getCategories() {

        homeView.showLoading();

        Call<Categories> categoriesCall = Utils.getFoodApi().getCategories();

        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(@NonNull Call<Categories> call, @NonNull Response<Categories> response) {

                //whatever the result, only this process will happen.
                homeView.hideLoading();

                if (response.isSuccessful() && response.body() != null) {

                    homeView.setCategory(response.body().getCategories());
                }
                else {

                    homeView.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Categories> call, @NonNull Throwable t) {

                //here should hide.
                homeView.hideLoading();

                homeView.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
