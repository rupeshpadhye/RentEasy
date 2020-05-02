package com.renteasy.views.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.renteasy.R;
import com.renteasy.views.fragments.CartFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 9/6/2016.
 */
public class HomeCartActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initializeToolbar();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CartFragment())
                    .commit();
        }


    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initUi() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
