package com.renteasy.injector.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.renteasy.App;
import com.renteasy.util.CartUpdateProvider;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RUPESH on 7/30/2016.
 */
@Module
public class AppModule {
    private final App mApp;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public AppModule(App rentEasyApplication) {
        this.mApp = rentEasyApplication;
    }
    @Provides @Singleton
    App provideRentEasyApplicationContext() {
        return mApp; }

    @Provides @Singleton public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApp);
    }

    @Provides @Singleton public CartUpdateProvider provideCartUpdateProvider(){
        return new CartUpdateProvider ();
    }

    @Provides @Singleton public FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    @Provides @Singleton public  FirebaseDatabase provideFirebaseDatabase() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        return firebaseDatabase;
    }

    @Provides @Singleton public OkHttpClient provideOkHttpClient(){
        return  new OkHttpClient();
    }

    @Provides @Singleton public Picasso providePicasso(){
       return new Picasso.Builder(mApp)
                .downloader(new OkHttpDownloader(provideOkHttpClient()))
                .build();
    }

}
