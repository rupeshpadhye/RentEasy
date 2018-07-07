package com.renteasy.views.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.adapters.ImagePagerAdapter;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.Pricing;
import com.renteasy.entity.ProductDetail;
import com.renteasy.presenters.CartPresenter;
import com.renteasy.presenters.FavouritePresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RUPESH on 8/2/2016.
 */
public class ProductDetailFragment extends Fragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.product_slider) ViewPager productViewPager;
    @BindView(R.id.viewPagerCountDots) LinearLayout pageIndicator;
    @BindView(R.id.radiogroup) RadioGroup radioGroup;
    @BindView(R.id.deposit_amount)  TextView depositAmount;
    @BindView(R.id.rent_amount) TextView rentAmount;
    @BindView(R.id.notify) FloatingActionButton notifyBtn;
    @BindView(R.id.addToCart) FloatingActionButton addToCart;
    /*@BindView(R.id.back) FloatingActionButton backFromDetailBtn;*/
    @BindView(R.id.favBtn) ImageButton favBtn;
    @BindView(R.id.updateCart) FloatingActionButton updateCart;
    @BindView(R.id.coordinateLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.description) TextView descTextView;
    @Inject
    CartPresenter cartPresenter;
    @Inject
    FavouritePresenter favouritePresenter;
    private Pricing currentPricing;
    private int tenureIndex = 0;
    private ImagePagerAdapter mImagePagerAdapter;
    private ProductDetail mProductDetail;
    private int dotsCount;
    private ImageView[] dots;
    private boolean isFav;
    private boolean isInfo;
    private boolean isToShowFavourites=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detail, container, false);
        ButterKnife.bind(this, rootView);
        mImagePagerAdapter = new ImagePagerAdapter(getChildFragmentManager());


        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(AppConstant.PROD_DETAIL)) {
            mProductDetail = (ProductDetail) intent.getSerializableExtra(AppConstant.PROD_DETAIL);
            isInfo= intent.getBooleanExtra(AppConstant.PROD_DETAIL_INFO,false);
             isToShowFavourites =intent.getBooleanExtra(AppConstant.IS_TO_SHOW_FAV,true);
            updateUI(mProductDetail);

        } else if (getArguments() != null) {
            Bundle bundle = getArguments();
            mProductDetail = (ProductDetail) bundle.getSerializable(AppConstant.PROD_DETAIL);
            isInfo= bundle.getBoolean(AppConstant.PROD_DETAIL_INFO);
            updateUI(mProductDetail);

        }
        else {
            coordinatorLayout.setVisibility(View.INVISIBLE);
        }



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tenureIndex = checkedId;
                setPricingDetails(mProductDetail.getPricingList().get(checkedId));
            }
        });

        if(!isToShowFavourites){
            favBtn.setVisibility(View.GONE);
        }

        return rootView;
    }

    private int findTenureIndex() {
        tenureIndex=0;
        if(mProductDetail.getPricing() !=null && mProductDetail.getPricingList()!=null) {
            for(int i=0;i<mProductDetail.getPricingList().size();i++){
                if(mProductDetail.getPricingList().get(i).equals(mProductDetail.getPricing())){
                    tenureIndex=i;
                    break;
                }
            }
        }
        return  tenureIndex;
    }

    private void showProductInfo() {
        descTextView.setText(mProductDetail.getDescription());
        if(isInfo){
            addToCart.setVisibility(View.GONE);
            updateCart.setVisibility(View.VISIBLE);
        }
    }

    private void checkStockAvailable(int stock) {
        if (stock == 0) {
            addToCart.setVisibility(View.GONE);
            updateCart.setVisibility(View.GONE);
            notifyBtn.setVisibility(View.VISIBLE);
        } else {
            addToCart.setVisibility(View.VISIBLE);
            notifyBtn.setVisibility(View.GONE);
        }
    }

    private void initializeToolbar(String title) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }


    private void setImageSlider(List<String> gallery) {
        if (gallery != null && !gallery.isEmpty()) {
            for (String image : gallery) {
                mImagePagerAdapter.addFragment(new ImageFragment(), image);
            }
            dotsCount = mImagePagerAdapter.getCount();
            setSliderDots(dotsCount);
        }
        productViewPager.setAdapter(mImagePagerAdapter);
        //productViewPager.setCurrentItem(0);
        productViewPager.setOnPageChangeListener(this);
    }

    private void setSliderDots(int count) {
        dots = new ImageView[count];

        for (int i = 0; i < count; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            pageIndicator.addView(dots[i], params);
        }
        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }

    private void addRadioButtons(List<Pricing> pricing) {
        if (pricing != null) {
            for (int i = 0; i < pricing.size(); i++) {
                RadioButton rdbtn = new RadioButton(getContext());
                rdbtn.setId(i);
                rdbtn.setChecked(i == findTenureIndex());
                rdbtn.setText(pricing.get(i).getDuration() + " " + pricing.get(i).getPeriod());
                radioGroup.addView(rdbtn);
            }
            setPricingDetails(mProductDetail.getPricingList().get(0));
        }

    }

    private void setPricingDetails(Pricing pricing) {
        depositAmount.setText(String.valueOf(pricing.getDeposit()));
        rentAmount.setText(String.valueOf(pricing.getPrice()));
        currentPricing = pricing;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

    }


   /* @OnClick(R.id.back)
    public void onBackBtnPressed() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).onBackPressedFragment();
        }
        else if(getActivity() instanceof CheckOutActivity) {
            ((CheckOutActivity) getActivity()).onBackPressedFragment();
        }
    }*/

    @OnClick(R.id.notify)
    public void onNotifyBtnClicked(){
        Snackbar.make(getView(), getString(R.string.notify_product), Snackbar.LENGTH_SHORT).show();
    }


    @OnClick(R.id.addToCart)
    public void addToCart() {
        mProductDetail.setPricing(currentPricing);
        cartPresenter.addToCart(mProductDetail);
        Snackbar.make(getView(),getString(R.string.added_to_cart),Snackbar.LENGTH_SHORT).show();
    }


    @OnClick(R.id.updateCart)
    public void updateCart() {
        cartPresenter.updateCart(mProductDetail, currentPricing);
        mProductDetail.setPricing(currentPricing);
        Snackbar.make(getView(), getString(R.string.updated_cart), Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void updateUI(ProductDetail productDetail) {
        coordinatorLayout.setVisibility(View.VISIBLE);
        mProductDetail=productDetail;
        isFav = favouritePresenter.isFavourite(mProductDetail);
        Drawable drawable;
        if (isFav) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite);
        } else {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_outline);
        }

        favBtn.setImageDrawable(drawable);
        favBtn.setOnClickListener(new View.OnClickListener() {
            Drawable drawable;

            @Override
            public void onClick(View v) {
                if (!isFav) {
                    favouritePresenter.addToFavourites(mProductDetail);
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite);
                    isFav = true;

                } else {
                    favouritePresenter.removeFavourites(mProductDetail);
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_outline);
                    isFav = false;
                }
                favBtn.setImageDrawable(drawable);
            }
        });

        clearFields();
        initializeToolbar(mProductDetail.getName());
        setImageSlider(mProductDetail.getGallery());
        addRadioButtons(mProductDetail.getPricingList());
        checkStockAvailable(mProductDetail.getStock());
        showProductInfo();

    }

    private void clearFields() {
        mImagePagerAdapter.clearAdapter();
        radioGroup.removeAllViews();
        pageIndicator.removeAllViews();
        productViewPager.removeAllViews();
    }
}