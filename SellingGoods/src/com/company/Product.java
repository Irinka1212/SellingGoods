package com.company;

import java.io.Serializable;
import java.util.Calendar;

public class Product implements Serializable {
    private final int id;
    private final String name;
    private final double deliveryPrice;
    private final ProductCategory category;
    private final Calendar expirationDate;

    public Product(int id, String name, double deliveryPrice, ProductCategory category, Calendar expirationDate) {
        this.id = id;
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.category = category;
        this.expirationDate = expirationDate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getDeliveryPrice() { return deliveryPrice; }
    public ProductCategory getCategory() { return category; }
    public Calendar getExpirationDate() { return expirationDate; }

    public long getDaysUntilExpiration() {
        long difference = expirationDate.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        return difference / (1000 * 60 * 60 * 24); // milliseconds -> days
    }
    public boolean isExpired() {
        return expirationDate.before(Calendar.getInstance());
    }
}