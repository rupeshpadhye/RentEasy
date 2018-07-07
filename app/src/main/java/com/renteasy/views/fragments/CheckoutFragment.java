package com.renteasy.views.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.renteasy.constant.AppConstant;
import com.renteasy.presenters.CheckoutPresenter;
import com.renteasy.views.CheckoutView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RUPESH on 8/6/2016.
 */
public class CheckoutFragment extends Fragment implements CheckoutView {

    @BindView(R.id.pay) Button payButton;
    @BindView(R.id.deposit) TextView depositAmount;
    @BindView(R.id.successful_msg) TextView successMsg;
    @BindView(R.id.depositLayout) LinearLayout depositLayout;
    @Inject CheckoutPresenter checkoutPresenter;
    private CheckoutCallback checkoutCallback;
    private String mDepositAmount;
    public static  boolean mSuccess;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.checkout_pay, container, false);
        ButterKnife.bind(this, rootView);

       /* if(savedInstanceState !=null && savedInstanceState.containsKey(AppConstant.CHECKOUT_PAY_CLICKED)){
          mSuccess=savedInstanceState.getBoolean(AppConstant.CHECKOUT_PAY_CLICKED);
        }*/
       /* else if(getArguments()!=null && getArguments().containsKey(AppConstant.CHECKOUT_PAY_CLICKED)){
            Bundle bundle=getArguments();
            mSuccess=bundle.getBoolean(AppConstant.CHECKOUT_PAY_CLICKED,false);
        }*/

        if(mSuccess){
            onSuccessfulTransaction();
        }
        else {
            checkoutPresenter.attachView(this);
            checkoutPresenter.getDepositAmount();
        }

        return rootView;
    }

    private void onSuccessfulTransaction() {
        depositAmount.setText(mDepositAmount);
        payButton.setVisibility(View.GONE);
        depositLayout.setVisibility(View.GONE);
        successMsg.setVisibility(View.VISIBLE);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @OnClick(R.id.pay)
    public void oncPay(){
        if(!checkoutPresenter.isPrimaryAddressAdded()){
            Snackbar.make(getView(),getString(R.string.address_validation),Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(checkoutPresenter.isCartEmpty()){
            Snackbar.make(getView(),getString(R.string.empty_cart),Snackbar.LENGTH_SHORT).show();
            return;
        }
        checkoutPresenter.doPayment(new CheckoutCallback() {
            @Override
            public void success() {
                mSuccess = true;
                onSuccessfulTransaction();
                Snackbar.make(getView(),getString(R.string.pay_success),Snackbar.LENGTH_INDEFINITE).show();

            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(AppConstant.CHECKOUT_PAY_CLICKED, mSuccess);
        outState.putString(AppConstant.DEPOSIT_AMOUNT,mDepositAmount);
        super.onSaveInstanceState(outState);
    }


    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CheckoutCallback) {
            checkoutCallback = (CheckoutCallback) context;
        } else {
            throw new ClassCastException("not instance of onMovieSelectedListener");
        }
    }*/

    @Override
    public void showConnectionErrorMessage() {

    }

    @Override
    public void showServerErrorMessage(int messageCode) {
        Toast.makeText(getContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDepositAmount(String amount) {
        mDepositAmount=amount;
        if(!amount.equals("0")){
            depositAmount.setText(mDepositAmount);
        }else {
            depositAmount.setVisibility(View.GONE);
        }

    }

    public interface CheckoutCallback {
        void success();
    }

    public static void resetSuccessFlag(){
        mSuccess=false;
    }


}
