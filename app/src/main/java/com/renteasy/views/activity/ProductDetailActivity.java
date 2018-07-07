package com.renteasy.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.renteasy.R;
import com.renteasy.views.fragments.ProductDetailFragment;

/**
 * Created by RUPESH on 8/2/2016.
 */
public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.product_detail_fragment, new ProductDetailFragment())
                    .commit();
        }


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
