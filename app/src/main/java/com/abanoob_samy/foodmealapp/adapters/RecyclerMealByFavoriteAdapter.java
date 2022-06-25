package com.abanoob_samy.foodmealapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abanoob_samy.foodmealapp.R;
import com.abanoob_samy.foodmealapp.databinding.ItemRecyclerMealBinding;
import com.abanoob_samy.foodmealapp.models.Meals;
import com.abanoob_samy.foodmealapp.view.favorite.ClickListenerFavorite;
import com.abanoob_samy.foodmealapp.viewmodel.MealsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RecyclerMealByFavoriteAdapter
        extends RecyclerView.Adapter<RecyclerMealByFavoriteAdapter.MealByFavoriteHolder> {

    private List<Meals.Meal> meals;
    private Context context;
    private ClickListenerMealByCategory clickListenerMealByCategory;

    private ClickListenerFavorite clickListenerFavorite;

    private MealsViewModel mViewModel;

    private Boolean isTVShowAvailableInFavorite = false;

    public static Boolean IS_FAVORITE_UPDATED = false;

    public RecyclerMealByFavoriteAdapter(List<Meals.Meal> meals, Context context) {
        this.meals = meals;
        this.context = context;
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(MealsViewModel.class);
    }

    public void setClickListenerMealByCategory(ClickListenerMealByCategory clickListener) {
        this.clickListenerMealByCategory = clickListener;
    }

    public void setClickListenerFavorite(ClickListenerFavorite clickListenerFavorite) {
        this.clickListenerFavorite = clickListenerFavorite;
    }

    @NonNull
    @Override
    public MealByFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MealByFavoriteHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_meal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealByFavoriteHolder holder, int position) {

        Meals.Meal meal = meals.get(position);

        String strMealThumb = meal.getStrMealThumb();
        Picasso.get()
                .load(strMealThumb)
                .placeholder(R.drawable.shadow_bottom_to_top)
                .into(holder.binding.mealThumb);

        String strMealName = meal.getStrMeal();
        holder.binding.mealName.setText(strMealName);

        checkTVShowInFavorite(holder, meal);

        addToFavorite(holder, meal);

        holder.binding.love.setOnClickListener(view -> {

            clickListenerFavorite.removeTVShowFromFavorite(meal, position);
        });

    }

    @Override
    public int getItemCount() {

        if (meals != null)
            return meals.size();

        else
            return 0;
    }

    public class MealByFavoriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemRecyclerMealBinding binding;

        public MealByFavoriteHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemRecyclerMealBinding.bind(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListenerMealByCategory.onClick(view, getAdapterPosition());
        }
    }

    public interface ClickListenerMealByCategory {
        void onClick(View view, int position);
    }

    private void checkTVShowInFavorite(MealByFavoriteHolder holder, Meals.Meal meal) {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(mViewModel.getMealsFromFavorite(String.valueOf(meal.getIdMeal()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {

                    //is clicked.
                    isTVShowAvailableInFavorite = true;
                    holder.binding.love.setImageResource(R.drawable.ic_favorite);
                    compositeDisposable.dispose();
                }));
    }

    private void addToFavorite(MealByFavoriteHolder holder, Meals.Meal meal) {

        holder.binding.love.setOnClickListener(view -> {

            CompositeDisposable compositeDisposable = new CompositeDisposable();

            if (isTVShowAvailableInFavorite) {

                compositeDisposable.add(mViewModel.removeFromFavorite(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {

                            isTVShowAvailableInFavorite = false;
                            IS_FAVORITE_UPDATED = true;

                            holder.binding.love.setImageResource(R.drawable.ic_favorite_border);

                            Toast.makeText(context, "Disable from Favorite.",
                                    Toast.LENGTH_SHORT).show();

                            compositeDisposable.dispose();
                        }));
            }
            else {
                compositeDisposable.add(mViewModel.addToFavorite(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {

                            IS_FAVORITE_UPDATED = true;

                            holder.binding.love.setImageResource(R.drawable.ic_favorite);

                            Toast.makeText(context, "Added to Favorite.",
                                    Toast.LENGTH_SHORT).show();

                            compositeDisposable.dispose();
                        }));
            }
        });

        holder.binding.love.setVisibility(View.VISIBLE);
    }

}
