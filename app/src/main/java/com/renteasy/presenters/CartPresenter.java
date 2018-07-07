package com.renteasy.presenters;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.Pricing;
import com.renteasy.entity.ProductDetail;
import com.renteasy.util.CartUpdateProvider;
import com.renteasy.views.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by RUPESH on 8/7/2016.
 */
public class CartPresenter implements Presenter {

    @Inject SharedPreferences sharedPreferences;
    @Inject CartUpdateProvider cartUpdateProvider;

    @Inject public CartPresenter(){

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

    public List<ProductDetail> getCart(){
         List<ProductDetail> cart=null;
        if(sharedPreferences.contains(AppConstant.CART)) {
            String jsonFavorites = sharedPreferences.getString(AppConstant.CART, null);
            Gson gson = new Gson();
            ProductDetail[] favoriteItems = gson.fromJson(jsonFavorites,
                    ProductDetail[].class);

            cart = Arrays.asList(favoriteItems);
            cart = new ArrayList<>(cart);
        }
        return  cart;
    }

    public void addToCart(ProductDetail product){
        List<ProductDetail> cart = getCart();
        if (cart == null){
            cart = new ArrayList<>();
        }
        cart.add(product);
        saveCart(cart);
    }

    public void removeFromCart(ProductDetail product){
        List<ProductDetail> cart = getCart();
        cart.remove(product);
        saveCart(cart);
    }
    private void  saveCart(List<ProductDetail> cart){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonCart = gson.toJson(cart);
        editor.putString(AppConstant.CART, jsonCart);
        editor.commit();
        cartUpdateProvider.sendCartUpdatedNotifier(cart.size(), String.valueOf(calculateCartPrice(cart)));
    }


    public void updateCart(ProductDetail productDetail, Pricing currentPricing) {
        List<ProductDetail> cart = getCart();
        int index=cart.indexOf(productDetail);
        productDetail.setPricing(currentPricing);
        cart.set(index, productDetail);
        saveCart(cart);
    }

    public double calculateCartPrice(List<ProductDetail> cart) {
        double total=0 ;
        for(ProductDetail pd:cart){
            total=total+(pd.getPricing().getPrice());
        }
        return total;
    }

    public double calculateDeposit(List<ProductDetail> cart) {
        double total=0 ;
        for(ProductDetail pd:cart){
            total=total+(pd.getPricing().getDeposit());
        }
        return total;
    }


    public void clearCart() {
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.remove(AppConstant.CART);
        editor.commit();
        cartUpdateProvider.sendCartUpdatedNotifier(0,"0");
    }

    public boolean isCartEmpty(){
        return (!sharedPreferences.contains(AppConstant.CART));
    }
}
