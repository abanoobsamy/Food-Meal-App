package com.abanoob_samy.foodmealapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abanoob_samy.foodmealapp.R;
import com.abanoob_samy.foodmealapp.models.Meals;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerHeaderAdapter extends PagerAdapter {

    private List<Meals.Meal> meals;
    private Context context;
    private static ClickListenerPagerHome clickListener;

    public ViewPagerHeaderAdapter(List<Meals.Meal> meals, Context context) {
        this.meals = meals;
        this.context = context;
    }

    public void setClickListenerPagerHome(ClickListenerPagerHome clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return meals.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_view_pager_header, null);

//        View view = LayoutInflater.from(context)
//                .inflate(R.layout.item_view_pager_header, container, false);

        ImageView ivMealThumb = view.findViewById(R.id.mealThumb);
        TextView tvMealName = view.findViewById(R.id.mealName);

        Meals.Meal meal = meals.get(position);

        String strMealThumb = meal.getStrMealThumb();
        Picasso.get().load(strMealThumb).into(ivMealThumb);

        String strMealName = meal.getStrMeal();
        tvMealName.setText(strMealName);

        view.setOnClickListener(v -> clickListener.onClick(v, position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface ClickListenerPagerHome {
        void onClick(View v, int position);
    }
}
