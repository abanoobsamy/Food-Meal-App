package com.abanoob_samy.foodmealapp.view.details;

import com.abanoob_samy.foodmealapp.Utils;
import com.abanoob_samy.foodmealapp.models.Meals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPresenter {

    private DetailsView detailsView;

    public DetailsPresenter(DetailsView detailsView) {
        this.detailsView = detailsView;
    }

    public void getMealsBySearch(String mealName) {

        detailsView.showLoading();

        Call<Meals> mealCall = Utils.getFoodApi().getMealBySearch(mealName);
        mealCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {

                //whatever the result, only this process will happen.
                detailsView.hideLoading();

                if (response.isSuccessful() && response.body() != null) {

                    detailsView.setMeal(response.body().getMeals().get(0));
                }
                else {
                    detailsView.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {

                //here should hide.
                detailsView.hideLoading();

                detailsView.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
