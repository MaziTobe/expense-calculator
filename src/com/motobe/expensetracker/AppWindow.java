package com.motobe.expensetracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppWindow extends Application {
    protected static Stage mainStage;
    protected static MainView mainView;
    protected static EarningView earningView;
    protected static SpendingView spendingView;
    protected static FullScreenView fullScreenView;
    protected static ArrayList<Earning> loadedEarning;
    protected static ArrayList<Spending> loadedSpending;
    protected static String loadedEarningText;
    protected static String loadedSpendingText;
    protected static double totalSALARY;
    protected static double totalSALARY_BONUS;
    protected static double totalGIFT_TO_ME;
    protected static double totalINVESTMENT_GAINS;
    protected static double totalPROFIT_FROM_TRADES;
    protected static double totalBORROWED;
    protected static double totalPOCKET_SELF;
    protected static double totalTRANSPORT_SELF;
    protected static double totalFEEDING_SELF;
    protected static double totalCLOTHING_SELF;
    protected static double totalBUY_THINGS_SELF;
    protected static double totalDO_PROJECT_SELF;
    protected static double totalEDUCATION_SELF;
    protected static double totalENTERTAINMENT_SELF;
    protected static double totalINVESTMENT_SELF;
    protected static double totalASSIST_FAMILY;
    protected static double totalLEND_FAMILY;
    protected static double totalASSIST_FRIEND;
    protected static double totalLEND_FRIEND;
    protected static double totalHELP_STRANGER;
    protected static double totalREPAY_LOAN;
    protected static double totalEarning;
    protected static double totalSpending;
    private static int sessionStartTime;
    private static int sessionStopTime;

    public AppWindow() {
    }

    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        sessionStartTime = Integer.parseInt((new SimpleDateFormat("MMyyyy")).format(new Date()));
        loadedEarning = new ArrayList();
        loadedSpending = new ArrayList();
        AppData appData = this.loadDataFromFile();
        loadedEarningText = appData.getEarnedRegisterText();
        loadedSpendingText = appData.getSpentRegisterText();
        totalSALARY = appData.getTotalSALARY();
        totalSALARY_BONUS = appData.getTotalSALARY_BONUS();
        totalGIFT_TO_ME = appData.getTotalGIFT_TO_ME();
        totalINVESTMENT_GAINS = appData.getTotalINVESTMENT_GAINS();
        totalPROFIT_FROM_TRADES = appData.getTotalPROFIT_FROM_TRADES();
        totalBORROWED = appData.getTotalBORROWED();
        totalPOCKET_SELF = appData.getTotalPOCKET_SELF();
        totalTRANSPORT_SELF = appData.getTotalTRANSPORT_SELF();
        totalFEEDING_SELF = appData.getTotalFEEDING_SELF();
        totalCLOTHING_SELF = appData.getTotalCLOTHING_SELF();
        totalBUY_THINGS_SELF = appData.getTotalBUY_THINGS_SELF();
        totalDO_PROJECT_SELF = appData.getTotalDO_PROJECT_SELF();
        totalEDUCATION_SELF = appData.getTotalEDUCATION_SELF();
        totalENTERTAINMENT_SELF = appData.getTotalENTERTAINMENT_SELF();
        totalINVESTMENT_SELF = appData.getTotalINVESTMENT_SELF();
        totalASSIST_FAMILY = appData.getTotalASSIST_FAMILY();
        totalLEND_FAMILY = appData.getTotalLEND_FAMILY();
        totalASSIST_FRIEND = appData.getTotalASSIST_FRIEND();
        totalLEND_FRIEND = appData.getTotalLEND_FRIEND();
        totalHELP_STRANGER = appData.getTotalHELP_STRANGER();
        totalREPAY_LOAN = appData.getTotalREPAY_LOAN();
        totalEarning = appData.getTotalEarning();
        totalSpending = appData.getTotalSpending();
        mainView = new MainView(new VBox());
        earningView = new EarningView(new VBox());
        spendingView = new SpendingView(new VBox());
        fullScreenView = new FullScreenView(new BorderPane());
        mainStage.setTitle("My Expense Tracker");
        mainStage.setScene(mainView);
        mainStage.sizeToScene();
        mainStage.setResizable(true);
        mainStage.getIcons().add(new Image((new File("appResources/images/icon/expenseIcon.png")).toURI().toString()));
        mainStage.show();
        mainStage.setOnCloseRequest((event) -> {
            sessionStopTime = Integer.parseInt((new SimpleDateFormat("MMyyyy")).format(new Date()));
            String toBeSavedEarning = FullScreenView.earnedRegister.getText();
            String toBeSavedSpending = FullScreenView.spentRegister.getText();
            double toBeSavedSalary = EarningView.SALARY;
            double toBeSavedBonus = EarningView.SALARY_BONUS;
            double toBeSavedGift = EarningView.GIFT_TO_ME;
            double toBeSavedInvestGain = EarningView.INVESTMENT_GAINS;
            double toBeSavedTradeProfit = EarningView.PROFIT_FROM_TRADES;
            double toBeSavedBorrowed = EarningView.BORROWED;
            double toBeSavedPocket = SpendingView.POCKET_SELF;
            double toBeSavedTransport = SpendingView.TRANSPORT_SELF;
            double toBeSavedFeeding = SpendingView.FEEDING_SELF;
            double toBeSavedClothing = SpendingView.CLOTHING_SELF;
            double toBeSavedBuyThing = SpendingView.BUY_THINGS_SELF;
            double toBeSavedProject = SpendingView.DO_PROJECT_SELF;
            double toBeSavedEducation = SpendingView.EDUCATION_SELF;
            double toBeSavedEntertain = SpendingView.ENTERTAINMENT_SELF;
            double toBeSavedInvest = SpendingView.INVESTMENT_SELF;
            double toBeSavedAssistFam = SpendingView.ASSIST_FAMILY;
            double toBeSavedLendFam = SpendingView.LEND_FAMILY;
            double toBeSavedAssistFriend = SpendingView.ASSIST_FRIEND;
            double toBeSavedLendFriend = SpendingView.LEND_FRIEND;
            double toBeSavedHelpStranger = SpendingView.HELP_STRANGER;
            double toBeSavedRepayLoan = SpendingView.REPAY_LOAN;
            AppData appDataToBeSaved = new AppData(toBeSavedEarning, toBeSavedSpending, toBeSavedSalary, toBeSavedBonus, toBeSavedGift, toBeSavedInvestGain, toBeSavedTradeProfit, toBeSavedBorrowed, toBeSavedPocket, toBeSavedTransport, toBeSavedFeeding, toBeSavedClothing, toBeSavedBuyThing, toBeSavedProject, toBeSavedEducation, toBeSavedEntertain, toBeSavedInvest, toBeSavedAssistFam, toBeSavedLendFam, toBeSavedAssistFriend, toBeSavedLendFriend, toBeSavedHelpStranger, toBeSavedRepayLoan);
            this.saveDataToFile(appDataToBeSaved);
        });
    }

    public void saveDataToFile(AppData appDataObjectToPersist) {
        String fileNameMain = (new SimpleDateFormat("MMyyyy")).format(new Date());
        String fileName = fileNameMain + ".appdata";

        try {
            FileOutputStream fOutStream = new FileOutputStream(fileName);
            ObjectOutputStream objOutStream = new ObjectOutputStream(fOutStream);
            objOutStream.writeObject(appDataObjectToPersist);
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }

    public AppData loadDataFromFile() {
        String fileNameMain = (new SimpleDateFormat("MMyyyy")).format(new Date());
        String fileName = fileNameMain + ".appdata";

        try {
            FileInputStream fInStream = new FileInputStream(fileName);
            ObjectInputStream objInStream = new ObjectInputStream(fInStream);
            return (AppData)objInStream.readObject();
        } catch (ClassNotFoundException | IOException var5) {
            return new AppData("<Money Earned>\n\n", "<Money Spent>\n\n", 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    public void getStarted(String[] input) {
        launch(input);
    }
}