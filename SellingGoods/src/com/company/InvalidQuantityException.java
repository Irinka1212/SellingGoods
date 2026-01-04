package com.company;

public class InvalidQuantityException extends Exception {
    public InvalidQuantityException(Product product) {
        super("Invalid quantity for product: " + product.getName());
    }
}