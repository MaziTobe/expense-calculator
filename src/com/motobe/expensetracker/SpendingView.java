package com.motobe.expensetracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SpendingView extends Scene {
    private static ComboBox timeScaleSelector;
    private BarChart<String, Number> myBarChart;

    public SpendingView(VBox root) {
        super(root);
        timeScaleSelector = new ComboBox();
        IntervalCategory[] intervalCategories= IntervalCategory.values();
        for (IntervalCategory intervalCat : intervalCategories) {
            timeScaleSelector.getItems().add(intervalCat);
        }
        timeScaleSelector.getSelectionModel().selectLast();
        timeScaleSelector.setOnAction(event -> setChartData());
        timeScaleSelector.setPrefSize(240.0D, 30.0D);
        HBox timeScaleContainer = new HBox();
        timeScaleContainer.setAlignment(Pos.CENTER);
        timeScaleContainer.setPrefSize(400.0D, 60.0D);
        timeScaleContainer.getChildren().addAll(timeScaleSelector);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        myBarChart = new BarChart<>(xAxis, yAxis);
        myBarChart.setTitle("Spending Category Chart");
        myBarChart.setBackground(AppState.chartBackground);
        myBarChart.setPrefSize(320.0D, 240.0D);
        myBarChart.setMinSize(320.0D, 240.0D);
        myBarChart.setMaxSize(320.0D, 240.0D);
        myBarChart.setAnimated(false);
        myBarChart.setHorizontalGridLinesVisible(true);
        myBarChart.setHorizontalZeroLineVisible(true);
        myBarChart.setVerticalGridLinesVisible(true);
        myBarChart.setVerticalZeroLineVisible(true);
        myBarChart.setLegendVisible(false);
        myBarChart.setOpacity(100.0D);
        myBarChart.setVisible(true);
        myBarChart.setBarGap(2);

        HBox chartBox = new HBox();
        chartBox.setAlignment(Pos.CENTER);
        chartBox.setPrefSize(400.0D, 240.0D);
        chartBox.getChildren().addAll(myBarChart);
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
        SpendCategory[] spendCategories= SpendCategory.values();
        for (SpendCategory spendingCat : spendCategories) {
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
                AppDataSaver.spendingArrayList.add(newSpending);
                setChartData();
                AppDataSaver.saveToJSON();
            } catch (InputMismatchException var22) {
                new ErrorMessage("Error Saving Expense Data!",
                        "Only numbers accepted for the amount input").show();
            } catch (NoSuchElementException var23) {
                new ErrorMessage("Error Saving Expense Data!",
                        "All fields must be filled").show();
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
            AppWindow.setWindowTitle("home");
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

        for(Spending spend: AppDataSaver.spendingArrayList){
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

        XYChart.Series<String, Number> myBarChartData = new XYChart.Series<>();
        myBarChartData.getData().add(new XYChart.Data<>("POCKET_SELF", POCKET_SELF));
        myBarChartData.getData().add(new XYChart.Data<>("TRANSPORT_SELF", TRANSPORT_SELF));
        myBarChartData.getData().add(new XYChart.Data<>("FEEDING_SELF", FEEDING_SELF));
        myBarChartData.getData().add(new XYChart.Data<>("CLOTHING_SELF", CLOTHING_SELF));
        myBarChartData.getData().add(new XYChart.Data<>("BUY_THINGS_SELF", BUY_THINGS_SELF));
        myBarChartData.getData().add(new XYChart.Data<>("DO_PROJECT_SELF", DO_PROJECT_SELF));
        myBarChartData.getData().add(new XYChart.Data<>("EDUCATION_SELF", EDUCATION_SELF));
        myBarChartData.getData().add(new XYChart.Data<>("ENTERTAINMENT_SELF", ENTERTAINMENT_SELF));
        myBarChartData.getData().add(new XYChart.Data<>("INVESTMENT_SELF", INVESTMENT_SELF));
        myBarChartData.getData().add(new XYChart.Data<>("ASSIST_FAMILY", ASSIST_FAMILY));
        myBarChartData.getData().add(new XYChart.Data<>("LEND_FAMILY", LEND_FAMILY));
        myBarChartData.getData().add(new XYChart.Data<>("ASSIST_FRIEND", ASSIST_FRIEND));
        myBarChartData.getData().add(new XYChart.Data<>("LEND_FRIEND", LEND_FRIEND));
        myBarChartData.getData().add(new XYChart.Data<>("HELP_STRANGER", HELP_STRANGER));
        myBarChartData.getData().add(new XYChart.Data<>("REPAY_LOAN", REPAY_LOAN));
        myBarChart.getData().add(myBarChartData);
    }
}
