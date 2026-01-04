package com.company;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Receipt implements Serializable {
    private static int counter = 0;
    private final Cashier cashier;
    private final Calendar date;
    private final int number;
    private final List<ReceiptEntry> entries = new ArrayList<>();

    public Receipt(Cashier cashier) {
        this.cashier = cashier;
        this.date = Calendar.getInstance();
        this.number = ++counter;
    }

    public Cashier getCashier() { return cashier;}
    public int getNumber() { return number;}

    public double getTotal() {
        return entries.stream().mapToDouble(ReceiptEntry::getTotalPrice).sum();
    }

    public void addReceipt(Product product, double quantity, double unitPrice) throws InvalidQuantityException {
        if (quantity <= 0) throw new InvalidQuantityException(product);

        for (ReceiptEntry entry : entries) {
            if (entry.getProduct() == product) {
                entry.addQuantity(quantity);
                return;
            }
        }

        entries.add(new ReceiptEntry(product, quantity, unitPrice));
    }
}