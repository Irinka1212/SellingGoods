package com.company;

import java.util.HashMap;

public class Cart {
    private HashMap<Product, Double> productList;
    private Shop shop;

    public Cart(Shop shop) {
        this.shop = shop;
        productList = new HashMap<>();
    }

    public HashMap<Product, Double> getProductList() { return productList; }

    public void addProduct(Product product, double quantity)
            throws InvalidQuantityException, ExpiredProductException, InsufficientQuantityException {
        if (quantity <= 0) throw new InvalidQuantityException(product);
        if (product.isExpired()) throw new ExpiredProductException(product);

        double available = shop.getProductQuantity(product);
        if (quantity > available)
            throw new InsufficientQuantityException(product, quantity - available);

        productList.put(product, productList.getOrDefault(product, 0.0) + quantity);
    }
}