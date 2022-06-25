package com.abanoob_samy.foodmealapp;

import android.app.AlertDialog;
import android.content.Context;

import com.abanoob_samy.foodmealapp.api.FoodApi;
import com.abanoob_samy.foodmealapp.api.FoodClient;

public class Utils {

    public static FoodApi getFoodApi() {
        return FoodClient.getFoodClient().create(FoodApi.class);
    }

    public static AlertDialog showDialogMessage(Context context, String title, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .show();

        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }

        return alertDialog;
    }
}
