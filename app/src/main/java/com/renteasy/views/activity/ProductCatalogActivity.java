package com.renteasy.views.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.adapters.ViewPagerAdapter;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.Category;
import com.renteasy.entity.ProductDetail;
import com.renteasy.entity.SubCategory;
import com.renteasy.presenters.ProductCatalogPresenter;
import com.renteasy.views.ProductCatalogView;
import com.renteasy.views.fragments.ProductDetailFragment;
import com.renteasy.views.fragments.ProductGridFragment;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 7/31/2016.
 */
public class ProductCatalogActivity extends AppCompatActivity implements ProductCatalogView,ProductGridFragment.onProductSelectedListener {

    @Inject ProductCatalogPresenter productCatalogPresenter;
    @Inject SharedPreferences sharedPreferences;
    @BindView(R.id.product_pager) ViewPager mProductPager;
    //@BindView(R.id.product_tabs) TableLayout mTablLayout;
    private TabLayout mTablLayout;
    private static Category mCategory;
    private static int tabIndex;
    private boolean mTwoPane;
    private static String LOG_TAG=ProductCatalogActivity.class.getName();
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        mTwoPane= getResources().getBoolean(R.bool.dual_pane);
        mTablLayout = (TabLayout) findViewById(R.id.product_tabs);
        initializeDependencyInjector();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(AppConstant.CAT_DATA)) {
            final Category category = (Category)intent.getSerializableExtra(AppConstant.CAT_DATA);
            setCategory(category);
        }

        if (savedInstanceState == null || !savedInstanceState.containsKey(AppConstant.CATEGORY_DATA)) {
            initializePresenter();

        } else {
            setUpProductViewPager((List<SubCategory>) savedInstanceState.getSerializable(AppConstant.CATEGORY_DATA));
        }
         initializeToolbar(mCategory.getName());
        setTabIndex(tabIndex);
        if(mTwoPane){
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.productDetailFragment, new ProductDetailFragment())
                        .commit();

            }
        }
    }

    private void initUI() {
        setContentView(R.layout.product_catalog);
        ButterKnife.bind(this);

    }

    private  void setTabIndex(int tabIndex) {
        mTablLayout.setScrollPosition(tabIndex, 0f, true);
        mProductPager.setCurrentItem(tabIndex);
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

    private void initializePresenter() {
        productCatalogPresenter.attachView(this);
        productCatalogPresenter.loadProductsCatalog(mCategory);
        productCatalogPresenter.onCreate();
    }

    private void initializeToolbar(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ic_action_cart) {
            Intent intent = new Intent(this, ProductHomeCartActivity.class)
                    .putExtra(AppConstant.CART_HEADING, getString(R.string.cart));

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        tabIndex= mTablLayout.getSelectedTabPosition();
        outState.putSerializable(AppConstant.CATEGORY_DATA
                    , (Serializable) mCategory.getCatalogs());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setUpProductViewPager(List<SubCategory> productList) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for(SubCategory product:productList){
            ProductGridFragment productGridFragment=new ProductGridFragment();
            productGridFragment.setSubCategory(product.getId());
            adapter.addFragment(productGridFragment, product.getName());
        }
        mProductPager.setAdapter(adapter);
        mTablLayout.setupWithViewPager(mProductPager);
    }


    @Override
    public void showConnectionErrorMessage() {

    }

    @Override
    public void showServerErrorMessage(int messageCode) {

    }

    public void setCategory(Category category) {
        this.mCategory =category;
    }


    @Override
    public void onProductSelected(ProductDetail productDetail) {
        if(mTwoPane){
            ProductDetailFragment fragment = (ProductDetailFragment)getSupportFragmentManager().
                    findFragmentById(R.id.productDetailFragment);
            fragment.updateUI(productDetail);
        }
        else{
            Intent intent = new Intent(this, ProductDetailActivity.class)
                    .putExtra(AppConstant.PROD_DETAIL, productDetail);
            startActivity(intent);

        }
    }
}
