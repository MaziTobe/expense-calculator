package com.motobe.expensetracker;

import java.io.Serializable;

public class AppData implements Serializable {
    private String earnedRegisterText;
    private String spentRegisterText;
    private double totalSALARY;
    private double totalSALARY_BONUS;
    private double totalGIFT_TO_ME;
    private double totalINVESTMENT_GAINS;
    private double totalPROFIT_FROM_TRADES;
    private double totalBORROWED;
    private double totalPOCKET_SELF;
    private double totalTRANSPORT_SELF;
    private double totalFEEDING_SELF;
    private double totalCLOTHING_SELF;
    private double totalBUY_THINGS_SELF;
    private double totalDO_PROJECT_SELF;
    private double totalEDUCATION_SELF;
    private double totalENTERTAINMENT_SELF;
    private double totalINVESTMENT_SELF;
    private double totalASSIST_FAMILY;
    private double totalLEND_FAMILY;
    private double totalASSIST_FRIEND;
    private double totalLEND_FRIEND;
    private double totalHELP_STRANGER;
    private double totalREPAY_LOAN;
    private double totalEarning;
    private double totalSpending;

    public AppData(String earnReg, String spendReg, double salary, double bonus, double gift, double investGain, double tradeProfit, double borrowed, double pocket, double transport, double feeding, double clothing, double buyThing, double project, double education, double entertain, double invest, double assistFam, double lendFam, double assistFriend, double lendFriend, double helpStranger, double repayLoan) {
        this.earnedRegisterText = earnReg;
        this.spentRegisterText = spendReg;
        this.totalSALARY = salary;
        this.totalSALARY_BONUS = bonus;
        this.totalGIFT_TO_ME = gift;
        this.totalINVESTMENT_GAINS = investGain;
        this.totalPROFIT_FROM_TRADES = tradeProfit;
        this.totalBORROWED = borrowed;
        this.totalPOCKET_SELF = pocket;
        this.totalTRANSPORT_SELF = transport;
        this.totalFEEDING_SELF = feeding;
        this.totalCLOTHING_SELF = clothing;
        this.totalBUY_THINGS_SELF = buyThing;
        this.totalDO_PROJECT_SELF = project;
        this.totalEDUCATION_SELF = education;
        this.totalENTERTAINMENT_SELF = entertain;
        this.totalINVESTMENT_SELF = invest;
        this.totalASSIST_FAMILY = assistFam;
        this.totalLEND_FAMILY = lendFam;
        this.totalASSIST_FRIEND = assistFriend;
        this.totalLEND_FRIEND = lendFriend;
        this.totalHELP_STRANGER = helpStranger;
        this.totalREPAY_LOAN = repayLoan;
        this.totalEarning = this.totalSALARY + this.totalSALARY_BONUS + this.totalGIFT_TO_ME + this.totalINVESTMENT_GAINS + this.totalPROFIT_FROM_TRADES + this.totalBORROWED;
        this.totalSpending = this.totalPOCKET_SELF + this.totalTRANSPORT_SELF + this.totalFEEDING_SELF + this.totalCLOTHING_SELF + this.totalBUY_THINGS_SELF + this.totalDO_PROJECT_SELF + this.totalEDUCATION_SELF + this.totalENTERTAINMENT_SELF + this.totalINVESTMENT_SELF + this.totalASSIST_FAMILY + this.totalLEND_FAMILY + this.totalASSIST_FRIEND + this.totalLEND_FRIEND + this.totalHELP_STRANGER + this.totalREPAY_LOAN;
    }

    public String getEarnedRegisterText() {
        return this.earnedRegisterText;
    }

    public String getSpentRegisterText() {
        return this.spentRegisterText;
    }

    public double getTotalSALARY() {
        return this.totalSALARY;
    }

    public double getTotalSALARY_BONUS() {
        return this.totalSALARY_BONUS;
    }

    public double getTotalGIFT_TO_ME() {
        return this.totalGIFT_TO_ME;
    }

    public double getTotalINVESTMENT_GAINS() {
        return this.totalINVESTMENT_GAINS;
    }

    public double getTotalPROFIT_FROM_TRADES() {
        return this.totalPROFIT_FROM_TRADES;
    }

    public double getTotalBORROWED() {
        return this.totalBORROWED;
    }

    public double getTotalPOCKET_SELF() {
        return this.totalPOCKET_SELF;
    }

    public double getTotalTRANSPORT_SELF() {
        return this.totalTRANSPORT_SELF;
    }

    public double getTotalFEEDING_SELF() {
        return this.totalFEEDING_SELF;
    }

    public double getTotalCLOTHING_SELF() {
        return this.totalCLOTHING_SELF;
    }

    public double getTotalBUY_THINGS_SELF() {
        return this.totalBUY_THINGS_SELF;
    }

    public double getTotalDO_PROJECT_SELF() {
        return this.totalDO_PROJECT_SELF;
    }

    public double getTotalEDUCATION_SELF() {
        return this.totalEDUCATION_SELF;
    }

    public double getTotalENTERTAINMENT_SELF() {
        return this.totalENTERTAINMENT_SELF;
    }

    public double getTotalINVESTMENT_SELF() {
        return this.totalINVESTMENT_SELF;
    }

    public double getTotalASSIST_FAMILY() {
        return this.totalASSIST_FAMILY;
    }

    public double getTotalLEND_FAMILY() {
        return this.totalLEND_FAMILY;
    }

    public double getTotalASSIST_FRIEND() {
        return this.totalASSIST_FRIEND;
    }

    public double getTotalLEND_FRIEND() {
        return this.totalLEND_FRIEND;
    }

    public double getTotalHELP_STRANGER() {
        return this.totalHELP_STRANGER;
    }

    public double getTotalREPAY_LOAN() {
        return this.totalREPAY_LOAN;
    }

    public double getTotalEarning() {
        return this.totalEarning;
    }

    public double getTotalSpending() {
        return this.totalSpending;
    }
}
