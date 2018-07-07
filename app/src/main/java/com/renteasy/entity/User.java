package com.renteasy.entity;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

/**
 * Created by RUPESH on 9/3/2016.
 */
public class User implements Serializable {
    private String uuid;
    private String displayName;
    private String email;
    //private Uri photoUrl;
    private String mobileNo;
    private boolean isAnonymous;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }*/

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
    public User(){
        super();
    }
    public User(FirebaseUser user){
        this.setUuid(user.getUid());
        this.setDisplayName(user.getDisplayName());
        this.setEmail(user.getEmail());
        //this.setPhotoUrl(user.getPhotoUrl());
        this.setIsAnonymous(user.isAnonymous());

    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
