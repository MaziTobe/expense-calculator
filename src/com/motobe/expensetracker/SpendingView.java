package com.motobe.expensetracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SpendingView extends Scene {
    protected static ArrayList<Spending> totalSpending;
    private PieChart myPieChart;
    protected static double POCKET_SELF;
    protected static double TRANSPORT_SELF;
    protected static double FEEDING_SELF;
    protected static double CLOTHING_SELF;
    protected static double BUY_THINGS_SELF;
    protected static double DO_PROJECT_SELF;
    protected static double EDUCATION_SELF;
    protected static double ENTERTAINMENT_SELF;
    protected static double INVESTMENT_SELF;
    protected static double ASSIST_FAMILY;
    protected static double LEND_FAMILY;
    protected static double ASSIST_FRIEND;
    protected static double LEND_FRIEND;
    protected static double HELP_STRANGER;
    protected static double REPAY_LOAN;
    private boolean isFirstRun = true;

    private String currencyUnit= "NGN";

    public SpendingView(VBox root) {
        super(root);
        totalSpending = AppWindow.loadedSpending;
        POCKET_SELF = AppWindow.totalPOCKET_SELF;
        TRANSPORT_SELF = AppWindow.totalTRANSPORT_SELF;
        FEEDING_SELF = AppWindow.totalFEEDING_SELF;
        CLOTHING_SELF = AppWindow.totalCLOTHING_SELF;
        BUY_THINGS_SELF = AppWindow.totalBUY_THINGS_SELF;
        DO_PROJECT_SELF = AppWindow.totalDO_PROJECT_SELF;
        EDUCATION_SELF = AppWindow.totalEDUCATION_SELF;
        ENTERTAINMENT_SELF = AppWindow.totalENTERTAINMENT_SELF;
        INVESTMENT_SELF = AppWindow.totalINVESTMENT_SELF;
        ASSIST_FAMILY = AppWindow.totalASSIST_FAMILY;
        LEND_FAMILY = AppWindow.totalLEND_FAMILY;
        ASSIST_FRIEND = AppWindow.totalASSIST_FRIEND;
        LEND_FRIEND = AppWindow.totalLEND_FRIEND;
        HELP_STRANGER = AppWindow.totalHELP_STRANGER;
        REPAY_LOAN = AppWindow.totalREPAY_LOAN;
        ComboBox timeScaleSelector = new ComboBox();
        timeScaleSelector.getItems().add("Today's Transactions");
        timeScaleSelector.getItems().add("Transactions Month to Date");
        timeScaleSelector.getItems().add("Transactions Year to Date");
        timeScaleSelector.setPrefSize(240.0D, 30.0D);
        HBox timeScaleContainer = new HBox();
        timeScaleContainer.setAlignment(Pos.CENTER);
        timeScaleContainer.setPrefSize(400.0D, 60.0D);
        timeScaleContainer.getChildren().addAll(new Node[]{timeScaleSelector});
        this.myPieChart = new PieChart();
        this.myPieChart.setLabelLineLength(2.0D);
        this.myPieChart.setTitle("Expense Category Chart");
        this.myPieChart.setBackground(AppState.chartBackground);
        this.myPieChart.setPrefSize(320.0D, 240.0D);
        this.myPieChart.setMinSize(320.0D, 240.0D);
        this.myPieChart.setMaxSize(320.0D, 240.0D);
        this.myPieChart.setLegendVisible(true);
        this.myPieChart.setOpacity(100.0D);
        this.myPieChart.setVisible(true);
        HBox chartBox = new HBox();
        chartBox.setAlignment(Pos.CENTER);
        chartBox.setPrefSize(400.0D, 240.0D);
        chartBox.getChildren().addAll(new Node[]{this.myPieChart});
        this.setChartData();
        Label amountTag = new Label("AMOUNT");
        amountTag.setBackground(AppState.labelBackground);
        amountTag.setFont(AppState.labelFont);
        amountTag.setTextFill(AppState.labelTextFill);
        amountTag.setAlignment(Pos.CENTER);
        amountTag.setPrefSize(120.0D, 30.0D);
        TextField amountInput = new TextField();
        amountInput.setPrefSize(200.0D, 30.0D);
        HBox amountBox = new HBox();
        amountBox.setAlignment(Pos.CENTER);
        amountBox.setPrefSize(400.0D, 60.0D);
        amountBox.getChildren().addAll(new Node[]{amountTag, amountInput});
        Label categoryTag = new Label("CATEGORY");
        categoryTag.setBackground(AppState.labelBackground);
        categoryTag.setFont(AppState.labelFont);
        categoryTag.setTextFill(AppState.labelTextFill);
        categoryTag.setAlignment(Pos.CENTER);
        categoryTag.setPrefSize(120.0D, 30.0D);
        ComboBox categorySelector = new ComboBox();
        SpendCategory[] var10 = SpendCategory.values();
        int var11 = var10.length;

        for(int var12 = 0; var12 < var11; ++var12) {
            SpendCategory spendingCat = var10[var12];
            categorySelector.getItems().add(spendingCat);
        }

        categorySelector.setPrefSize(200.0D, 30.0D);
        HBox categoryBox = new HBox();
        categoryBox.setAlignment(Pos.CENTER);
        categoryBox.setPrefSize(400.0D, 60.0D);
        categoryBox.getChildren().addAll(new Node[]{categoryTag, categorySelector});
        Label descriptionTag = new Label("SPENDING\nDESCRIPTION");
        descriptionTag.setBackground(AppState.labelBackground);
        descriptionTag.setFont(AppState.labelFont);
        descriptionTag.setTextFill(AppState.labelTextFill);
        descriptionTag.setAlignment(Pos.CENTER);
        descriptionTag.setPrefSize(120.0D, 120.0D);
        TextArea descriptionInput = new TextArea();
        descriptionInput.setWrapText(true);
        descriptionInput.setPrefSize(200.0D, 120.0D);
        HBox descriptionBox = new HBox();
        descriptionBox.setAlignment(Pos.CENTER);
        descriptionBox.setPrefSize(400.0D, 120.0D);
        descriptionBox.getChildren().addAll(new Node[]{descriptionTag, descriptionInput});
        Button submit = new Button("SUBMIT");
        submit.setFont(AppState.buttonFont);
        submit.setTextFill(AppState.buttonTextFill);
        submit.setBackground(AppState.spendButtonBackground);
        submit.setPrefSize(80.0D, 30.0D);
        submit.setOnAction((event) -> {
            try {
                double amount = (new Scanner(amountInput.getText())).nextDouble();
                String description = (new Scanner(descriptionInput.getText())).nextLine();
                String category = categorySelector.getSelectionModel().getSelectedItem().toString();
                String ID = (new SimpleDateFormat("ssmmHHddMMyyyy")).format(new Date());
                int day = Integer.parseInt((new SimpleDateFormat("dd")).format(new Date()));
                int month = Integer.parseInt((new SimpleDateFormat("MM")).format(new Date()));
                int year = Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date()));
                Spending newSpending = new Spending(amount, ID, description, category, day, month, year);
                double tAmount = newSpending.getAmountSpent();
                String tID = newSpending.getTransactionID();
                String tDescription = newSpending.getTransactionDescription();
                String tCategory = newSpending.getTransactionCategory();
                int tDay = newSpending.getTransactionDay();
                int tMonth = newSpending.getTransactionMonth();
                int tYear = newSpending.getTransactionYear();
                FullScreenView.spentRegister.appendText("ID: " + tID + "\nAmount Spent: " + tAmount + " " + currencyUnit + "\nCategory: " + tCategory + "\nTransaction Date: " + tDay + "/" + tMonth + "/" + tYear + "\nTransaction Description: " + tDescription + "\n\n");
                totalSpending.add(newSpending);
                this.setChartData();
            } catch (InputMismatchException var22) {
                (new Alert(AlertType.ERROR, "Only numbers accepted for the amount input", new ButtonType[0])).show();
            } catch (NoSuchElementException var23) {
                (new Alert(AlertType.ERROR, "All fields must be filled", new ButtonType[0])).show();
            }

            amountInput.setText("");
            categorySelector.setValue((Object)null);
            descriptionInput.setText("");
        });
        Button gotoMainView = new Button("QUIT");
        gotoMainView.setFont(AppState.buttonFont);
        gotoMainView.setTextFill(AppState.buttonTextFill);
        gotoMainView.setBackground(AppState.quitButtonBackground);
        gotoMainView.setPrefSize(80.0D, 30.0D);
        gotoMainView.setOnAction((event) -> {
            MainView.setChartData();
            AppWindow.mainStage.setScene(AppWindow.mainView);
        });
        HBox submitAndGotoBox = new HBox();
        submitAndGotoBox.setAlignment(Pos.CENTER);
        submitAndGotoBox.setSpacing(50.0D);
        submitAndGotoBox.setPrefSize(400.0D, 60.0D);
        submitAndGotoBox.getChildren().addAll(new Node[]{submit, gotoMainView});
        root.setPrefSize(400.0D, 600.0D);
        root.setBackground(AppState.appBackground);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(new Node[]{timeScaleContainer, chartBox, amountBox, categoryBox, descriptionBox, submitAndGotoBox});
    }

    protected void setChartData() {
        double totalPOCKET_SELF = 0.0D;
        double totalTRANSPORT_SELF = 0.0D;
        double totalFEEDING_SELF = 0.0D;
        double totalCLOTHING_SELF = 0.0D;
        double totalBUY_THINGS_SELF = 0.0D;
        double totalDO_PROJECT_SELF = 0.0D;
        double totalEDUCATION_SELF = 0.0D;
        double totalENTERTAINMENT_SELF = 0.0D;
        double totalINVESTMENT_SELF = 0.0D;
        double totalASSIST_FAMILY = 0.0D;
        double totalLEND_FAMILY = 0.0D;
        double totalASSIST_FRIEND = 0.0D;
        double totalLEND_FRIEND = 0.0D;
        double totalHELP_STRANGER = 0.0D;
        double totalREPAY_LOAN = 0.0D;
        Iterator var31 = totalSpending.iterator();

        while(var31.hasNext()) {
            Spending spending = (Spending)var31.next();
            if (spending.getTransactionCategory().equals("POCKET_SELF")) {
                totalPOCKET_SELF += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("TRANSPORT_SELF")) {
                totalTRANSPORT_SELF += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("FEEDING_SELF")) {
                totalFEEDING_SELF += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("CLOTHING_SELF")) {
                totalCLOTHING_SELF += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("BUY_THINGS_SELF")) {
                totalBUY_THINGS_SELF += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("DO_PROJECT_SELF")) {
                totalDO_PROJECT_SELF += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("EDUCATION_SELF")) {
                totalEDUCATION_SELF += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("ENTERTAINMENT_SELF")) {
                totalENTERTAINMENT_SELF += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("INVESTMENT_SELF")) {
                totalINVESTMENT_SELF += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("ASSIST_FAMILY")) {
                totalASSIST_FAMILY += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("LEND_FAMILY")) {
                totalLEND_FAMILY += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("ASSIST_FRIEND")) {
                totalASSIST_FRIEND += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("LEND_FRIEND")) {
                totalLEND_FRIEND += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("HELP_STRANGER")) {
                totalHELP_STRANGER += spending.getAmountSpent();
            } else if (spending.getTransactionCategory().equals("REPAY_LOAN")) {
                totalREPAY_LOAN += spending.getAmountSpent();
            }
        }

        if (this.isFirstRun) {
            this.isFirstRun = false;
        } else {
            POCKET_SELF = AppWindow.totalPOCKET_SELF + totalPOCKET_SELF;
            TRANSPORT_SELF = AppWindow.totalTRANSPORT_SELF + totalTRANSPORT_SELF;
            FEEDING_SELF = AppWindow.totalFEEDING_SELF + totalFEEDING_SELF;
            CLOTHING_SELF = AppWindow.totalCLOTHING_SELF + totalCLOTHING_SELF;
            BUY_THINGS_SELF = AppWindow.totalBUY_THINGS_SELF + totalBUY_THINGS_SELF;
            DO_PROJECT_SELF = AppWindow.totalDO_PROJECT_SELF + totalDO_PROJECT_SELF;
            EDUCATION_SELF = AppWindow.totalEDUCATION_SELF + totalEDUCATION_SELF;
            ENTERTAINMENT_SELF = AppWindow.totalENTERTAINMENT_SELF + totalENTERTAINMENT_SELF;
            INVESTMENT_SELF = AppWindow.totalINVESTMENT_SELF + totalINVESTMENT_SELF;
            ASSIST_FAMILY = AppWindow.totalASSIST_FAMILY + totalASSIST_FAMILY;
            LEND_FAMILY = AppWindow.totalLEND_FAMILY + totalLEND_FAMILY;
            ASSIST_FRIEND = AppWindow.totalASSIST_FRIEND + totalASSIST_FRIEND;
            LEND_FRIEND = AppWindow.totalLEND_FRIEND + totalLEND_FRIEND;
            HELP_STRANGER = AppWindow.totalHELP_STRANGER + totalHELP_STRANGER;
            REPAY_LOAN = AppWindow.totalREPAY_LOAN + totalREPAY_LOAN;
        }

        ObservableList<Data> myPieChartData = FXCollections.observableArrayList(new Data[]{new Data("POCKET_SELF", POCKET_SELF), new Data("TRANSPORT_SELF", TRANSPORT_SELF), new Data("FEEDING_SELF", FEEDING_SELF), new Data("CLOTHING_SELF", CLOTHING_SELF), new Data("BUY_THINGS_SELF", BUY_THINGS_SELF), new Data("DO_PROJECT_SELF", DO_PROJECT_SELF), new Data("EDUCATION_SELF", EDUCATION_SELF), new Data("ENTERTAINMENT_SELF", ENTERTAINMENT_SELF), new Data("INVESTMENT_SELF", INVESTMENT_SELF), new Data("ASSIST_FAMILY", ASSIST_FAMILY), new Data("LEND_FAMILY", LEND_FAMILY), new Data("ASSIST_FRIEND", ASSIST_FRIEND), new Data("LEND_FRIEND", LEND_FRIEND), new Data("HELP_STRANGER", HELP_STRANGER), new Data("REPAY_LOAN", REPAY_LOAN)});
        this.myPieChart.setData(myPieChartData);
    }
}
