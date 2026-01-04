package com.company;

import java.util.*;

public class Shop {

    private final String name;
    private final Map<Product, Double> stock = new HashMap<>();
    private final List<CashRegister> registers = new ArrayList<>();
    private final List<Cashier> cashiers = new ArrayList<>();

    private int receiptCount = 0;
    private double totalDeliveryCost = 0;
    private double totalTurnover = 0;

    private double foodMarkup = 0.30;
    private double nonFoodMarkup = 0.50;
    private double discountPercent = 0.20;
    private int discountDays = 5;

    public Shop(String name) { this.name = name; }

    public String getName() { return name; }
    public int getReceiptCount() { return receiptCount; }
    public double getTotalDeliveryCost() { return totalDeliveryCost;}
    public double getTotalTurnover() { return totalTurnover; }
    public double getTotalProfit() { return totalTurnover - totalDeliveryCost;}

    public double getProductQuantity(Product product) {
        return stock.getOrDefault(product, 0.0);
    }

    public double getSalaryExpenses() {
        return cashiers.stream().mapToDouble(Cashier::getMonthlySalary).sum();
    }

    public Map<Product, Double> getAllProducts() {
        return Collections.unmodifiableMap(stock);
    }

    public void addCashier(Cashier cashier) {
        cashiers.add(cashier);
    }

    public void addRegister(CashRegister register) {
        registers.add(register);
    }

    public void deliverProduct(Product product, double quantity) throws InvalidQuantityException {
        if (quantity <= 0) throw new InvalidQuantityException(product);
        stock.put(product, stock.getOrDefault(product, 0.0) + quantity);
        totalDeliveryCost += product.getDeliveryPrice() * quantity;
    }

    public synchronized void processSale(CashRegister register, Client client, Cart cart)
            throws InvalidQuantityException, ExpiredProductException, InsufficientQuantityException, InsufficientFundsException {

        double total = calculateCartTotal(cart);

        if (!client.canPay(total)) throw new InsufficientFundsException(total - client.getMoney());

        sellCartProducts(cart);
        client.pay(total);
        register.createReceipt(cart, this);

        ++receiptCount;
        totalTurnover += total;
    }

    private double calculateCartTotal(Cart cart) throws InvalidQuantityException, ExpiredProductException {
        double total = 0;
        for (var entry : cart.getProductList().entrySet()) {
            Product product = entry.getKey();
            double quantity = entry.getValue();

            if (quantity <= 0) throw new InvalidQuantityException(product);
            if (product.isExpired()) throw new ExpiredProductException(product);

            total += calculatePrice(product) * quantity;
        }
        return total;
    }

    private void sellCartProducts(Cart cart)
            throws InvalidQuantityException, ExpiredProductException, InsufficientQuantityException {

        for (var entry : cart.getProductList().entrySet()) {
            sellProduct(entry.getKey(), entry.getValue());
        }
    }

    private void sellProduct(Product product, double quantity)
            throws InvalidQuantityException, ExpiredProductException, InsufficientQuantityException {

        if (quantity <= 0) throw new InvalidQuantityException(product);
        if (product.isExpired()) throw new ExpiredProductException(product);

        double available = stock.getOrDefault(product, 0.0);
        if (available < quantity) throw new InsufficientQuantityException(product, quantity - available);

        stock.put(product, available - quantity);
    }

    public double calculatePrice(Product product) {
        double markup = product.getCategory() == ProductCategory.FOOD ? foodMarkup : nonFoodMarkup;
        double price = product.getDeliveryPrice() * (1 + markup);
        long daysLeft = product.getDaysUntilExpiration();

        if (daysLeft >= 0 && daysLeft <= discountDays) {
            double discountedPrice = price * (1 - discountPercent);
            price = Math.max(discountedPrice, product.getDeliveryPrice() * 1.01);
        }

        return price;
    }
}