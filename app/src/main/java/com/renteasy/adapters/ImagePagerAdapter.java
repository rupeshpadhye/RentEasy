package com.renteasy.adapters;

/**
 * Created by RUPESH on 8/4/2016.
 */

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.renteasy.constant.AppConstant;

import java.util.ArrayList;
import java.util.List;

public  class ImagePagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    public ImagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    public void addFragment(Fragment fragment, String image) {
        Bundle bundle=new Bundle();
        bundle.putString(AppConstant.PROD_IMG,image);
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);

    }

    public void clearAdapter(){
        mFragmentList.clear();
        notifyDataSetChanged();
    }



}
