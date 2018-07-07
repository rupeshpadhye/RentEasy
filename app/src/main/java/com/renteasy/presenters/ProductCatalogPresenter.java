package com.renteasy.presenters;

import com.renteasy.entity.Category;
import com.renteasy.views.ProductCatalogView;
import com.renteasy.views.View;

import javax.inject.Inject;

/**
 * Created by RUPESH on 7/31/2016.
 */
public class ProductCatalogPresenter implements Presenter {
    private ProductCatalogView productCatalogView;

    @Inject public  ProductCatalogPresenter(){

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
        productCatalogView=(ProductCatalogView)v;
    }

    @Override
    public void onCreate() {

    }

    public void loadProductsCatalog(Category category) {
        productCatalogView.setUpProductViewPager(category.getCatalogs());
    }

}
