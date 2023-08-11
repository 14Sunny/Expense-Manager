package com.example.expensemanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Models.Category;
import com.example.expensemanager.R;
import com.example.expensemanager.databinding.SampleCategoryItemBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;

    //creating interface so that we can select our categories and show them in category box
    public interface CategoryClickListner{
        void onCategoryClicked(Category category);
    }
    CategoryClickListner categoryClickListner;
    // end of above interface, now we pass the object to our adapter given below
    public CategoryAdapter(Context context, ArrayList<Category> categories,CategoryClickListner categoryClickListner){
        this.context=context;
        this.categories=categories;
        this.categoryClickListner=categoryClickListner;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category=categories.get(position);
        holder.binding.categoryText.setText(category.getCategoryName());
        holder.binding.categoryIcon.setImageResource(category.getCategoryImage());

        //setting different background color of category icons
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()));

        //step 3: //for category-->binding it to the ui
        holder.itemView.setOnClickListener(v -> categoryClickListner.onCategoryClicked(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        SampleCategoryItemBinding binding;

        public CategoryViewHolder(@NonNull View itemView){
            super(itemView);
            binding=SampleCategoryItemBinding.bind(itemView);

        }
    }
}
