package com.renteasy.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.adapters.CartListAdapter;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.ProductDetail;
import com.renteasy.presenters.FavouritePresenter;
import com.renteasy.views.activity.ProductDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 8/6/2016.
 */
public class FavouriteFragment extends Fragment {


    @BindView(R.id.fav_details) RecyclerView mCartRecyclerView;
    @BindView(R.id.fav_empty) TextView emptyCart;
    @Inject FavouritePresenter favouritePresenter;
    private CartListAdapter mcCartListAdapter;
    private List<ProductDetail> cart;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favourite_products, container, false);
        ButterKnife.bind(this, rootView);
        Intent intent = getActivity().getIntent();
        initializeToolbar();
        initializeRecyclerView();
        showFavouriteList();
        return rootView;
    }

    private void initializeRecyclerView() {
        mCartRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void showFavouriteList() {
        cart=favouritePresenter.getFavourites();
        if(cart==null || cart.size()==0) {
            showCartEmptyMessage();
        }
        mcCartListAdapter= new CartListAdapter(cart, getContext(),new CartFragment.CartItemClickListener(){

            @Override
            public void showProductDetails(int position) {
                ProductDetail productDetail= cart.get(position);
                Intent intent = new Intent(getContext(), ProductDetailActivity.class)
                        .putExtra(AppConstant.PROD_DETAIL, productDetail);
                intent.putExtra(AppConstant.IS_TO_SHOW_FAV,false);
                startActivity(intent);
            }

            @Override
            public void removeFromCart(int position) {
                favouritePresenter.removeFavourites(cart.get(position));
                cart.remove(position);
                mcCartListAdapter.notifyItemRemoved(position);
                if(cart==null || cart.size()==0) {
                    showCartEmptyMessage();
                }
            }
        });

        mCartRecyclerView.setAdapter(mcCartListAdapter);
    }

    private void showCartEmptyMessage() {
        mCartRecyclerView.setVisibility(View.GONE);
        emptyCart.setVisibility(View.VISIBLE);
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void initializeToolbar() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.favourites);
    }
}