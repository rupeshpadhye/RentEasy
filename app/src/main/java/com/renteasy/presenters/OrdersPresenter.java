package com.renteasy.presenters;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.Order;
import com.renteasy.entity.User;
import com.renteasy.repository.OrderRepository;
import com.renteasy.views.OrdersView;
import com.renteasy.views.View;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/23/2016.
 */
public class OrdersPresenter implements  Presenter {

    @Inject
    OrderRepository orderRepository;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject OrdersPresenter(){}

    private OrdersView mOrdersView;

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
        mOrdersView=(OrdersView)v;
    }

    @Override
    public void onCreate() {

    }

    public void getOrders(){

        String userString=sharedPreferences.getString(AppConstant.USER, null);
        User user =new Gson().fromJson(userString, User.class);
        mOrdersView.showLoading();
        orderRepository.getOrders(user, new OrdersCallback(){

            @Override
            public void onSuccess(List<Order> result) {
                mOrdersView.showOrderList(result);
            }

            @Override
            public void onError(String message) {
                mOrdersView.hideLoading();
            }
        });
    }

    public interface OrdersCallback {

        void onSuccess(List<Order> result);

        void onError(String message);
    }
}
