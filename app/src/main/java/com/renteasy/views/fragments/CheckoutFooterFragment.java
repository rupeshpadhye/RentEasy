package com.renteasy.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.entity.ProductDetail;
import com.renteasy.presenters.CartPresenter;
import com.renteasy.presenters.UserLifeCyclePresenter;
import com.renteasy.util.CartSubscriber;
import com.renteasy.util.CartUpdateProvider;
import com.renteasy.views.activity.CheckOutActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RUPESH on 8/6/2016.
 */
public class CheckoutFooterFragment extends Fragment implements CartSubscriber {

    @BindView(R.id.total_amount)
    TextView totalAmount;
    @BindView(R.id.cart_item)
    TextView cartItem;
    @BindView(R.id.checkout_btn)
    Button checkoutBtn;
    @BindView(R.id.checkout_footer)
    LinearLayout checkoutFooter;
    @Inject
    CartUpdateProvider cartUpdateProvider;
    @Inject
    CartPresenter cartPresenter;
    @Inject
    UserLifeCyclePresenter userLifeCyclePresenter;

    private String total;
    private int cartSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);
        cartUpdateProvider.subscribeCardUpdates(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.checkout_footer, container, false);
        ButterKnife.bind(this, rootView);
        List<ProductDetail> cart = cartPresenter.getCart();
        if (cart != null) {
            cartSize = cart.size();
            total = String.valueOf(cartPresenter.calculateCartPrice(cart));
            updateFooter(cart.size(), total);
        }
        showFooter(cartSize);

        return rootView;
    }


    private void updateFooter(int cartSize, String amount) {
        showFooter(cartSize);
        cartItem.setText(String.valueOf(cartSize));
        totalAmount.setText(amount);
    }

    private void showFooter(int cartSize) {
        if (cartSize == 0) {
            checkoutFooter.setVisibility(View.GONE);
        } else {
            checkoutFooter.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @OnClick(R.id.checkout_btn)
    public void onCheckOutClicked() {
        if (userLifeCyclePresenter.identifyUserIsAnonymous()) {
            Toast.makeText(getActivity(), R.string.sign_in_to_checkout, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), CheckOutActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void cartUpdated(int item, String total) {
        updateFooter(item, total);
    }
}
