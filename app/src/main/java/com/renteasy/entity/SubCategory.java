package com.renteasy.entity;

import java.io.Serializable;

/**
 * Created by RUPESH on 7/31/2016.
 */
public class SubCategory implements Serializable {

    private String id;
    private String name;

    public  SubCategory(){
        super();
    }
    public SubCategory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
