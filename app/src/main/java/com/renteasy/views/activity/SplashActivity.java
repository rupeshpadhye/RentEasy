package com.renteasy.views.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.renteasy.App;
import com.renteasy.constant.AppConstant;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/3/2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Inject SharedPreferences sharedPreferences;
    private static final String TAG=SplashActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeDependencyInjector();
        Intent intent;
        if(sharedPreferences.contains(AppConstant.USER)) {
            intent  = new Intent(this, MainActivity.class);
        }
        else{
            intent  = new Intent(this, LoginActivity.class);
        }


        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void initializeDependencyInjector() {
        App.getAppComponent(this).inject(this);
    }

}
