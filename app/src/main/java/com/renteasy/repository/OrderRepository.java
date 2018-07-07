package com.renteasy.repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.Order;
import com.renteasy.entity.User;
import com.renteasy.presenters.CheckoutPresenter;
import com.renteasy.presenters.OrdersPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/19/2016.
 */
public class OrderRepository {

    @Inject
    FirebaseDatabase firebaseDatabase;

    private CheckoutPresenter.checkoutPresenterCallback mCheckout;

    private OrdersPresenter.OrdersCallback mOrdersCallback ;

    @Inject
    OrderRepository() {
    }

    public void placeOrder(User user, Order order, CheckoutPresenter.checkoutPresenterCallback checkoutCallback) {
        mCheckout=checkoutCallback;
        DatabaseReference databaseReference = firebaseDatabase.getReference(AppConstant.FIRE_BASE_ORDERS).child(user.getUuid()).push();
        databaseReference.setValue(order, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    mCheckout.success();
                } else {
                    mCheckout.failure(databaseError.getMessage());
                }
            }
       });
    }

    public void getOrders(User user ,OrdersPresenter.OrdersCallback ordersCallback){
        mOrdersCallback=ordersCallback;
        firebaseDatabase.getReference(AppConstant.FIRE_BASE_ORDERS).child(user.getUuid())
                .addValueEventListener(bindEventListener());
    }

    private ValueEventListener bindEventListener() {
        return new ValueEventListener() {
            List<Order> result=new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot child:dataSnapshot.getChildren() ){
                    result.add(child.getValue(Order.class));
                }
                mOrdersCallback.onSuccess(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOrdersCallback.onError(databaseError.getMessage());
            }
        };
    }

}
