package com.company;

public class ReceiptFileNotFoundException extends Exception {
    public ReceiptFileNotFoundException(String fileName) {
        super("Failed to read receipt file: " + fileName);
    }
}