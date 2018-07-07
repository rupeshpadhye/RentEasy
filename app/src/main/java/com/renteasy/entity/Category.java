package com.renteasy.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RUPESH on 7/30/2016.
 */
public class Category implements Serializable {
    private String id;
    private String image;
    private String name;
    private List<SubCategory> catalogs;

    public Category(){

    }

    public Category(String id, String image, String name, List<SubCategory> catalogs) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.catalogs = catalogs;
    }


    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public List<SubCategory> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<SubCategory> catalogs) {
        this.catalogs = catalogs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
