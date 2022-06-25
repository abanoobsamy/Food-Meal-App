package com.abanoob_samy.foodmealapp.database;

import android.content.Context;

import com.abanoob_samy.foodmealapp.dao.MealsDao;
import com.abanoob_samy.foodmealapp.models.Meals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Meals.Meal.class, version = 1, exportSchema = false)
public abstract class MealsRoomDatabase extends RoomDatabase {

    public abstract MealsDao getMealsDao();

    private static volatile MealsRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized MealsRoomDatabase getMealsDatabase(Context context) {

        if (INSTANCE == null) {

            synchronized (MealsRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MealsRoomDatabase.class, "database_meals").build();
                }
            }
        }

        return INSTANCE;
    }

}
