package com.renteasy.views;

import com.renteasy.entity.Category;

import java.util.List;

/**
 * Created by RUPESH on 7/30/2016.
 */
public interface CategoryListView extends View {

    void hideLoadingSpinner();

    void showLoadingSpinner();

    void showDetailScreen(Category category);

    void addCategoryToAdapter(Category category);
    void addCategoryListToAdapter(List<Category> category);
}
