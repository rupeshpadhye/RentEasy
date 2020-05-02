package com.renteasy.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.adapters.CartListAdapter;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.ProductDetail;
import com.renteasy.presenters.CartPresenter;
import com.renteasy.util.CartUpdateProvider;
import com.renteasy.views.activity.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RUPESH on 8/6/2016.
 */
public class CartFragment extends Fragment {

    @Inject CartPresenter mCartPresenter;
    @Inject CartUpdateProvider cartUpdateProvider;
    @BindView(R.id.cart_details) RecyclerView mCartRecyclerView;
    @BindView(R.id.empty_cart)  LinearLayout emptyCart;
    private CartListAdapter mcCartListAdapter;
    private List<ProductDetail> cart;
    private Boolean productInfo = false;
    private Boolean isToShowProductDeatail = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cart_details, container, false);
        ButterKnife.bind(this, rootView);


        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(AppConstant.CART_HEADING)) {
            String cartHeading = intent.getStringExtra(AppConstant.CART_HEADING);
            initializeToolbar(cartHeading);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else if (getArguments() != null) {
            Bundle bundle = getArguments();
            String cartHeading = bundle.getString(AppConstant.CART_HEADING);
            productInfo = bundle.getBoolean(AppConstant.PROD_DETAIL_INFO);
            if (bundle.containsKey(AppConstant.SHOW_PROD_DETAIL)) {
                isToShowProductDeatail=bundle.getBoolean(AppConstant.SHOW_PROD_DETAIL);
            }
            if (savedInstanceState != null && savedInstanceState.containsKey(AppConstant.PRODUCT_INFO)) {
                productInfo = savedInstanceState.getBoolean(AppConstant.PRODUCT_INFO);
            }
            initializeToolbar(cartHeading);
        }


        initializeRecyclerView();
        bindCartList();
        return rootView;
    }


    private void initializeToolbar(String cartHeading) {
        if(cartHeading !=null){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(cartHeading);
        }

    }

    private void initializeRecyclerView() {
        mCartRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void bindCartList() {
        cart = mCartPresenter.getCart();
        if (cart == null || cart.size() == 0) {
            showCartEmptyMessage();
        }
        mcCartListAdapter = new CartListAdapter(cart, getContext(), new CartItemClickListener() {

            @Override
            public void showProductDetails(int position) {

                if (isToShowProductDeatail) {
                    ProductDetail productDetail = cart.get(position);
                    Bundle bundle = new Bundle();
                    Fragment prodFragment = new ProductDetailFragment();
                    bundle.putSerializable(AppConstant.PROD_DETAIL, productDetail);
                    bundle.putBoolean(AppConstant.PROD_DETAIL_INFO, productInfo);
                    bundle.putBoolean(AppConstant.PROD_DETAIL_INFO,true);
                    prodFragment.setArguments(bundle);
                    FragmentManager mg = getFragmentManager();
                    mg.beginTransaction()
                            .replace(R.id.container, prodFragment)
                            .addToBackStack("")
                            .commit();

                }
            }

            @Override
            public void removeFromCart(int position) {
                mCartPresenter.removeFromCart(cart.get(position));
                cart.remove(position);
                mcCartListAdapter.notifyItemRemoved(position);
                if (cart == null || cart.size() == 0) {
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

    @OnClick(R.id.explore)
    public void onExploreBtnClicked() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }


    public interface CartItemClickListener {
        void showProductDetails(int position);

        void removeFromCart(int position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(AppConstant.PRODUCT_INFO, productInfo);
    }

}

