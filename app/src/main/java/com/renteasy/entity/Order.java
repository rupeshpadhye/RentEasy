package com.renteasy.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RUPESH on 9/20/2016.
 */
public class Order implements Serializable {
    private List<ProductDetail> productDetail;
    private Address address;

    public  Order(){
        super();
    }
    public Order(List<ProductDetail> productDetail, Address address) {
        this.productDetail = productDetail;
        this.address = address;
    }

    public List<ProductDetail> getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(List<ProductDetail> productDetail) {
        this.productDetail = productDetail;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productDetail=" + productDetail +
                ", address=" + address +
                '}';
    }
}
