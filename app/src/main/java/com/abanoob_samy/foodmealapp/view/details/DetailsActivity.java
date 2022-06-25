package com.abanoob_samy.foodmealapp.view.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.abanoob_samy.foodmealapp.R;
import com.abanoob_samy.foodmealapp.Utils;
import com.abanoob_samy.foodmealapp.adapters.RecyclerMealByFavoriteAdapter;
import com.abanoob_samy.foodmealapp.dao.MealsDao;
import com.abanoob_samy.foodmealapp.databinding.ActivityDetailsBinding;
import com.abanoob_samy.foodmealapp.models.Meals;
import com.abanoob_samy.foodmealapp.view.category.CategoryFragment;
import com.abanoob_samy.foodmealapp.view.home.HomeActivity;
import com.abanoob_samy.foodmealapp.viewmodel.MealsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements DetailsView {

    private static final String TAG = "DetailsActivity";

    private static boolean IS_FAVORITE_UPDATED = false;

    private ActivityDetailsBinding binding;
    private MealsViewModel mViewModel;

    private boolean isTVShowAvailableInFavorite = false;

    private Meals.Meal mMeal;

    private MenuItem favoriteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupActionBar();

        String mealName = getIntent().getStringExtra(HomeActivity.EXTRA_DETAILS);
//        int position = getIntent().getIntExtra(HomeActivity.EXTRA_POSITION, 0);

        DetailsPresenter presenter = new DetailsPresenter(this);
        presenter.getMealsBySearch(mealName);

        mViewModel = new ViewModelProvider(this).get(MealsViewModel.class);
    }

    private void setupActionBar() {

        setSupportActionBar(binding.toolbar);

        binding.collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.colorWhite));
        binding.collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
        binding.collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void showLoading() {

        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMeal(Meals.Meal meal) {

        Log.w(TAG, meal.getStrMeal());

        mMeal = meal;

        Picasso.get().load(meal.getStrMealThumb()).into(binding.mealThumb);

        binding.collapsingToolbar.setTitle(meal.getStrMeal());

        binding.category.setText(meal.getStrCategory());

        binding.country.setText(meal.getStrArea());

        binding.instructions.setText(meal.getStrInstructions());

        setupActionBar();

        //===

        if (!meal.getStrIngredient1().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient1());
        }
        if (!meal.getStrIngredient2().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient2());
        }
        if (!meal.getStrIngredient3().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient3());
        }
        if (!meal.getStrIngredient4().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient4());
        }
        if (!meal.getStrIngredient5().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient5());
        }
        if (!meal.getStrIngredient6().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient6());
        }
        if (!meal.getStrIngredient7().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient7());
        }
        if (!meal.getStrIngredient8().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient8());
        }
        if (!meal.getStrIngredient9().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient9());
        }
        if (!meal.getStrIngredient10().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient10());
        }
        if (!meal.getStrIngredient11().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient11());
        }
        if (!meal.getStrIngredient12().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient12());
        }
        if (!meal.getStrIngredient13().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient13());
        }
        if (!meal.getStrIngredient14().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient14());
        }
        if (!meal.getStrIngredient15().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient15());
        }
        if (!meal.getStrIngredient16().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient16());
        }
        if (!meal.getStrIngredient17().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient17());
        }
        if (!meal.getStrIngredient18().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient18());
        }
        if (!meal.getStrIngredient19().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient19());
        }
        if (!meal.getStrIngredient20().isEmpty()) {
            binding.ingredient.append("\n \u2022 " + meal.getStrIngredient20());
        }

        //===

        if (!meal.getStrMeasure1().isEmpty() && !Character.isWhitespace(meal.getStrMeasure1().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure1());
        }
        if (!meal.getStrMeasure2().isEmpty() && !Character.isWhitespace(meal.getStrMeasure2().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure2());
        }
        if (!meal.getStrMeasure3().isEmpty() && !Character.isWhitespace(meal.getStrMeasure3().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure3());
        }
        if (!meal.getStrMeasure4().isEmpty() && !Character.isWhitespace(meal.getStrMeasure4().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure4());
        }
        if (!meal.getStrMeasure5().isEmpty() && !Character.isWhitespace(meal.getStrMeasure5().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure5());
        }
        if (!meal.getStrMeasure6().isEmpty() && !Character.isWhitespace(meal.getStrMeasure6().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure6());
        }
        if (!meal.getStrMeasure7().isEmpty() && !Character.isWhitespace(meal.getStrMeasure7().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure7());
        }
        if (!meal.getStrMeasure8().isEmpty() && !Character.isWhitespace(meal.getStrMeasure8().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure8());
        }
        if (!meal.getStrMeasure9().isEmpty() && !Character.isWhitespace(meal.getStrMeasure9().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure9());
        }
        if (!meal.getStrMeasure10().isEmpty() && !Character.isWhitespace(meal.getStrMeasure10().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure10());
        }
        if (!meal.getStrMeasure11().isEmpty() && !Character.isWhitespace(meal.getStrMeasure11().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure11());
        }
        if (!meal.getStrMeasure12().isEmpty() && !Character.isWhitespace(meal.getStrMeasure12().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure12());
        }
        if (!meal.getStrMeasure13().isEmpty() && !Character.isWhitespace(meal.getStrMeasure13().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure13());
        }
        if (!meal.getStrMeasure14().isEmpty() && !Character.isWhitespace(meal.getStrMeasure14().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure14());
        }
        if (!meal.getStrMeasure15().isEmpty() && !Character.isWhitespace(meal.getStrMeasure15().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure15());
        }
        if (!meal.getStrMeasure16().isEmpty() && !Character.isWhitespace(meal.getStrMeasure16().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure16());
        }
        if (!meal.getStrMeasure17().isEmpty() && !Character.isWhitespace(meal.getStrMeasure17().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure17());
        }
        if (!meal.getStrMeasure18().isEmpty() && !Character.isWhitespace(meal.getStrMeasure18().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure18());
        }
        if (!meal.getStrMeasure19().isEmpty() && !Character.isWhitespace(meal.getStrMeasure19().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure19());
        }
        if (!meal.getStrMeasure20().isEmpty() && !Character.isWhitespace(meal.getStrMeasure20().charAt(0))) {
            binding.measure.append("\n : " + meal.getStrMeasure20());
        }

        //===

        binding.youtube.setOnClickListener(v -> {

            Intent intentYoutube = new Intent(Intent.ACTION_VIEW);
            intentYoutube.setData(Uri.parse(meal.getStrYoutube()));
            startActivity(intentYoutube);
        });

        binding.source.setOnClickListener(v -> {

            Intent intentSource = new Intent(Intent.ACTION_VIEW);
            intentSource.setData(Uri.parse(meal.getStrSource()));
            startActivity(intentSource);
        });

    }

    @Override
    public void onErrorLoading(String message) {

        Utils.showDialogMessage(this, "Error ", message);
    }

    private void setupColorActionBarIcon(Drawable favoriteItemColor) {

        binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if ((binding.collapsingToolbar.getHeight() + verticalOffset)
                        < (2 * ViewCompat.getMinimumHeight(binding.collapsingToolbar))) {

                    if (binding.toolbar.getNavigationIcon() != null)
                        binding.toolbar.getNavigationIcon()
                                .setColorFilter(DetailsActivity.this
                                                .getResources().getColor(R.color.colorPrimary)
                                        , PorterDuff.Mode.SRC_ATOP);

                    favoriteItemColor.mutate().setColorFilter(DetailsActivity.this
                                    .getResources().getColor(R.color.colorPrimary)
                            , PorterDuff.Mode.SRC_ATOP);

                } else {

                    if (binding.toolbar.getNavigationIcon() != null)
                        binding.toolbar.getNavigationIcon()
                                .setColorFilter(DetailsActivity.this
                                                .getResources().getColor(R.color.colorWhite)
                                        , PorterDuff.Mode.SRC_ATOP);

                    favoriteItemColor.mutate().setColorFilter(DetailsActivity.this
                                    .getResources().getColor(R.color.colorWhite)
                            , PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_details, menu);
        favoriteItem = menu.findItem(R.id.favorite);

        Drawable favoriteItemColor = favoriteItem.getIcon();
        setupColorActionBarIcon(favoriteItemColor);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.favorite:

                checkTVShowInFavorite();
                addToFavorite();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkTVShowInFavorite() {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(mViewModel.getMealsFromFavorite(String.valueOf(mMeal.getIdMeal()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {

                    //is clicked.
                    isTVShowAvailableInFavorite = true;
                    favoriteItem.setIcon(R.drawable.ic_favorite);
                    compositeDisposable.dispose();
                }));
    }

    private void addToFavorite() {

        CompositeDisposable compositeDisposable = new CompositeDisposable();

        if (isTVShowAvailableInFavorite) {

            compositeDisposable.add(mViewModel.removeFromFavorite(mMeal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {

                        isTVShowAvailableInFavorite = false;
                        IS_FAVORITE_UPDATED = true;

                        favoriteItem.setIcon(R.drawable.ic_favorite_border);

                        Toast.makeText(getApplicationContext(), "Disable from Favorite.",
                                Toast.LENGTH_SHORT).show();

                        compositeDisposable.dispose();
                    }));
        }
        else {

            compositeDisposable.add(mViewModel.addToFavorite(mMeal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {

                        IS_FAVORITE_UPDATED = true;

                        favoriteItem.setIcon(R.drawable.ic_favorite);

                        Toast.makeText(getApplicationContext(), "Added to Favorite.",
                                Toast.LENGTH_SHORT).show();

                        compositeDisposable.dispose();
                    }));
        }
    }
}