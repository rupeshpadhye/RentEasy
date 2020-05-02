package com.renteasy.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.adapters.CategoryListAdapter;
import com.renteasy.constant.AppConstant;
import com.renteasy.entity.Category;
import com.renteasy.presenters.CategoryListPresenter;
import com.renteasy.views.CategoryListView;
import com.renteasy.views.activity.ProductCatalogActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 7/30/2016.
 */
public class CategoryListFragment  extends Fragment implements CategoryListView {


    @Inject CategoryListPresenter mCategoryListPresenter;
    @BindView(R.id.category_list) RecyclerView mCategoryListRecycleView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.category_list_relative_layout) RelativeLayout relativeLayout;
    private CategoryListAdapter mCategoryListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        ButterKnife.bind(this,rootView);
         mCategoryListRecycleView=(RecyclerView)rootView.findViewById(R.id.category_list);
        initializeRecyclerView();
        initializePresenter();
        initializeToolbar();
        initCategoryListAdapter();
        return rootView;
    }

    private void initializeToolbar() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.explore);
    }


    private void initializePresenter() {
        mCategoryListPresenter.attachView(this);
        mCategoryListPresenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        mCategoryListPresenter.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCategoryListPresenter.onPause();
    }

    private void initializeRecyclerView() {
        mCategoryListRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public void initCategoryListAdapter() {
        mCategoryListAdapter= new CategoryListAdapter(getContext(), new CategoryListListener() {
            @Override
            public void onElementClick(Category category) {
                mCategoryListPresenter.onElementClick(category);
            }
        });
        mCategoryListRecycleView.setAdapter(mCategoryListAdapter);
    }

    @Override
    public void hideLoadingSpinner() {
        progressBar.setVisibility(View.GONE);
        mCategoryListRecycleView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingSpinner() {
        progressBar.setVisibility(View.VISIBLE);
        mCategoryListRecycleView.setVisibility(View.GONE);
    }

    @Override
    public void showConnectionErrorMessage() {
        Snackbar.make(relativeLayout,getString(R.string.no_internet),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showServerErrorMessage(int messageCode) {
        Snackbar.make(relativeLayout,getString(messageCode,R.string.something_wrong),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showDetailScreen(Category category) {

        Intent intent = new Intent(getContext(), ProductCatalogActivity.class)
                .putExtra(AppConstant.CAT_DATA, category);
        startActivity(intent);
    }

    @Override
    public void addCategoryToAdapter(Category category) {
        mCategoryListAdapter.addCategory(category);
    }

    @Override
    public void addCategoryListToAdapter(List<Category> categoryList) {
        mCategoryListAdapter.addCategoryList(categoryList);
    }

    public  interface  CategoryListListener {
        void onElementClick (Category category);
    }
}
