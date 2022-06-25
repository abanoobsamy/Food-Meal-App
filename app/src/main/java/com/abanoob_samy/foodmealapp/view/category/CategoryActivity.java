package com.abanoob_samy.foodmealapp.view.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.abanoob_samy.foodmealapp.R;
import com.abanoob_samy.foodmealapp.adapters.ViewPagerCategoryAdapter;
import com.abanoob_samy.foodmealapp.databinding.ActivityCategoryBinding;
import com.abanoob_samy.foodmealapp.models.Categories;
import com.abanoob_samy.foodmealapp.view.home.HomeActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;

    public static final String EXTRA_DATA_NAME = "EXTRA_DATA_NAME";
    public static final String EXTRA_DATA_DESC = "EXTRA_DATA_DESC";
    public static final String EXTRA_DATA_IMAGE = "EXTRA_DATA_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActionBar();
        initIntent();
    }

    private void initIntent() {

        Intent intent = getIntent();

        List<Categories.Category> categories =
                (List<Categories.Category>) intent.getSerializableExtra(HomeActivity.EXTRA_CATEGORY);

        int position = intent.getIntExtra(HomeActivity.EXTRA_POSITION, 0);

        FragmentManager fm = getSupportFragmentManager();

        ViewPagerCategoryAdapter adapter = new ViewPagerCategoryAdapter(fm, getLifecycle(), categories);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayoutCategory, binding.viewPager, (tab, position1) -> {

            tab.setText(categories.get(position1).getStrCategory());
        }).attach();

        binding.viewPager.setCurrentItem(position, true);
        adapter.notifyDataSetChanged();
    }

    private void initActionBar() {

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Category");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }
}