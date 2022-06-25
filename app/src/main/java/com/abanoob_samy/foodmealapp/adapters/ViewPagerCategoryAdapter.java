package com.abanoob_samy.foodmealapp.adapters;

import android.os.Bundle;

import com.abanoob_samy.foodmealapp.models.Categories;
import com.abanoob_samy.foodmealapp.view.category.CategoryActivity;
import com.abanoob_samy.foodmealapp.view.category.CategoryFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerCategoryAdapter extends FragmentStateAdapter {

    private List<Categories.Category> categories;

    public ViewPagerCategoryAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,
                                    List<Categories.Category> categories) {
        super(fragmentManager, lifecycle);
        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment = new CategoryFragment();

        //sending data to fragment.
        Bundle args = new Bundle();
        args.putString(CategoryActivity.EXTRA_DATA_NAME, categories.get(position).getStrCategory());
        args.putString(CategoryActivity.EXTRA_DATA_DESC, categories.get(position).getStrCategoryDescription());
        args.putString(CategoryActivity.EXTRA_DATA_IMAGE, categories.get(position).getStrCategoryThumb());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
