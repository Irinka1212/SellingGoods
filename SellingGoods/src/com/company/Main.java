package com.company;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        try {
            Shop shop = new Shop("Lidl");

            Cashier anna = new Cashier(1, "Anna", 1200);
            Cashier bob = new Cashier(2, "Bob", 1300);
            shop.addCashier(anna);
            shop.addCashier(bob);

            CashRegister firstRegister = new CashRegister(anna);
            CashRegister secondRegister = new CashRegister(anna);
            CashRegister thirdRegister = new CashRegister(bob);
            shop.addRegister(firstRegister);
            shop.addRegister(secondRegister);
            shop.addRegister(thirdRegister);

            Calendar milkExp = Calendar.getInstance();
            milkExp.add(Calendar.DAY_OF_MONTH, 3);
            Calendar bananaExp = Calendar.getInstance();
            bananaExp.add(Calendar.DAY_OF_MONTH, 7);

            Product milk = new Product(1, "Milk", 1.2, ProductCategory.FOOD, milkExp);
            Product banana = new Product(2, "Banana", 0.8, ProductCategory.FOOD, bananaExp);

            shop.deliverProduct(milk, 500);
            shop.deliverProduct(banana, 1000);

            List<Client> clients = new ArrayList<>();
            List<Cart> carts = new ArrayList<>();
            List<CashRegister> registers = Arrays.asList(firstRegister, secondRegister, thirdRegister);

            Random rand = new Random();

            for (int i = 0; i < 120; ++i) {
                Client client = new Client(i,20 + rand.nextInt(50));
                Cart cart = new Cart(shop);

                cart.addProduct(milk, 1);
                cart.addProduct(banana, 2);

                System.out.printf(
                        "Prepared Client %d | Milk: %.0f | Banana: %.0f%n",
                        i + 1,
                        cart.getProductList().get(milk),
                        cart.getProductList().get(banana)
                );

                clients.add(client);
                carts.add(cart);
            }

            ExecutorService executor = Executors.newFixedThreadPool(4, r -> {
                Thread t = new Thread(r);
                t.setName("Worker-" + t.getId());
                return t;
            });

            for (int i = 0; i < clients.size(); ++i) {
                SaleTask task = new SaleTask(shop, registers.get(i % registers.size()), clients.get(i), carts.get(i));
                executor.execute(task);
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                Thread.sleep(50);
            }

            System.out.println("\nRemaining stock in the shop:");
            shop.getAllProducts().forEach((product, quantity) ->
                    System.out.printf("%s: %.2f%n", product.getName(), quantity));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}