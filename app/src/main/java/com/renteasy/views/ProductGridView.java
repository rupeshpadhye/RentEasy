package com.renteasy.views;

import com.renteasy.entity.ProductDetail;
import com.renteasy.reciever.ProductReciever;

import java.util.List;

/**
 * Created by RUPESH on 7/31/2016.
 */
public interface ProductGridView extends View, ProductReciever.Receiver {
    void bindProductsGrid(List<ProductDetail> productDetailList);
    void showProductDetail(ProductDetail productDetail);

}
