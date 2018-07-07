package com.renteasy.entity;

import java.io.Serializable;

/**
 * Created by RUPESH on 8/7/2016.
 */
public class Pricing implements Serializable {

    private double deposit;
    private  double price;
    private int duration;
    private String Period;

    public Pricing(){}

    public Pricing(double deposit, double price, int duration, String period) {
        this.deposit = deposit;
        this.price = price;
        this.duration = duration;
        Period = period;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String period) {
        Period = period;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pricing)) return false;

        Pricing pricing = (Pricing) o;

        if (Double.compare(pricing.deposit, deposit) != 0) return false;
        if (Double.compare(pricing.price, price) != 0) return false;
        if (duration != pricing.duration) return false;
        return !(Period != null ? !Period.equals(pricing.Period) : pricing.Period != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(deposit);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + duration;
        result = 31 * result + (Period != null ? Period.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pricing{" +
                "deposit=" + deposit +
                ", price=" + price +
                ", duration=" + duration +
                ", Period='" + Period + '\'' +
                '}';
    }
}
