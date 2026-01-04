package com.company;

public class InsufficientQuantityException extends Exception {
    private final double missing;
    public InsufficientQuantityException(Product product, double missing) {
        super("Insufficient quantity for " + product.getName() + ", missing: " + missing);
        this.missing = missing;
    }
    public double getMissing() { return missing; }
}