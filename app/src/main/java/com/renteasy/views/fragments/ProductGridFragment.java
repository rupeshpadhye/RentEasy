package com.renteasy.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.adapters.ProductGridAdapter;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.ProductDetail;
import com.renteasy.presenters.ProductGridPresenter;
import com.renteasy.reciever.ProductReciever;
import com.renteasy.service.ProductIntentService;
import com.renteasy.views.ProductGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 7/31/2016.
 */
public class ProductGridFragment extends Fragment  implements ProductGridView {

    @BindView(R.id.product_grid) GridView mProductGridView;
    @BindView(R.id.gridRelativeLayout) RelativeLayout  relativeLayout;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.no_products) TextView noProductsTextView;

    @Inject ProductGridPresenter productGridPresenter;
    private static int mPosition = GridView.INVALID_POSITION;
    private ProductGridAdapter mProductGridAdapter;
    private String mSubCategoryId;
    private ProductReciever mReceiver;
    private onProductSelectedListener mProductClickListener;
    private  List<ProductDetail> mProductDetailList;
    private static final String TAG=ProductGridFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);
        initiateAdapter();
        productGridPresenter.onCreate();
        mReceiver = new ProductReciever(new Handler());
        mReceiver.setReceiver(this);


    }

    private void initiateAdapter() {
        mProductGridAdapter = new ProductGridAdapter(getActivity(), R.layout.product_grid_item,new ArrayList<ProductDetail>());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_grid, container, false);
        ButterKnife.bind(this, rootView);
        initializePresenter();
        if (savedInstanceState != null && savedInstanceState.containsKey(AppConstant.SCROLL_POSITION)) {
            mPosition = savedInstanceState.getInt(AppConstant.SCROLL_POSITION);

        }
        if (savedInstanceState != null && savedInstanceState.containsKey(AppConstant.SUB_CAT_ID)) {
            mSubCategoryId = savedInstanceState.getString(AppConstant.SUB_CAT_ID);
        }


        if (savedInstanceState != null && savedInstanceState.containsKey(AppConstant.PRODUCT_DATA)) {
            mProductDetailList = (List<ProductDetail>) savedInstanceState.getSerializable(AppConstant.PRODUCT_DATA);
            if(mProductDetailList!=null && mProductDetailList.size()!=0){
                bindProductsGrid(mProductDetailList);
            }
            else {
                showNoProducts();
            }
        }
        else {
            showProgress();
            Intent bookIntent = new Intent(getActivity(), ProductIntentService.class);
            bookIntent.putExtra(AppConstant.SUB_CAT_ID, mSubCategoryId);
            bookIntent.putExtra(AppConstant.RECEIVER, mReceiver);
            getActivity().startService(bookIntent);
        }


        if (mPosition != GridView.INVALID_POSITION) {
            mProductGridView.smoothScrollToPosition(mPosition);
        }


        mProductGridView.setAdapter(mProductGridAdapter);

        mProductGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ProductDetail productDetail = mProductGridAdapter.getItem(position);
                mPosition = position;
                productGridPresenter.onProductSelected(productDetail);

            }
        });


        return rootView;


    }
    @Override
    public void onStart() {
        super.onStart();
        productGridPresenter.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        productGridPresenter.onPause();
    }

    private void initializePresenter() {
        productGridPresenter.attachView(this);

    }
    @Override
    public void showConnectionErrorMessage() {

    }

    @Override
    public void showServerErrorMessage(int messageCode) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (mProductGridAdapter.getCount() > 0) {
            outState.putInt(AppConstant.SCROLL_POSITION, mPosition);
        }

        outState.putSerializable(AppConstant.PRODUCT_DATA
                , (Serializable) mProductDetailList);
        outState.putString(AppConstant.SUB_CAT_ID, mSubCategoryId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void bindProductsGrid(List<ProductDetail> productDetailList) {
        mProductGridAdapter.setGridData(productDetailList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onProductSelectedListener) {
            mProductClickListener = (onProductSelectedListener) context;
        } else {
            throw new ClassCastException("not instance of onProductSelectedListener");
        }
    }



    @Override
    public void showProductDetail(ProductDetail productDetail) {
        mProductClickListener.onProductSelected(productDetail);
        /*Intent intent = new Intent(getContext(), ProductDetailActivity.class)
                .putExtra(AppConstant.PROD_DETAIL, productDetail);
                        startActivity(intent);*/
    }

    public void setSubCategory(String id) {
        mSubCategoryId =id;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        hideProgress();
        if(resultCode==AppConstant.PRODUCTS_RETRIEVAL_SUCCESS){
            mProductDetailList=(List<ProductDetail>)resultData.getSerializable(AppConstant.PRODUCT_DATA);
            bindProductsGrid(mProductDetailList);
        }
        else if(resultCode==AppConstant.PRODUCTS_RETRIEVAL_FAILURE) {
            Snackbar.make(relativeLayout,R.string.something_wrong,Snackbar.LENGTH_SHORT).show();
    }
        else if(resultCode==AppConstant.PRODUCTS_RETRIEVAL_EMPTY){
            showNoProducts();
        }
}


    public void  showNoProducts(){
        noProductsTextView.setVisibility(View.VISIBLE);
        mProductGridView.setVisibility(View.INVISIBLE);
    };
    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }

    public interface onProductSelectedListener {
        public void onProductSelected(ProductDetail productDetail);

    }

}