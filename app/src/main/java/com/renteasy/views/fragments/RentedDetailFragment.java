package com.renteasy.views.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.adapters.OrdersListAdapter;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.Order;
import com.renteasy.entity.ProductDetail;
import com.renteasy.presenters.OrdersPresenter;
import com.renteasy.views.OrdersView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 8/6/2016.
 */
public class RentedDetailFragment extends Fragment implements OrdersView {


    @BindView(R.id.orders)
    RecyclerView mOrdersRecyclerView;
    @BindView(R.id.no_order)
    TextView noOrders;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private OrdersListAdapter mOrderListAdapter;
    private List<Order> mOrders;

    @Inject
    OrdersPresenter mOrdersPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);
        mOrdersPresenter.onCreate();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rented_product_details, container, false);
        ButterKnife.bind(this, rootView);
        mOrdersPresenter.attachView(this);

        initializeToolbar();
        initializeRecyclerView();
        initCategoryListAdapter();

        if (savedInstanceState != null && savedInstanceState.containsKey(AppConstant.ORDERS)) {
            mOrders = (ArrayList<Order>) savedInstanceState.getSerializable(AppConstant.ORDERS);
            if (mOrders != null && mOrders.size() > 0) {
                showOrderList(mOrders);
            } else {
                showEmptyOrders();
            }
        } else {
            mOrdersPresenter.getOrders();
        }
        return rootView;
    }


    private void initializeRecyclerView() {
        mOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void initCategoryListAdapter() {
        mOrderListAdapter = new OrdersListAdapter(getContext());
        mOrdersRecyclerView.setAdapter(mOrderListAdapter);
    }

    private void initializeToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_rented_item);
    }


    @Override
    public void onStart() {
        super.onStart();
        mOrdersPresenter.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mOrdersPresenter.onPause();
    }

    @Override
    public void showConnectionErrorMessage() {

    }

    @Override
    public void showServerErrorMessage(int messageCode) {

    }


    @Override
    public void showOrderList(List<Order> result) {
        hideLoading();
        List<ProductDetail> productDetailList = new ArrayList<>();
        if (result == null || result.isEmpty()) {
            showEmptyOrders();
            return;
        }
        for (Order order : result) {
            productDetailList.addAll(order.getProductDetail());
        }
        mOrders = result;
        mOrderListAdapter.setOrdersList(productDetailList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(AppConstant.ORDERS, (Serializable) mOrders);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        mOrdersRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        mOrdersRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showEmptyOrders() {
        noOrders.setVisibility(View.VISIBLE);
        mOrdersRecyclerView.setVisibility(View.GONE);
    }
}
