package com.company;

public class ExpiredProductException extends Exception {
    public ExpiredProductException(Product product) { super("Product expired: " + product.getName()); }
}