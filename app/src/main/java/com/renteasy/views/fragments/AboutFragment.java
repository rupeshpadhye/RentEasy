package com.renteasy.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renteasy.R;

import butterknife.ButterKnife;

/**
 * Created by RUPESH on 8/6/2016.
 */
public class AboutFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about, container, false);
        ButterKnife.bind(this, rootView);
        initializeToolbar();
        return rootView;
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.about);
    }

}