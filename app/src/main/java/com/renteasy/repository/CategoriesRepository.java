package com.renteasy.repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.Category;
import com.renteasy.presenters.CategoryListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/4/2016.
 */
public class CategoriesRepository {

    @Inject FirebaseDatabase firebaseDatabase;

    @Inject CategoriesRepository(){}

    private CategoryListPresenter.categoryListResponse mCategoryListResponse;
    private static final String TAG=CategoriesRepository.class.getName();

    public void getCategories(CategoryListPresenter.categoryListResponse listener){
        mCategoryListResponse=listener;
        DatabaseReference databaseReference= firebaseDatabase.getReference(AppConstant.FIRE_BASE_CATEGORIES);
        databaseReference.addValueEventListener(bindListener());
    }

    private ValueEventListener bindListener() {
        return new ValueEventListener() {
            List<Category> categoryList=new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Category category = child.getValue(Category.class);
                    categoryList.add(category);
                }
                mCategoryListResponse.onSuccess(categoryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mCategoryListResponse.onFailure(databaseError.getDetails());
            }
        };
    }

}
