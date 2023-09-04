package com.motobe.expensetracker;

public class Spending {
    private double amountSpent;
    private String transactionID;
    private int transactionDay;
    private int transactionMonth;
    private int transactionYear;
    private String transactionDescription;
    private String transactionCategory;

    public Spending(double tAmount, String tID, String tDescription, String tCategory, int tDay, int tMonth, int tYear) {
        this.amountSpent = tAmount;
        this.transactionID = tID;
        this.transactionDescription = tDescription;
        this.transactionCategory = tCategory;
        this.transactionDay = tDay;
        this.transactionMonth = tMonth;
        this.transactionYear = tYear;
    }

    public double getAmountSpent() {
        return this.amountSpent;
    }

    public String getTransactionID() {
        return this.transactionID;
    }

    public int getTransactionDay() {
        return this.transactionDay;
    }

    public int getTransactionMonth() {
        return this.transactionMonth;
    }

    public int getTransactionYear() {
        return this.transactionYear;
    }

    public String getTransactionDescription() {
        return this.transactionDescription;
    }

    public String getTransactionCategory() {
        return this.transactionCategory;
    }
}