package com.renteasy.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RUPESH on 7/31/2016.
 */
public class ProductDetail implements Serializable {

    private String id;
    private String name;
    private String offer;
    private String image;
    private String description;
    private double actualPrice;
    private List<String> gallery;
    private List<Pricing> pricingList;
    private Pricing pricing;
    private int stock;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public List<Pricing> getPricingList() {
        return pricingList;
    }

    public void setPricingList(List<Pricing> pricingList) {
        this.pricingList = pricingList;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDetail)) return false;

        ProductDetail that = (ProductDetail) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        return !(pricing != null ? !pricing.equals(that.pricing) : that.pricing != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (pricing != null ? pricing.hashCode() : 0);
        return result;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", offer='" + offer + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", actualPrice=" + actualPrice +
                ", gallery=" + gallery +
                ", pricingList=" + pricingList +
                ", pricing=" + pricing +
                ", stock=" + stock +
                '}';
    }
}
