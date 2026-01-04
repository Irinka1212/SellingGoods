package com.company;

public class CashRegister {
    private final Cashier cashier;

    public CashRegister(Cashier cashier) { this.cashier = cashier; }

    public Cashier getCashier() { return cashier; }

    public Receipt createReceipt(Cart cart, Shop shop) throws InvalidQuantityException {
        Receipt receipt = new Receipt(cashier);

        for (var entry : cart.getProductList().entrySet()) {
            Product product = entry.getKey();
            double quantity = entry.getValue();
            double unitPrice = shop.calculatePrice(product);

            receipt.addReceipt(product, quantity, unitPrice);
        }

        return receipt;
    }
}