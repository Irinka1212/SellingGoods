package com.company;

public class SaleTask implements Runnable {

    private final Shop shop;
    private final CashRegister register;
    private final Client client;
    private final Cart cart;

    public SaleTask(Shop shop, CashRegister register, Client client, Cart cart) {
        this.shop = shop;
        this.register = register;
        this.client = client;
        this.cart = cart;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        try {
            System.out.printf(
                    "[%s] START sale | Client id: %d%n",
                    threadName, client.getId()
            );

            shop.processSale(register, client, cart);

            System.out.printf(
                    "[%s] FINISH sale | Client id: %d%n",
                    threadName, client.getId()
            );

        } catch (Exception e) {
            System.out.printf(
                    "[%s] SALE FAILED: %s%n",
                    threadName, e.getMessage()
            );
        }
    }
}
