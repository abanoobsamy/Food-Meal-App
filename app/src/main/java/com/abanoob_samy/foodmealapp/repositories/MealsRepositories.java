package com.abanoob_samy.foodmealapp.repositories;

import android.app.Application;

import com.abanoob_samy.foodmealapp.dao.MealsDao;
import com.abanoob_samy.foodmealapp.database.MealsRoomDatabase;
import com.abanoob_samy.foodmealapp.models.Meals;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class MealsRepositories {

    private MealsDao mealsDao;
    private LiveData<List<Meals.Meal>> liveDataMeals;

    public MealsRepositories(Application application) {

        MealsRoomDatabase database = MealsRoomDatabase.getMealsDatabase(application);

        mealsDao = database.getMealsDao();

        liveDataMeals = mealsDao.getAlphabetizedMeals();
    }

    public LiveData<List<Meals.Meal>> getLiveDataMeals() {
        return liveDataMeals;
    }

    public Completable addToFavorite(Meals.Meal meal) {

//        MealsRoomDatabase.databaseWriteExecutor.execute(() -> {
//
//            mealsDao.insertMeals(meal);
//        });

        return mealsDao.insertMeals(meal);
    }

    public Flowable<List<Meals.Meal>> getFavorites() {

        return mealsDao.getFavorites();
    }

    public Completable removeFromFavorite(Meals.Meal meal) {

        return mealsDao.removeFromFavorite(meal);
    }

    public Flowable<Meals.Meal> getMealsFromFavorite(String melaId) {

        return mealsDao.getMealsFromFavorite(melaId);
    }
}
