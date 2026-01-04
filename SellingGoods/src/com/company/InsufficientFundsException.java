package com.company;

public class InsufficientFundsException extends Exception {
    private final double missingAmount;

    public InsufficientFundsException(double missingAmount) {
        super("Client cannot pay: missing " + missingAmount);
        this.missingAmount = missingAmount;
    }

    public double getMissingAmount() {
        return missingAmount;
    }
}