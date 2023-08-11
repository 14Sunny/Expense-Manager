package com.example.expensemanager.Models;

public class Category {
    private String categoryName;
    private int categoryImage;

    private int categoryColor;

    public Category(int categoryColor) {
        this.categoryColor = categoryColor;
    }

    public int getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
    }

//    public Category(String salary, int ic_salary, int category1) {
//    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public Category(String categoryName, int categoryImage,int categoryColor) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.categoryColor=categoryColor;
    }
}
