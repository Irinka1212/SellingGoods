package com.company;

import java.io.Serializable;

public class Cashier implements Serializable {
    private final int id;
    private String name;
    private double monthlySalary;

    public Cashier(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.monthlySalary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getMonthlySalary() { return monthlySalary; }
}