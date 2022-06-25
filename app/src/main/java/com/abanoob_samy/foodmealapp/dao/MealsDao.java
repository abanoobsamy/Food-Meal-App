package com.abanoob_samy.foodmealapp.dao;

import com.abanoob_samy.foodmealapp.models.Meals;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeals(Meals.Meal meal);

    @Query("SELECT * FROM mealsTable")
    Flowable<List<Meals.Meal>> getFavorites();

    @Delete
    Completable removeFromFavorite(Meals.Meal meal);

    @Query("SELECT * FROM mealsTable WHERE idMeal = :melaId")
    Flowable<Meals.Meal> getMealsFromFavorite(String melaId);

    @Query("DELETE FROM mealsTable")
    void deleteAll();

    @Query("SELECT * FROM mealsTable ORDER BY strMeal ASC")
    LiveData<List<Meals.Meal>> getAlphabetizedMeals();

}
