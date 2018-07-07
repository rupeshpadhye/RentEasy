package com.renteasy.entity;

import android.database.Cursor;

import com.renteasy.database.DatabaseContract;

/**
 * Created by RUPESH on 8/14/2016.
 */
public class Address {

    private int ID;
    private String line1;
    private String line2;
    private String line3;
    private String line4;
    private String primaryAddress;

    public Address(){
        super();
    }

    public Address(int id,String line1, String line2, String line3, String line4,String primaryAddress) {
        this.ID=id;
        this.line1=line1;
        this.line2=line2;
        this.line3=line3;
        this.line4=line4;
        this.primaryAddress=primaryAddress;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public static Address from(Cursor cursor) {
        int Id = cursor.getInt(cursor.getColumnIndexOrThrow(
                DatabaseContract.address_table._ID));
        String line1 = cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseContract.address_table.LINE1));
        String line2 = cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseContract.address_table.LINE2));
        String line3 = cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseContract.address_table.LINE3));
        String line4 = cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseContract.address_table.LINE4));
        String primaryAddress=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.address_table.PRIMARY_ADDRESS_FLAG));
        return new Address(Id,line1,line2,line3,line4,primaryAddress);
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(String primaryAddress) {
        this.primaryAddress = primaryAddress;
    }


    @Override
    public String toString() {
        return "Address{" +
                "ID=" + ID +
                ", line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", line3='" + line3 + '\'' +
                ", line4='" + line4 + '\'' +
                ", primaryAddress='" + primaryAddress + '\'' +
                '}';
    }
}
