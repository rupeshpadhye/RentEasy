package com.renteasy.views;

import com.renteasy.entity.Order;

import java.util.List;

/**
 * Created by RUPESH on 9/23/2016.
 */
public interface OrdersView extends  View  {
    void showOrderList(List<Order> result);

    void showLoading();

    void hideLoading();
}
