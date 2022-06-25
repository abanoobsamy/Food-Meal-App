package com.abanoob_samy.foodmealapp.view.favorite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abanoob_samy.foodmealapp.R;
import com.abanoob_samy.foodmealapp.adapters.RecyclerMealByCategoryAdapter;
import com.abanoob_samy.foodmealapp.adapters.RecyclerMealByFavoriteAdapter;
import com.abanoob_samy.foodmealapp.databinding.ActivityFavoriteBinding;
import com.abanoob_samy.foodmealapp.models.Meals;
import com.abanoob_samy.foodmealapp.view.details.DetailsActivity;
import com.abanoob_samy.foodmealapp.view.home.HomeActivity;
import com.abanoob_samy.foodmealapp.viewmodel.MealsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private static boolean IS_WATCHLIST_UPDATED = false;

    private ActivityFavoriteBinding binding;
    private MealsViewModel mViewModel;

    private List<Meals.Meal> mealList;
    private RecyclerMealByFavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActionBar();

        mViewModel = new ViewModelProvider(this).get(MealsViewModel.class);

        mealList = new ArrayList<>();

        loadFavorite();
    }

    private void initActionBar() {

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Favorite");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadFavorite() {

        binding.progressFavorite.setVisibility(View.VISIBLE);

        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(mViewModel.getFavorites()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {

                    binding.progressFavorite.setVisibility(View.GONE);

                    if (mealList.size() > 0) {
                        mealList.clear();
                    }

                    mealList.addAll(meal);

                    adapter = new RecyclerMealByFavoriteAdapter(mealList, FavoriteActivity.this);
                    binding.recyclerFavorite.setLayoutManager(new GridLayoutManager(FavoriteActivity.this, 1));
                    binding.recyclerFavorite.setHasFixedSize(true);
                    binding.recyclerFavorite.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    adapter.setClickListenerMealByCategory((view, position) -> {

                        TextView mealName = view.findViewById(R.id.mealName);

                        Intent intent = new Intent(this, DetailsActivity.class);
                        intent.putExtra(HomeActivity.EXTRA_DETAILS, mealName.getText().toString());
                        startActivity(intent);

                        Toast.makeText(this, "Meal " + meal.get(position).getStrMeal(), Toast.LENGTH_SHORT).show();
                    });

                    adapter.setClickListenerFavorite(new ClickListenerFavorite() {
                        @Override
                        public void removeTVShowFromFavorite(Meals.Meal meal, int position) {

                            CompositeDisposable disposableDelete = new CompositeDisposable();
                            disposableDelete.add(mViewModel.removeFromFavorite(meal)
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {

                                        mealList.remove(position);
                                        adapter.notifyItemRemoved(position);
                                        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                                        disposableDelete.dispose();

                                        Toast.makeText(getApplicationContext(), "Deleted Successfully.",
                                                Toast.LENGTH_SHORT).show();
                                    }));
                        }
                    });

                    disposable.dispose();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (IS_WATCHLIST_UPDATED) {

            loadFavorite();
            IS_WATCHLIST_UPDATED = false;
        }
    }

//    @Override
//    public void removeTVShowFromWatchlist(TVShows tvShows, int position) {
//
//        CompositeDisposable disposableDelete = new CompositeDisposable();
//        disposableDelete.add(mViewModel.removeTVShowFromWatchlist(tvShows)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() -> {
//
//                    watchlistTvShows.remove(position);
//                    adapter.notifyItemRemoved(position);
//                    adapter.notifyItemRangeChanged(position, adapter.getItemCount());
//                    disposableDelete.dispose();
//
//                    Toast.makeText(getApplicationContext(), "Deleted Successfully.", Toast.LENGTH_SHORT).show();
//                }));
//
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}