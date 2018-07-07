package com.renteasy.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.renteasy.App;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.ProductDetail;
import com.renteasy.repository.ProductRepository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/10/2016.
 */
public class ProductIntentService extends IntentService {

    @Inject
    ProductRepository productRepository;
    private ResultReceiver receiver;
    private static final String TAG = ProductIntentService.class.getName();


    @Inject
    public ProductIntentService() {
        super(ProductIntentService.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.getAppComponent(this).inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String storeId = intent.getStringExtra(AppConstant.SUB_CAT_ID);

        productRepository.getProducts(storeId,new ProductsResponse(){
            Bundle bundle = new Bundle();
            @Override
            public void onSuccess(List<ProductDetail> productDetailList) {
                if(productDetailList !=null) {

                    bundle.putSerializable(AppConstant.PRODUCT_DATA, (Serializable) productDetailList);
                    receiver.send(AppConstant.PRODUCTS_RETRIEVAL_SUCCESS, bundle);
                }
                else {
                    receiver.send(AppConstant.PRODUCTS_RETRIEVAL_EMPTY, bundle);
                }
            }
            @Override
            public void onFailure(String message) {
                receiver.send(AppConstant.PRODUCTS_RETRIEVAL_FAILURE, bundle);
            }
        });
        receiver = intent.getParcelableExtra(AppConstant.RECEIVER);
    }

    public interface ProductsResponse{
        void onSuccess(List<ProductDetail> productDetails);
        void onFailure(String message);
    }
}
