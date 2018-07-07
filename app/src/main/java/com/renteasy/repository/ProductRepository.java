package com.renteasy.repository;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.ProductDetail;
import com.renteasy.service.ProductIntentService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/10/2016.
 */
public class ProductRepository {

    private ProductIntentService.ProductsResponse mProductsResponse;
    private static final String TAG=ProductRepository.class.getName();
    @Inject FirebaseDatabase firebaseDatabase;

    @Inject ProductRepository() {
    }

    public void getProducts(String storeId, ProductIntentService.ProductsResponse eventListener) {
        Log.d(TAG, storeId);
        mProductsResponse = eventListener;
        firebaseDatabase.getReference(AppConstant.FIRE_BASE_STORE + "/" + storeId)
                .addValueEventListener(bindEventListener());

    }

    private ValueEventListener bindEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ProductDetail> productDetailList = null;
                if (dataSnapshot.exists()) {
                    productDetailList = new ArrayList<>();
                    GenericTypeIndicator<List<ProductDetail>> listType = new GenericTypeIndicator<List<ProductDetail>>() {
                    };
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        productDetailList = child.getValue(listType);
                    }
                    mProductsResponse.onSuccess(productDetailList);
                }
                else {
                    mProductsResponse.onSuccess(productDetailList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProductsResponse.onFailure(databaseError.getDetails());
            }
        };
    }

}
