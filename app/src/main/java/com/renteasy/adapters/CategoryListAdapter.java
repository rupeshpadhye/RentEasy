package com.renteasy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.renteasy.R;
import com.renteasy.entity.Category;
import com.renteasy.views.fragments.CategoryListFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 7/30/2016.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private final CategoryListFragment.CategoryListListener mRecyclerListener;
    private final List<Category> mCategories;
    private Context mContext;
    @Inject
    Picasso picasso;

    public CategoryListAdapter(Context context,
                               CategoryListFragment.CategoryListListener recyclerClickListener) {

        mCategories = new ArrayList<>();
        mContext = context;
        mRecyclerListener = recyclerClickListener;
    }

    @Override
    public CategoryListAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(rootView, mRecyclerListener);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.CategoryViewHolder holder, int position) {
        holder.bindCategory(mCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public void addCategory(Category category) {
        mCategories.add(category);
        notifyDataSetChanged();
    }

    public void addCategoryList(List<Category> categoryList) {
        mCategories.clear();
        mCategories.addAll(categoryList);
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.catName) TextView catName;
        @BindView(R.id.catImage) ImageView catImage;
        @BindView(R.id.catProgressBar) ProgressBar progressBar;

        public CategoryViewHolder(View itemView, CategoryListFragment.CategoryListListener mRecyclerListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, mRecyclerListener);

        }

        private void bindListener(View itemView, final CategoryListFragment.CategoryListListener recyclerClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerClickListener.onElementClick(mCategories.get(getAdapterPosition()));
                }
            });

        }

        public void bindCategory(Category category) {
            if (category.getImage() != null && !category.getImage().isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);

                picasso.with(mContext)
                        .load(category.getImage())
                                //  .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(catImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {


                            }
                        });
            }
            catName.setText(category.getName());

        }
    }

}
