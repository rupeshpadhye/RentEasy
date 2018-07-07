package com.renteasy.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RUPESH on 8/7/2016.
 */
public class CartUpdateProvider  {

    private List<CartSubscriber> cartSubscribers=new ArrayList<>();

    public void subscribeCardUpdates(CartSubscriber cartSubscriber) {
        cartSubscribers.add(cartSubscriber);
    }

    public void sendCartUpdatedNotifier(int size, String total) {
        for(CartSubscriber cartSubscriber:cartSubscribers){
            cartSubscriber.cartUpdated(size,total);
        }
    }
}
