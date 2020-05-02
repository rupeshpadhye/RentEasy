package com.renteasy.presenters;

import android.database.Cursor;
import android.os.Bundle;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.renteasy.views.View;

/**
 * Created by RUPESH on 8/14/2016.
 */
public class AddressPresenter implements  Presenter ,LoaderManager.LoaderCallbacks<Cursor>{

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
