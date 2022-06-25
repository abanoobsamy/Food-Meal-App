package com.abanoob_samy.foodmealapp.view.category;

import com.abanoob_samy.foodmealapp.Utils;
import com.abanoob_samy.foodmealapp.models.Meals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenter {

    private CategoryView categoryView;

    public CategoryPresenter(CategoryView categoryView) {
        this.categoryView = categoryView;
    }

    public void getMealByCategory(String category) {

        categoryView.showLoading();

        Call<Meals> mealsCall = Utils.getFoodApi().getMealByCategory(category);
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {

                //whatever the result, only this process will happen.
                categoryView.hideLoading();

                if (response.isSuccessful() && response.body() != null) {

                    categoryView.setMeal(response.body().getMeals());
                }
                else {

                    categoryView.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {

                //here should hide.
                categoryView.hideLoading();

                categoryView.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
