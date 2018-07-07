package com.renteasy.views.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.adapters.ViewPagerAdapter;
import com.renteasy.constant.AppConstant;
import com.renteasy.util.CartUpdateProvider;
import com.renteasy.views.fragments.AddressFragment;
import com.renteasy.views.fragments.CartFragment;
import com.renteasy.views.fragments.CheckoutFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 8/7/2016.
 */
public class CheckOutActivity extends AppCompatActivity  {

    @Inject
    CartUpdateProvider cartUpdateProvider;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tabLinerlayout)
    LinearLayout tabLinearLayout;

    @BindView(R.id.success_block)
    CardView successBlock;

    private int[] tabIcons = {
            R.drawable.ic_add_shopping_cart,
            R.drawable.ic_home,
            R.drawable.ic_credit_card
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_activity);
        ButterKnife.bind(this);
        initializeDependencyInjector();
        initializeToolbar();
        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();



    }

    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        CartFragment cartFragment = new CartFragment();
        bundle.putBoolean(AppConstant.SHOW_PROD_DETAIL, false);
        cartFragment.setArguments(bundle);
        adapter.addFragment(cartFragment, getString(R.string.cart));
        adapter.addFragment(new AddressFragment(), getString(R.string.address));
        adapter.addFragment(new CheckoutFragment(), getString(R.string.pay));
        mViewPager.setAdapter(adapter);
    }


    public void initializeDependencyInjector() {
        App.getAppComponent(this).inject(this);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void initializeToolbar() {
        getSupportActionBar().setTitle(getString(R.string.checkout_title));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckoutFragment.resetSuccessFlag();
    }
}
