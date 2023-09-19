package com.motobe.expensetracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SpendingView extends Scene {
    private final PieChart myPieChart;

    public SpendingView(VBox root) {
        super(root);
        ComboBox timeScaleSelector = new ComboBox();
        timeScaleSelector.getItems().add("Today's Transactions");
        timeScaleSelector.getItems().add("Transactions Month to Date");
        timeScaleSelector.getItems().add("Transactions Year to Date");
        timeScaleSelector.setPrefSize(240.0D, 30.0D);
        HBox timeScaleContainer = new HBox();
        timeScaleContainer.setAlignment(Pos.CENTER);
        timeScaleContainer.setPrefSize(400.0D, 60.0D);
        timeScaleContainer.getChildren().addAll(timeScaleSelector);
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
        chartBox.getChildren().addAll(this.myPieChart);
        setChartData();
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
        amountBox.getChildren().addAll(amountTag, amountInput);
        Label categoryTag = new Label("CATEGORY");
        categoryTag.setBackground(AppState.labelBackground);
        categoryTag.setFont(AppState.labelFont);
        categoryTag.setTextFill(AppState.labelTextFill);
        categoryTag.setAlignment(Pos.CENTER);
        categoryTag.setPrefSize(120.0D, 30.0D);
        ComboBox categorySelector = new ComboBox();
        SpendCategory[] var10 = SpendCategory.values();
        for (SpendCategory spendingCat : var10) {
            categorySelector.getItems().add(spendingCat);
        }
        categorySelector.setPrefSize(200.0D, 30.0D);
        HBox categoryBox = new HBox();
        categoryBox.setAlignment(Pos.CENTER);
        categoryBox.setPrefSize(400.0D, 60.0D);
        categoryBox.getChildren().addAll(categoryTag, categorySelector);
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
        descriptionBox.getChildren().addAll(descriptionTag, descriptionInput);
        Button submit = new Button("SUBMIT");
        submit.setFont(AppState.buttonFont);
        submit.setTextFill(AppState.buttonTextFill);
        submit.setBackground(AppState.spendButtonBackground);
        submit.setPrefSize(80.0D, 30.0D);
        submit.setOnAction((event) -> {
            try {
                Spending newSpending = new Spending();
                newSpending.setAmountSpent(new Scanner(amountInput.getText()).nextDouble());
                newSpending.setTransactionDescription(new Scanner(descriptionInput.getText()).nextLine());
                newSpending.setTransactionCategory(categorySelector.getSelectionModel().getSelectedItem().toString());
                newSpending.setTransactionID(new SimpleDateFormat("ssmmHHddMMyyyy").format(new Date()));
                newSpending.setTransactionDay(Integer.parseInt((new SimpleDateFormat("dd")).format(new Date())));
                newSpending.setTransactionMonth(Integer.parseInt((new SimpleDateFormat("MM")).format(new Date())));
                newSpending.setTransactionYear(Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date())));
                AppData.loadedSpending.add(newSpending);
                setChartData();
            } catch (InputMismatchException var22) {
                new Alert(AlertType.ERROR,"Only numbers accepted for the amount input").show();
            } catch (NoSuchElementException var23) {
                new Alert(AlertType.ERROR,"All fields must be filled").show();
            }
            amountInput.setText("");
            categorySelector.setValue(null);
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
        submitAndGotoBox.getChildren().addAll(submit, gotoMainView);
        root.setPrefSize(400.0D, 600.0D);
        root.setBackground(AppState.appBackground);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(timeScaleContainer, chartBox, amountBox, categoryBox, descriptionBox, submitAndGotoBox);
    }

    protected void setChartData() {
        double POCKET_SELF = 0.0D;
        double TRANSPORT_SELF = 0.0D;
        double FEEDING_SELF = 0.0D;
        double CLOTHING_SELF = 0.0D;
        double BUY_THINGS_SELF = 0.0D;
        double DO_PROJECT_SELF = 0.0D;
        double EDUCATION_SELF = 0.0D;
        double ENTERTAINMENT_SELF = 0.0D;
        double INVESTMENT_SELF = 0.0D;
        double ASSIST_FAMILY = 0.0D;
        double LEND_FAMILY = 0.0D;
        double ASSIST_FRIEND = 0.0D;
        double LEND_FRIEND = 0.0D;
        double HELP_STRANGER = 0.0D;
        double REPAY_LOAN = 0.0D;

        for(Spending spend: AppData.loadedSpending){
            switch (spend.getTransactionCategory()) {
                case "POCKET_SELF":
                    POCKET_SELF += spend.getAmountSpent();
                    break;
                case "TRANSPORT_SELF":
                    TRANSPORT_SELF += spend.getAmountSpent();
                    break;
                case "FEEDING_SELF":
                    FEEDING_SELF += spend.getAmountSpent();
                    break;
                case "CLOTHING_SELF":
                    CLOTHING_SELF += spend.getAmountSpent();
                    break;
                case "BUY_THINGS_SELF":
                    BUY_THINGS_SELF += spend.getAmountSpent();
                    break;
                case "DO_PROJECT_SELF":
                    DO_PROJECT_SELF += spend.getAmountSpent();
                    break;
                case "EDUCATION_SELF":
                    EDUCATION_SELF += spend.getAmountSpent();
                    break;
                case "ENTERTAINMENT_SELF":
                    ENTERTAINMENT_SELF += spend.getAmountSpent();
                    break;
                case "INVESTMENT_SELF":
                    INVESTMENT_SELF += spend.getAmountSpent();
                    break;
                case "ASSIST_FAMILY":
                    ASSIST_FAMILY += spend.getAmountSpent();
                    break;
                case "LEND_FAMILY":
                    LEND_FAMILY += spend.getAmountSpent();
                    break;
                case "ASSIST_FRIEND":
                    ASSIST_FRIEND += spend.getAmountSpent();
                    break;
                case "LEND_FRIEND":
                    LEND_FRIEND += spend.getAmountSpent();
                    break;
                case "HELP_STRANGER":
                    HELP_STRANGER += spend.getAmountSpent();
                    break;
                case "REPAY_LOAN":
                    REPAY_LOAN += spend.getAmountSpent();
                    break;
            }
        }

        ObservableList<Data> myPieChartData = FXCollections.observableArrayList(
                new Data("POCKET_SELF", POCKET_SELF),
                new Data("TRANSPORT_SELF", TRANSPORT_SELF),
                new Data("FEEDING_SELF", FEEDING_SELF),
                new Data("CLOTHING_SELF", CLOTHING_SELF),
                new Data("BUY_THINGS_SELF", BUY_THINGS_SELF),
                new Data("DO_PROJECT_SELF", DO_PROJECT_SELF),
                new Data("EDUCATION_SELF", EDUCATION_SELF),
                new Data("ENTERTAINMENT_SELF", ENTERTAINMENT_SELF),
                new Data("INVESTMENT_SELF", INVESTMENT_SELF),
                new Data("ASSIST_FAMILY", ASSIST_FAMILY),
                new Data("LEND_FAMILY", LEND_FAMILY),
                new Data("ASSIST_FRIEND", ASSIST_FRIEND),
                new Data("LEND_FRIEND", LEND_FRIEND),
                new Data("HELP_STRANGER", HELP_STRANGER),
                new Data("REPAY_LOAN", REPAY_LOAN));
        this.myPieChart.setData(myPieChartData);
    }
}
