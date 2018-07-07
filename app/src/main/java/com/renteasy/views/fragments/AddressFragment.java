package com.renteasy.views.fragments;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.adapters.AddressCursorAdapter;
import com.renteasy.constant.AppConstant;
import com.renteasy.database.DatabaseContract;
import com.renteasy.entity.Address;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RUPESH on 8/14/2016.
 */
public class AddressFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.addressBtn) Button addressBtn;
    @BindView(R.id.cancelAddress) Button cancelAddressBtn;
    @BindView(R.id.saveAddress) Button saveAddressBtn;
    @BindView(R.id.addresslist) ListView addressListView;
    @BindView(R.id.new_address_block) LinearLayout newAddressBlock;
    @BindView(R.id.add_line1) EditText newAddressLine1;
    @BindView(R.id.add_line2) EditText newAddressLine2;
    @BindView(R.id.add_line3) EditText newAddressLine3;
    @BindView(R.id.add_pincode) EditText newAddressLine4;
    @BindView(R.id.add4_layout) TextInputLayout textInputLayoutLine4;
    @BindView(R.id.add1_layout) TextInputLayout textInputLayoutLine1;
    @Inject SharedPreferences sharedPreferences;
    public static final int ADDRESS_LOADER = 0;
    private AddressCursorAdapter addressCursorAdapter;
    private boolean isAddNewAddressOpened=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);
    }

    private void restartLoader(){
        getLoaderManager().restartLoader(ADDRESS_LOADER,null,this);
    };

     AddressItemClickListener mItemListener = new AddressItemClickListener() {

        public void selectAddress(Address address) {

            ContentValues args = new ContentValues();
            args.put(DatabaseContract.address_table.PRIMARY_ADDRESS_FLAG, "N");
            getContext().getContentResolver().update(DatabaseContract.address_table.buildAddressUri()
                    , args, null, null);

            ContentValues args_latest = new ContentValues();
            args_latest.put(DatabaseContract.address_table.PRIMARY_ADDRESS_FLAG, "Y");
            getContext().getContentResolver().update(DatabaseContract.address_table.buildAddressUri()
                    , args_latest, DatabaseContract.address_table._ID + " = " + String.valueOf(address.getID()), null);
            saveAddressInPreference(address);
            restartLoader();
        }


        public void deleteAddress(Address address) {
            String [] value={String.valueOf(address.getID())};
            getContext().getContentResolver().delete(DatabaseContract.address_table.buildAddressUri(),
                    DatabaseContract.address_table._ID +" = "+String.valueOf(address.getID()),null);
            restartLoader();
        }
    };

    private void saveAddressInPreference(Address address) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(AppConstant.PRIMARY_ADDRESS,new Gson().toJson(address));
        editor.commit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.address_fragment, container, false);
        ButterKnife.bind(this, rootView);

       if (savedInstanceState != null && savedInstanceState.containsKey(AppConstant.ADD_ADDRESS)) {
            isAddNewAddressOpened = savedInstanceState.getBoolean(AppConstant.ADD_ADDRESS);
        }
        showNewAddress();
        addressCursorAdapter=new AddressCursorAdapter(getActivity(),mItemListener,true);
        addressListView.setAdapter(addressCursorAdapter);
        getLoaderManager().initLoader(ADDRESS_LOADER, null, this);
        return rootView;
    }

    private void showNewAddress() {
        if(isAddNewAddressOpened)
        {
            newAddressBlock.setVisibility(View.VISIBLE);
            addressBtn.setVisibility(View.GONE);
        }
        else {
            newAddressBlock.setVisibility(View.GONE);
            addressBtn.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.addressBtn)
    public void addNewAddress(){
        isAddNewAddressOpened=true;
        clearFields();
        showNewAddress();
        //textInputLayoutLine1.setErrorEnabled(false);
        //textInputLayoutLine4.setErrorEnabled(false);
    }

    @OnClick(R.id.cancelAddress)
    public void cancelAddress(){
        isAddNewAddressOpened=false;
        clearFields();
        showNewAddress();
    }

    private void clearFields(){
        newAddressLine1.setText("");
        newAddressLine2.setText("");
        newAddressLine3.setText("");
        newAddressLine4.setText("");
    }
    @OnClick(R.id.saveAddress)
    public void saveAddress(){

        if(newAddressLine1.getText() ==null|| newAddressLine1.getText().toString().isEmpty() ){
            //textInputLayoutLine1.setErrorEnabled(true);
            textInputLayoutLine1.setError(getString(R.string.mandatory_field));
            return;
        }

        if(newAddressLine4.getText() ==null ||newAddressLine4.getText().toString().isEmpty() ){
            //textInputLayoutLine1.setErrorEnabled(true);
            textInputLayoutLine4.setError(getString(R.string.mandatory_field));
            return;
        }

        newAddressBlock.setVisibility(View.GONE);
        addressBtn.setVisibility(View.VISIBLE);
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.address_table.LINE1, newAddressLine1.getText().toString());
        if(newAddressLine2.getText() !=null){
            values.put(DatabaseContract.address_table.LINE2,newAddressLine2.getText().toString());
        }

        if(newAddressLine3.getText() !=null){
            values.put(DatabaseContract.address_table.LINE3,newAddressLine3.getText().toString());
        }

        values.put(DatabaseContract.address_table.LINE4, newAddressLine4.getText().toString());
        values.put(DatabaseContract.address_table.PRIMARY_ADDRESS_FLAG, "N");
        Uri uri =getContext().getContentResolver().insert(DatabaseContract.address_table.buildAddressUri(), values);
        restartLoader();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), DatabaseContract.address_table.buildAddressUri(),
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        int i = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            i++;
            cursor.moveToNext();
        }
        addressCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        addressCursorAdapter.swapCursor(null);
    }

    public  interface  AddressItemClickListener{
         void selectAddress(Address address);
        void deleteAddress(Address address);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(AppConstant.ADD_ADDRESS, isAddNewAddressOpened);
    }

}
