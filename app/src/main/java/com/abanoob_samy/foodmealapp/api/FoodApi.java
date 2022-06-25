package com.abanoob_samy.foodmealapp.api;

import com.abanoob_samy.foodmealapp.models.Categories;
import com.abanoob_samy.foodmealapp.models.Meals;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoodApi {

    //https://www.themealdb.com/api/json/v1/1/random.php

    @GET("random.php")
    Call<Meals> getMeals();

    //https://www.themealdb.com/api/json/v1/1/categories.php

    @GET("categories.php")
    Call<Categories> getCategories();

    //https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood

    @GET("filter.php")
    Call<Meals> getMealByCategory(@Query("c") String category);

    //Search meal by name
    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    @GET("search.php")
    Call<Meals> getMealBySearch(@Query("s") String mealName);
}
