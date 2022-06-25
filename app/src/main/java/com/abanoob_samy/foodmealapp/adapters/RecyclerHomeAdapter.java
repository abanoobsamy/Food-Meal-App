package com.abanoob_samy.foodmealapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abanoob_samy.foodmealapp.R;
import com.abanoob_samy.foodmealapp.databinding.ItemRecyclerCategoryBinding;
import com.abanoob_samy.foodmealapp.models.Categories;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHomeAdapter extends RecyclerView.Adapter<RecyclerHomeAdapter.HomeHolder> {

    private List<Categories.Category> categories;
    private Context context;
    private ClickListenerCategory clickListener;

    public RecyclerHomeAdapter(List<Categories.Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    public void setClickListenerCategory(ClickListenerCategory clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_category, parent, false);

        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {

        Categories.Category category = categories.get(position);
        
        String strCategoryThum = category.getStrCategoryThumb();

        Picasso.get().load(strCategoryThum).placeholder(R.drawable.ic_circle)
                .into(holder.binding.categoryThumb);

        String strCategoryName = category.getStrCategory();
        holder.binding.categoryName.setText(strCategoryName);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class HomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemRecyclerCategoryBinding binding;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemRecyclerCategoryBinding.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }

    public interface ClickListenerCategory {
        void onClick(View view, int position);
    }
}
