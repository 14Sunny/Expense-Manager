package com.example.expensemanager.utils;

import com.example.expensemanager.Models.Category;
import com.example.expensemanager.R;

import java.util.ArrayList;

public class Constants {
    public static String INCOME="Income";
    public static String EXPENSE="Expense";

    public static ArrayList<Category> categories;
    public static void setCategories(){
         categories=new ArrayList<>();
        categories.add(new Category("Salary", R.drawable.ic_salary,R.color.category1));
        categories.add(new Category("Entertainment",R.drawable.film,R.color.category2));
        categories.add(new Category("Shopping",R.drawable.shopping,R.color.category3));
        categories.add(new Category("Food",R.drawable.food,R.color.category4));
        categories.add(new Category("Travel",R.drawable.passport,R.color.category5));
        categories.add(new Category("General",R.drawable.general,R.color.category6));
        categories.add(new Category("Rent",R.drawable.ic_rent,R.color.category7));
        categories.add(new Category("Loan",R.drawable.ic_loan,R.color.category8));
    }
    
    public static Category getCategoryDetails(String categoryName){
        for (Category cat: categories) {
            if(cat.getCategoryName().equals(categoryName)){
                return cat;
            }
        }
        return null;
    }
}
