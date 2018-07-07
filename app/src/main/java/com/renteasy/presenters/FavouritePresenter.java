package com.renteasy.presenters;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.ProductDetail;
import com.renteasy.views.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by RUPESH on 8/15/2016.
 */
public class FavouritePresenter implements Presenter {

    private  static Set<String> FavouriteProductIdList=null ;
    @Inject SharedPreferences sharedPreferences;
    @Inject public FavouritePresenter(){

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

    }

    @Override
    public void onCreate() {

    }


    public List<ProductDetail> getFavourites(){
        List<ProductDetail> cart=null;
        if(sharedPreferences.contains(AppConstant.FAV_CART)) {
            String jsonFavorites = sharedPreferences.getString(AppConstant.FAV_CART, null);
            Gson gson = new Gson();
            ProductDetail[] favoriteItems = gson.fromJson(jsonFavorites,
                    ProductDetail[].class);

            cart = Arrays.asList(favoriteItems);
            cart = new ArrayList<>(cart);
        }
        return  cart;
    }

    public void addToFavourites(ProductDetail product){
        FavouriteProductIdList.add(product.getId());
        List<ProductDetail> cart = getFavourites();
        if (cart == null){
            cart = new ArrayList<>();
        }
        cart.add(product);
        saveCart(cart);
    }

    public void removeFavourites(ProductDetail product){
        FavouriteProductIdList.remove(product.getId());
        List<ProductDetail> cart = getFavourites();
        cart.remove(product);
        saveCart(cart);
    }

    public boolean isFavourite(ProductDetail productDetail){
        if(FavouriteProductIdList==null){
            FavouriteProductIdList =new HashSet<String>();
            List<ProductDetail> cart=getFavourites();
            if(cart !=null){
                loadFavourites(cart);
            }

        }
        return FavouriteProductIdList.contains(productDetail.getId());
    }

    private void loadFavourites(List<ProductDetail> favourites) {
        for(ProductDetail productDetail:favourites){
            FavouriteProductIdList.add(productDetail.getId());
        }
    }

    private void  saveCart(List<ProductDetail> cart){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonCart = gson.toJson(cart);
        editor.putString(AppConstant.FAV_CART, jsonCart);
        editor.commit();
    }


}
