package com.renteasy.views;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by RUPESH on 9/4/2016.
 */
public interface UserLifeCycleView extends  View,  GoogleApiClient.OnConnectionFailedListener {
     void hideProgressDialog();
    void showProgressDialog();
    void redirectUser(FirebaseUser user);
}
