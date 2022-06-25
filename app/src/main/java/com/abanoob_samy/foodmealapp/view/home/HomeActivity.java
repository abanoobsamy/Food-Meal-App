package com.abanoob_samy.foodmealapp.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.abanoob_samy.foodmealapp.R;
import com.abanoob_samy.foodmealapp.Utils;
import com.abanoob_samy.foodmealapp.adapters.RecyclerHomeAdapter;
import com.abanoob_samy.foodmealapp.adapters.RecyclerMealByCategoryAdapter;
import com.abanoob_samy.foodmealapp.adapters.ViewPagerHeaderAdapter;
import com.abanoob_samy.foodmealapp.databinding.ActivityHomeBinding;
import com.abanoob_samy.foodmealapp.models.Categories;
import com.abanoob_samy.foodmealapp.models.Meals;
import com.abanoob_samy.foodmealapp.view.category.CategoryActivity;
import com.abanoob_samy.foodmealapp.view.details.DetailsActivity;
import com.abanoob_samy.foodmealapp.view.favorite.FavoriteActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private static final String TAG = "HomeActivity";

    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_DETAILS = "details";
    public static final String EXTRA_POSITION = "position";

    private ActivityHomeBinding binding;

    private HomePresenter presenter;

    private Timer timer;

    private RecyclerMealByCategoryAdapter adapter;

    private List<Meals.Meal> mealsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        hideKeyboard();

        mealsList = new ArrayList<>();

        binding.imageFavorite.setOnClickListener(view -> {

            startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
        });

        presenter = new HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();

        binding.etSearchFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {
                    if (!charSequence.toString().trim().isEmpty() && charSequence.toString() != null) {

                        timer = new Timer();

                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                new Handler(Looper.getMainLooper()).post(() -> {

                                    presenter.getMealsBySearch(charSequence.toString());
                                });
                            }
                        }, 800);
                    }
                    else {
                        binding.tvSearchResult.setVisibility(View.GONE);
                        binding.recyclerSearch.setVisibility(View.GONE);
                        mealsList.clear();
                        Toast.makeText(getApplicationContext(), "No Meals Found", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                } catch (NullPointerException e) {

                    binding.tvSearchResult.setVisibility(View.GONE);
                    binding.recyclerSearch.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
//
//                if (timer != null) {
//                    timer.cancel();
//                }
            }
        });

        binding.etSearchFood.requestFocus();
    }

    private void hideKeyboard() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // this code for keyboard show and hide Keyboard.
        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        // this will give us the view, which is currently focus in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently focus then this will protect the app from crash
        if (view != null) {

            // now assign the system service to InputMethodManager
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showLoading() {

        binding.shimmerMeal.getRoot().setVisibility(View.VISIBLE);
        binding.shimmerCategory.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        binding.shimmerMeal.getRoot().setVisibility(View.GONE);
        binding.shimmerCategory.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void setMealBySearch(List<Meals.Meal> meal) {

        binding.tvSearchResult.setVisibility(View.VISIBLE);
        binding.recyclerSearch.setVisibility(View.VISIBLE);

        mealsList = meal;

        adapter = new RecyclerMealByCategoryAdapter(mealsList, this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.recyclerSearch.setLayoutManager(layoutManager);
        binding.recyclerSearch.setHasFixedSize(true);
        binding.recyclerSearch.setClipToPadding(true);
        binding.recyclerSearch.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setClickListenerMealByCategory((view, position) -> {

            TextView mealName = view.findViewById(R.id.mealName);

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(HomeActivity.EXTRA_DETAILS, mealName.getText().toString());
            startActivity(intent);

            Toast.makeText(this, "Meal " + meal.get(position).getStrMeal(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void setMeal(List<Meals.Meal> meal) {

//        String[] arr = {meal.get(0).getStrMealThumb()};
//
//        for (Meals.Meal meals: meal) {
//
//            meal.add(meals);
//        }

        ViewPagerHeaderAdapter adapter = new ViewPagerHeaderAdapter(meal, HomeActivity.this);

        binding.viewPagerHeader.setAdapter(adapter);
        binding.viewPagerHeader.setPadding(18, 0, 18, 0);
        adapter.notifyDataSetChanged();

        adapter.setClickListenerPagerHome((view, position) -> {

            TextView mealName = view.findViewById(R.id.mealName);

            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra(HomeActivity.EXTRA_DETAILS, mealName.getText().toString());
            startActivity(intent);

            Toast.makeText(getApplicationContext(),
                    meal.get(position).getStrMeal(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void setCategory(List<Categories.Category> category) {

        binding.recyclerCategory.setVisibility(View.VISIBLE);

        RecyclerHomeAdapter homeAdapter = new RecyclerHomeAdapter(category, this);
        binding.recyclerCategory.setAdapter(homeAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false);

        binding.recyclerCategory.setLayoutManager(layoutManager);
        binding.recyclerCategory.setNestedScrollingEnabled(true);

        homeAdapter.notifyDataSetChanged();

        homeAdapter.setClickListenerCategory((view, position) -> {

            Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
            intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
            intent.putExtra(EXTRA_POSITION, position);
            startActivity(intent);
        });
    }

    @Override
    public void onErrorLoading(String message) {

        Utils.showDialogMessage(this, "Error ", message);
    }
}