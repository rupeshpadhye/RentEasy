package com.renteasy.repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.renteasy.constant.AppConstant;
import com.renteasy.views.widget.HotDealsFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/17/2016.
 */
public class DealsRepository {
    @Inject
    FirebaseDatabase firebaseDatabase;

    @Inject
    DealsRepository(){}

    private HotDealsFactory.HotDealsResponse mHotDealsResponse;

    public void getTopDeals(HotDealsFactory.HotDealsResponse hotDealsResponse){
        mHotDealsResponse=hotDealsResponse;
        firebaseDatabase.getReference(AppConstant.FIRE_BASE_DEALS)
                .addValueEventListener(bindEventListener());
    }

    private ValueEventListener bindEventListener() {
        return new ValueEventListener() {
            List<String> result=new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren() ){
                    result.add(child.getValue(String.class));
                }
                mHotDealsResponse.onSuccess(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mHotDealsResponse.onError(databaseError.getMessage());
            }
        };
    }
}
