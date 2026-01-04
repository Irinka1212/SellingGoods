package com.company;

import java.io.Serializable;

public class ReceiptEntry implements Serializable {
    private final Product product;
    private double quantity;
    private final double unitPrice;

    public ReceiptEntry(Product product, double quantity, double unitPrice) throws InvalidQuantityException {
        if (quantity <= 0) throw new InvalidQuantityException(product);

        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Product getProduct() { return product; }
    public double getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice;}

    public double getTotalPrice() { return unitPrice * quantity; }

    public void addQuantity(double quantity) throws InvalidQuantityException {
        if (quantity <= 0) throw new InvalidQuantityException(product);
        this.quantity += quantity;
    }
}