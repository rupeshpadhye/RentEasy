package com.renteasy.presenters;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.renteasy.R;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.Address;
import com.renteasy.entity.Order;
import com.renteasy.entity.User;
import com.renteasy.repository.OrderRepository;
import com.renteasy.views.CheckoutView;
import com.renteasy.views.View;
import com.renteasy.views.fragments.CheckoutFragment;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/19/2016.
 */
public class CheckoutPresenter implements Presenter {

    private  CheckoutView checkoutView;
    @Inject
    OrderRepository checkoutRepository;

    @Inject CartPresenter mCartPresenter;

    @Inject
    SharedPreferences sharedPreferences;


    @Inject CheckoutPresenter (){}
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
        checkoutView=(CheckoutView)v;
    }

    @Override
    public void onCreate() {

    }

    public void getDepositAmount() {
        if(mCartPresenter.getCart() !=null){
            checkoutView.updateDepositAmount(String.valueOf(mCartPresenter.calculateDeposit(mCartPresenter.getCart())));
        }

    }

    public boolean isPrimaryAddressAdded(){
        return  (sharedPreferences.contains(AppConstant.PRIMARY_ADDRESS));
    }

    public boolean isCartEmpty() {
        return mCartPresenter.isCartEmpty();
    }

    public void doPayment(final CheckoutFragment.CheckoutCallback checkoutCallback) {

        String address=sharedPreferences.getString(AppConstant.PRIMARY_ADDRESS,null);
        Address addressObject=new Gson().fromJson(address, Address.class);
        String firebaseUser=sharedPreferences.getString(AppConstant.USER, null);
        User user =new Gson().fromJson(firebaseUser, User.class);
        Order order=new Order(mCartPresenter.getCart(),addressObject);

      checkoutRepository.placeOrder(user,order,new checkoutPresenterCallback(){

            @Override
            public void success() {
                mCartPresenter.clearCart();
                checkoutCallback.success();
            }

            @Override
            public void failure(String message) {
                Log.e("CheckoutPresenter", message);
                checkoutView.showServerErrorMessage(R.string.something_wrong);
            }
        });
    }

    public interface  checkoutPresenterCallback{
        void success();
        void failure(String message);
    }
}
