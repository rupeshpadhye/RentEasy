package com.renteasy.views;

import com.renteasy.entity.SubCategory;

import java.util.List;

/**
 * Created by RUPESH on 7/31/2016.
 */
public interface ProductCatalogView extends View {
    void setUpProductViewPager(List<SubCategory> products);
}
