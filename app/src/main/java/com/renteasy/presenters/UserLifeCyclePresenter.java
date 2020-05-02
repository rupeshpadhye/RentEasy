package com.renteasy.presenters;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import com.renteasy.R;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.User;
import com.renteasy.views.UserLifeCycleView;
import com.renteasy.views.View;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/4/2016.
 */
public class UserLifeCyclePresenter implements Presenter {

    @Inject FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = UserLifeCyclePresenter.class.getName();
    private GoogleApiClient mGoogleApiClient;
    @Inject SharedPreferences sharedPreferences;
    UserLifeCycleView userLifeCycleView;

    @Inject
    UserLifeCyclePresenter() {

    }


    public void setInstances(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
    }

    private void initFireBaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                userLifeCycleView.redirectUser(user);
            }
        };
    }


    @Override
    public void onStart() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        userLifeCycleView = (UserLifeCycleView) v;
    }

    @Override
    public void onCreate() {
        initFireBaseAuth();
    }

    public void saveUser(FirebaseUser firebaseUser) {
        User user = new User(firebaseUser);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonRegUser = gson.toJson(user);
        editor.putString(AppConstant.USER, jsonRegUser);
        editor.commit();

    }


    public  void updateUser(User user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonRegUser = gson.toJson(user);
        editor.putString(AppConstant.USER, jsonRegUser);
        editor.commit();

    }

    private void removeUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AppConstant.USER);
        editor.commit();
    }

    public boolean identifyUserIsAnonymous() {
        if (sharedPreferences.contains(AppConstant.USER)) {
            return getUser().isAnonymous();
        }
        return false;
    }

    public void signInAnonymously() {
        userLifeCycleView.showProgressDialog();
        mAuth.signInAnonymously()
                .addOnCompleteListener((AppCompatActivity) userLifeCycleView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously", task.getException());
                            userLifeCycleView.showServerErrorMessage(R.string.something_wrong);
                        }
                        userLifeCycleView.hideProgressDialog();

                    }
                });

    }

    public void anonymousSignOut() {
        userLifeCycleView.showProgressDialog();
        mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    userLifeCycleView.hideProgressDialog();
                    removeUser();
                    userLifeCycleView.redirectUser(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                userLifeCycleView.showServerErrorMessage(R.string.something_wrong);
            }
        });
    }





    public void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((AppCompatActivity) userLifeCycleView).startActivityForResult(signInIntent, AppConstant.RC_SIGN_IN);

    }

    public void googleSignOut() {
        firebaseAuthWithGoogleSignOut();
    }


    public void onGoogleSignInActivityResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        } else {
            userLifeCycleView.showServerErrorMessage(R.string.something_wrong);
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        userLifeCycleView.showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((AppCompatActivity) userLifeCycleView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential Failed", task.getException());
                            userLifeCycleView.showServerErrorMessage(R.string.something_wrong);
                            return;
                        }
                        userLifeCycleView.hideProgressDialog();
                    }

                });
    }

    private void firebaseAuthWithGoogleSignOut() {
        userLifeCycleView.showProgressDialog();
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        userLifeCycleView.hideProgressDialog();
                        removeUser();
                        userLifeCycleView.redirectUser(null);
                    }
                });
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.contains(AppConstant.USER);
    }

    public User getUser(){
        String jsonUser = sharedPreferences.getString(AppConstant.USER, null);
        Gson gson = new Gson();
        User user = gson.fromJson(jsonUser, User.class);
        return user;
    }
}