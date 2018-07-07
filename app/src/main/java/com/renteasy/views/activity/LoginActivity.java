package com.renteasy.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.constant.AppConstant;
import com.renteasy.presenters.UserLifeCyclePresenter;
import com.renteasy.util.Util;
import com.renteasy.views.UserLifeCycleView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RUPESH on 8/28/2016.
 */
public class LoginActivity extends AppCompatActivity implements UserLifeCycleView {
    @BindView(R.id.sign_in_anonymous)
    Button anonymousSignInBtn;
    //@BindView(R.id.sign_in_google) SignInButton googleSignInBtn;
    @BindView(R.id.sign_in_google)
    Button googleSignInBtn;
    @BindView(R.id.main_layout)
    RelativeLayout coordinatorLayout;
    @BindView(R.id.login_block)
    CardView cardView;
    @Inject
    UserLifeCyclePresenter userLifeCyclePresenter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initializeDependencyInjector();
        userLifeCyclePresenter.attachView(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        userLifeCyclePresenter.onCreate();
        userLifeCyclePresenter.setInstances(googleApiClient);
    }

    public void initializeDependencyInjector() {
        App.getAppComponent(this).inject(this);
    }


    private void initUi() {
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_in_google)
    public void onGoogleSignIn() {
        if (Util.isNetworkConnected(getBaseContext())) {
            userLifeCyclePresenter.googleSignIn();
        } else {
            showConnectionErrorMessage();
        }

    }

    @OnClick(R.id.sign_in_anonymous)
    public void onAnonymousSignIn() {
        if (userLifeCyclePresenter.identifyUserIsAnonymous()) {
            redirectToMainActivity();
        } else {
            if (Util.isNetworkConnected(getBaseContext())) {
                userLifeCyclePresenter.signInAnonymously();
            } else {
                showConnectionErrorMessage();
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        userLifeCyclePresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        userLifeCyclePresenter.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }


    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void showConnectionErrorMessage() {
        Snackbar.make(coordinatorLayout, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showServerErrorMessage(int messageCode) {
        Snackbar.make(coordinatorLayout, getString(messageCode, R.string.something_wrong), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void redirectUser(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            userLifeCyclePresenter.saveUser(user);
            redirectToMainActivity();
        }
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        showConnectionErrorMessage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.RC_SIGN_IN) {
            userLifeCyclePresenter.onGoogleSignInActivityResult(data);
        }
    }
}