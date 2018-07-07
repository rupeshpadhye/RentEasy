package com.renteasy.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseUser;
import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.constant.AppConstant;
import com.renteasy.presenters.UserLifeCyclePresenter;
import com.renteasy.util.Util;
import com.renteasy.views.UserLifeCycleView;
import com.renteasy.views.fragments.AboutFragment;
import com.renteasy.views.fragments.CategoryListFragment;
import com.renteasy.views.fragments.FavouriteFragment;
import com.renteasy.views.fragments.ProfileFragment;
import com.renteasy.views.fragments.RentedDetailFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,UserLifeCycleView {


    @BindView(R.id.toolbar)  Toolbar toolbar;
    @Inject UserLifeCyclePresenter userLifeCyclePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initializeToolbar();
        initializeDependencyInjector();
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CategoryListFragment())
                    .commit();
        }

        userLifeCyclePresenter.attachView(this);
    }


    public void initializeDependencyInjector() {
        App.getAppComponent(this).inject(this);
    }



    private void initUi() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    public void onBackPressedFragment(){
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(userLifeCyclePresenter.identifyUserIsAnonymous()){
            getMenuInflater().inflate(R.menu.anonymous_menu, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ic_action_cart) {
            Intent intent = new Intent(this, HomeCartActivity.class)
                    .putExtra(AppConstant.CART_HEADING, getString(R.string.cart));

            startActivity(intent);
        }
        else if(id==R.id.logout){
            userLifeCyclePresenter.googleSignOut();
        }
        else if(id==R.id.google_sign_in){
            userLifeCyclePresenter.anonymousSignOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Util.hideKeyboard(this);
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.ic_home) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new CategoryListFragment())
                    .commit();
        } else if (id == R.id.ic_account_circle) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ProfileFragment())
                    .commit();
        }
     /*   else if (id == R.id.ic_action_cart) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new CartFragment())
                    .commit();
        }*/
        else if (id == R.id.my_rented_item) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new RentedDetailFragment())
                    .commit();
        }
        else if (id == R.id.my_favourites) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new FavouriteFragment())
                    .commit();
        }
        else if (id == R.id.ic_people) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new AboutFragment())
                    .commit();
        }
      /*  else if (id == R.id.ic_logout) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new AboutFragment())
                    .commit();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void redirectUser(FirebaseUser user) {
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showConnectionErrorMessage() {

    }

    @Override
    public void showServerErrorMessage(int messageCode) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}