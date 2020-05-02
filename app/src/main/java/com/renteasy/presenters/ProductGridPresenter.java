package com.renteasy.presenters;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import androidx.fragment.app.Fragment;

import com.renteasy.constant.AppConstant;
import com.renteasy.entity.ProductDetail;
import com.renteasy.reciever.ProductReciever;
import com.renteasy.service.ProductIntentService;
import com.renteasy.views.ProductGridView;
import com.renteasy.views.View;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by RUPESH on 7/31/2016.
 */
public class ProductGridPresenter implements Presenter {

    private  List<ProductDetail> productDetailList;
    private ProductGridView productGridView;
    private ProductReciever mReceiver;
    @Inject public ProductGridPresenter(){}
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
        productGridView=(ProductGridView)v;
    }

    @Override
    public void onCreate() {
        mReceiver = new ProductReciever(new Handler());
        mReceiver.setReceiver(productGridView);
    }

    public void loadProducts(String subCategoryID) {
        Activity activity=null;
        if(productGridView instanceof Fragment){
           activity=((Fragment)productGridView).getActivity();
        }

        Intent bookIntent = new Intent(activity, ProductIntentService.class);
        bookIntent.putExtra(AppConstant.SUB_CAT_ID, subCategoryID);
        bookIntent.putExtra(AppConstant.RECEIVER, mReceiver);
        activity.startService(bookIntent);
    }

    public void onProductSelected(ProductDetail productDetail) {
        productGridView.showProductDetail(productDetail);
    }
}
