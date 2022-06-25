package com.abanoob_samy.foodmealapp.viewmodel;

import android.app.Application;

import com.abanoob_samy.foodmealapp.database.MealsRoomDatabase;
import com.abanoob_samy.foodmealapp.models.Meals;
import com.abanoob_samy.foodmealapp.repositories.MealsRepositories;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class MealsViewModel extends AndroidViewModel {

    private MealsRepositories repositories;

    private LiveData<List<Meals.Meal>> liveDataMeals;

    public MealsViewModel(@NonNull Application application) {
        super(application);

        repositories = new MealsRepositories(application);
        liveDataMeals = repositories.getLiveDataMeals();
    }

    public LiveData<List<Meals.Meal>> getLiveDataMeals() {
        return liveDataMeals;
    }

    public Completable addToFavorite(Meals.Meal meal) {

        return repositories.addToFavorite(meal);
    }

    public Flowable<List<Meals.Meal>> getFavorites() {
        return repositories.getFavorites();
    }

    public Completable removeFromFavorite(Meals.Meal meal) {

        return repositories.removeFromFavorite(meal);
    }

    public Flowable<Meals.Meal> getMealsFromFavorite(String melaId) {

        return repositories.getMealsFromFavorite(melaId);
    }

}
