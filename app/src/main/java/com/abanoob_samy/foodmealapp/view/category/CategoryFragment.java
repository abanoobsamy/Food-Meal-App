package com.abanoob_samy.foodmealapp.view.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abanoob_samy.foodmealapp.R;
import com.abanoob_samy.foodmealapp.Utils;
import com.abanoob_samy.foodmealapp.adapters.RecyclerMealByCategoryAdapter;
import com.abanoob_samy.foodmealapp.databinding.FragmentCategoryBinding;
import com.abanoob_samy.foodmealapp.models.Meals;
import com.abanoob_samy.foodmealapp.view.details.DetailsActivity;
import com.abanoob_samy.foodmealapp.view.details.DetailsView;
import com.abanoob_samy.foodmealapp.view.home.HomeActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryView {

    private FragmentCategoryBinding binding;

    private AlertDialog.Builder descDialog;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding
                .inflate(getLayoutInflater(), container, false);

        binding.cardCategory.setOnClickListener(view -> {

            descDialog.setPositiveButton("CLOSE", (dialogInterface, i) -> dialogInterface.dismiss());
            descDialog.show();
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //request data from pagerAdapter
        if (getArguments() != null) {

            binding.textCategory.setText(getArguments().getString(CategoryActivity.EXTRA_DATA_DESC));

            Picasso.get()
                    .load(getArguments().getString(CategoryActivity.EXTRA_DATA_IMAGE))
                    .into(binding.imageCategory);

            Picasso.get()
                    .load(getArguments().getString(CategoryActivity.EXTRA_DATA_IMAGE))
                    .into(binding.imageCategoryBg);

            descDialog = new AlertDialog.Builder(getContext())
                    .setTitle(getArguments().getString(CategoryActivity.EXTRA_DATA_NAME))
                    .setMessage(getArguments().getString(CategoryActivity.EXTRA_DATA_DESC));

            CategoryPresenter presenter = new CategoryPresenter(this);
            presenter.getMealByCategory(getArguments().getString(CategoryActivity.EXTRA_DATA_NAME));
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
    public void setMeal(List<Meals.Meal> meal) {

        RecyclerMealByCategoryAdapter adapter = new RecyclerMealByCategoryAdapter(meal, getContext());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerCategoryFragment.setLayoutManager(layoutManager);
        binding.recyclerCategoryFragment.setClipToPadding(true);
        binding.recyclerCategoryFragment.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setClickListenerMealByCategory((view, position) -> {

            TextView mealName = view.findViewById(R.id.mealName);

            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra(HomeActivity.EXTRA_DETAILS, mealName.getText().toString());
            startActivity(intent);

            Toast.makeText(getContext(), "Meal " + meal.get(position).getStrMeal(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onErrorLoading(String message) {

        Utils.showDialogMessage(getContext(), "Error ", message);
    }
}