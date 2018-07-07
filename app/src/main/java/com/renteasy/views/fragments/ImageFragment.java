package com.renteasy.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.renteasy.R;
import com.renteasy.constant.AppConstant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 8/4/2016.
 */
public class ImageFragment extends Fragment {

    @BindView(R.id.product_slider_img) ImageView sliderImage;
    private String imageURL;
    private static final  String TAG=ImageFragment.class.getName();

    public ImageFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_slide, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        imageURL=bundle.getString(AppConstant.PROD_IMG);

        Log.d("TAG",imageURL);

        if (imageURL != null&&!imageURL.isEmpty()) {
            Picasso.with(getContext()).load(imageURL).placeholder(R.mipmap.ic_launcher).into(sliderImage);
        }
        return  rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
