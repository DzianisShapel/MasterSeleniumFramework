package org.selenium.pom.objects;

public class Coupon {

    private String name;
    private int discount;

    public Coupon() {
    }

    public Coupon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDiscount() {
        return discount;
    }
}
