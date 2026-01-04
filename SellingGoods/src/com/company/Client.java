package com.company;

public class Client {
    private final int id;
    private double money;

    public Client(int id, double money) {
        this.id = id;
        this.money = money;
    }

    public int getId() { return id; }
    public double getMoney() { return money; }
    public boolean canPay(double amount) { return money >= amount; }

    public void pay(double amount) throws InsufficientFundsException {
        if (!canPay(amount)) throw new InsufficientFundsException((amount - money));
        money -= amount;
    }

    @Override
    public String toString() {
        return "Client{money=" + money + "}";
    }
}