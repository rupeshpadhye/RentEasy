package com.renteasy.presenters;

import com.renteasy.R;
import com.renteasy.entity.Category;
import com.renteasy.repository.CategoriesRepository;
import com.renteasy.views.CategoryListView;
import com.renteasy.views.View;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by RUPESH on 7/30/2016.
 */
public class CategoryListPresenter implements Presenter {

    private CategoryListView mCategoryListView;
    @Inject CategoriesRepository categoriesRepository;
    @Inject public CategoryListPresenter(){

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mCategoryListView = (CategoryListView) v;
    }

    @Override
    public void onCreate() {
        loadCategories();
    }

    public void loadCategories() {
        mCategoryListView.showLoadingSpinner();
        categoriesRepository.getCategories(new categoryListResponse(){
            @Override
            public void onSuccess(List<Category> categoryList) {
                mCategoryListView.addCategoryListToAdapter(categoryList);
                mCategoryListView.hideLoadingSpinner();
            }

            @Override
            public void onFailure(String message) {
                mCategoryListView.showServerErrorMessage(R.string.something_wrong);
            }
        });
    }


    public void onElementClick(Category category) {
        mCategoryListView.showDetailScreen(category);
    }

    public interface categoryListResponse {
        void onSuccess(List<Category> categoryList);
        void onFailure(String message);
    }

    /*   protected ValueEventListener bindCategoryListListener() {
        return  new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Category category = child.getValue(Category.class);
                    mCategoryListView.addCategoryToAdapter(category);
                    mCategoryListView.hideLoadingSpinner();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                mCategoryListView.showServerErrorMessage(R.string.something_wrong);
            }
        };
    }*/

}
