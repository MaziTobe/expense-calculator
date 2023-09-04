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

public class EarningView extends Scene {
    protected static ArrayList<Earning> totalEarning;
    private PieChart myPieChart;
    protected static double SALARY;
    protected static double SALARY_BONUS;
    protected static double GIFT_TO_ME;
    protected static double INVESTMENT_GAINS;
    protected static double PROFIT_FROM_TRADES;
    protected static double BORROWED;
    private boolean isFirstRun = true;

    public EarningView(VBox root) {
        super(root);
        totalEarning = AppWindow.loadedEarning;
        SALARY = AppWindow.totalSALARY;
        SALARY_BONUS = AppWindow.totalSALARY_BONUS;
        GIFT_TO_ME = AppWindow.totalGIFT_TO_ME;
        INVESTMENT_GAINS = AppWindow.totalINVESTMENT_GAINS;
        PROFIT_FROM_TRADES = AppWindow.totalPROFIT_FROM_TRADES;
        BORROWED = AppWindow.totalBORROWED;
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
        this.myPieChart.setTitle("Earning Category Chart");
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
        EarnCategory[] var10 = EarnCategory.values();
        int var11 = var10.length;

        for(int var12 = 0; var12 < var11; ++var12) {
            EarnCategory earningCat = var10[var12];
            categorySelector.getItems().add(earningCat);
        }

        categorySelector.setPrefSize(200.0D, 30.0D);
        HBox categoryBox = new HBox();
        categoryBox.setAlignment(Pos.CENTER);
        categoryBox.setPrefSize(400.0D, 60.0D);
        categoryBox.getChildren().addAll(new Node[]{categoryTag, categorySelector});
        Label descriptionTag = new Label("EARNING\nDESCRIPTION");
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
        submit.setBackground(AppState.earnButtonBackground);
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
                Earning newEarning = new Earning(amount, ID, description, category, day, month, year);
                double tAmount = newEarning.getAmountEarned();
                String tID = newEarning.getTransactionID();
                String tDescription = newEarning.getTransactionDescription();
                String tCategory = newEarning.getTransactionCategory();
                int tDay = newEarning.getTransactionDay();
                int tMonth = newEarning.getTransactionMonth();
                int tYear = newEarning.getTransactionYear();
                FullScreenView.earnedRegister.appendText("ID: " + tID + "\nAmount Earned: " + tAmount + "\nCategory: " + tCategory + "\nTransaction Date: " + tDay + "/" + tMonth + "/" + tYear + "\nTransaction Description: " + tDescription + "\n\n");
                totalEarning.add(newEarning);
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
        double totalSALARY = 0.0D;
        double totalSALARY_BONUS = 0.0D;
        double totalGIFT_TO_ME = 0.0D;
        double totalINVESTMENT_GAINS = 0.0D;
        double totalPROFIT_FROM_TRADES = 0.0D;
        double totalBORROWED = 0.0D;
        Iterator var13 = totalEarning.iterator();

        while(var13.hasNext()) {
            Earning earning = (Earning)var13.next();
            if (earning.getTransactionCategory().equals("SALARY")) {
                totalSALARY += earning.getAmountEarned();
            } else if (earning.getTransactionCategory().equals("SALARY_BONUS")) {
                totalSALARY_BONUS += earning.getAmountEarned();
            } else if (earning.getTransactionCategory().equals("GIFT_TO_ME")) {
                totalGIFT_TO_ME += earning.getAmountEarned();
            } else if (earning.getTransactionCategory().equals("INVESTMENT_GAINS")) {
                totalINVESTMENT_GAINS += earning.getAmountEarned();
            } else if (earning.getTransactionCategory().equals("PROFIT_FROM_TRADES")) {
                totalPROFIT_FROM_TRADES += earning.getAmountEarned();
            } else if (earning.getTransactionCategory().equals("BORROWED")) {
                totalBORROWED += earning.getAmountEarned();
            }
        }

        if (this.isFirstRun) {
            this.isFirstRun = false;
        } else {
            SALARY = AppWindow.totalSALARY + totalSALARY;
            SALARY_BONUS = AppWindow.totalSALARY_BONUS + totalSALARY_BONUS;
            GIFT_TO_ME = AppWindow.totalGIFT_TO_ME + totalGIFT_TO_ME;
            INVESTMENT_GAINS = AppWindow.totalINVESTMENT_GAINS + totalINVESTMENT_GAINS;
            PROFIT_FROM_TRADES = AppWindow.totalPROFIT_FROM_TRADES + totalPROFIT_FROM_TRADES;
            BORROWED = AppWindow.totalBORROWED + totalBORROWED;
        }

        ObservableList<Data> myPieChartData = FXCollections.observableArrayList(new Data[]{new Data("SALARY", SALARY), new Data("SALARY_BONUS", SALARY_BONUS), new Data("GIFT_TO_ME", GIFT_TO_ME), new Data("INVESTMENT_GAINS", INVESTMENT_GAINS), new Data("PROFIT_FROM_TRADES", PROFIT_FROM_TRADES), new Data("BORROWED", BORROWED)});
        this.myPieChart.setData(myPieChartData);
    }
}