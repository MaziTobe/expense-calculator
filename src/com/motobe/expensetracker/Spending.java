package com.motobe.expensetracker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Spending {
    private double amountSpent;
    private String transactionID;
    private int transactionDay;
    private int transactionMonth;
    private int transactionYear;
    private String transactionDescription;
    private String transactionCategory;

    public Spending() {
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public int getTransactionDay() {
        return transactionDay;
    }

    public void setTransactionDay(int transactionDay) {
        this.transactionDay = transactionDay;
    }

    public int getTransactionMonth() {
        return transactionMonth;
    }

    public void setTransactionMonth(int transactionMonth) {
        this.transactionMonth = transactionMonth;
    }

    public int getTransactionYear() {
        return transactionYear;
    }

    public void setTransactionYear(int transactionYear) {
        this.transactionYear = transactionYear;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    @Override
    public String toString() {
        return "Spending{" +
                "amountSpent=" + amountSpent +
                ", transactionID='" + transactionID + '\'' +
                ", transactionDay=" + transactionDay +
                ", transactionMonth=" + transactionMonth +
                ", transactionYear=" + transactionYear +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", transactionCategory='" + transactionCategory + '\'' +
                '}';
    }
}