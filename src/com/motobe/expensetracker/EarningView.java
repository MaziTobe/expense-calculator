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
import javafx.scene.chart.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EarningView extends Scene {
    private static ComboBox timeScaleSelector;
    private BarChart<String, Number> myBarChart;

    public EarningView(VBox root) {
        super(root);
        timeScaleSelector = new ComboBox();
        IntervalCategory[] intervalCategories= IntervalCategory.values();
        for (IntervalCategory intervalCat : intervalCategories) {
            timeScaleSelector.getItems().add(intervalCat);
        }
        timeScaleSelector.getSelectionModel().selectFirst();
        timeScaleSelector.setOnAction(event -> setChartData());
        timeScaleSelector.setPrefSize(240.0D, 30.0D);
        HBox timeScaleContainer = new HBox();
        timeScaleContainer.setAlignment(Pos.CENTER);
        timeScaleContainer.setPrefSize(400.0D, 60.0D);
        timeScaleContainer.getChildren().addAll(timeScaleSelector);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        myBarChart = new BarChart<>(xAxis, yAxis);
        myBarChart.setTitle("Earning Category Chart");
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
        EarnCategory[] var10 = EarnCategory.values();
        for (EarnCategory earningCat : var10) {
            categorySelector.getItems().add(earningCat);
        }
        categorySelector.setPrefSize(200.0D, 30.0D);
        HBox categoryBox = new HBox();
        categoryBox.setAlignment(Pos.CENTER);
        categoryBox.setPrefSize(400.0D, 60.0D);
        categoryBox.getChildren().addAll(categoryTag, categorySelector);
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
        descriptionBox.getChildren().addAll(descriptionTag, descriptionInput);
        Button submit = new Button("SUBMIT");
        submit.setFont(AppState.buttonFont);
        submit.setTextFill(AppState.buttonTextFill);
        submit.setBackground(AppState.earnButtonBackground);
        submit.setPrefSize(80.0D, 30.0D);
        submit.setOnAction((event) -> {
            try {
                Earning newEarning = new Earning();
                newEarning.setAmountEarned(new Scanner(amountInput.getText()).nextDouble());
                newEarning.setTransactionDescription(new Scanner(descriptionInput.getText()).nextLine());
                newEarning.setTransactionCategory(categorySelector.getSelectionModel().getSelectedItem().toString());
                newEarning.setTransactionID(new SimpleDateFormat("ssmmHHddMMyyyy").format(new Date()));
                newEarning.setTransactionDay(Integer.parseInt((new SimpleDateFormat("dd")).format(new Date())));
                newEarning.setTransactionMonth(Integer.parseInt((new SimpleDateFormat("MM")).format(new Date())));
                newEarning.setTransactionYear(Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date())));
                AppDataSaver.loadedEarning.add(newEarning);
                setChartData();
                AppDataSaver.saveToJSON();
            } catch (InputMismatchException exc) {
                new ErrorMessage("Error Saving Income Data!",
                        "Only numbers accepted for the amount input").show();
            } catch (NoSuchElementException exc) {
                new ErrorMessage("Error Saving Income Data!",
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
        double SALARY = 0.0D;
        double SALARY_BONUS = 0.0D;
        double GIFT_TO_ME = 0.0D;
        double INVESTMENT_GAINS = 0.0D;
        double PROFIT_FROM_TRADES = 0.0D;
        double BORROWED = 0.0D;

        for(Earning earn: AppDataSaver.loadedEarning){
            switch (earn.getTransactionCategory()) {
                case "SALARY":
                    SALARY += earn.getAmountEarned();
                    break;
                case "SALARY_BONUS":
                    SALARY_BONUS += earn.getAmountEarned();
                    break;
                case "GIFT_TO_ME":
                    GIFT_TO_ME += earn.getAmountEarned();
                    break;
                case "INVESTMENT_GAINS":
                    INVESTMENT_GAINS += earn.getAmountEarned();
                    break;
                case "PROFIT_FROM_TRADES":
                    PROFIT_FROM_TRADES += earn.getAmountEarned();
                    break;
                case "BORROWED":
                    BORROWED += earn.getAmountEarned();
                    break;
            }
        }

        XYChart.Series<String, Number> myBarChartData = new XYChart.Series<>();
        myBarChartData.getData().add(new XYChart.Data<>("SALARY", SALARY));
        myBarChartData.getData().add(new XYChart.Data<>("SALARY_BONUS", SALARY_BONUS));
        myBarChartData.getData().add(new XYChart.Data<>("GIFT_TO_ME", GIFT_TO_ME));
        myBarChartData.getData().add(new XYChart.Data<>("INVESTMENT_GAINS", INVESTMENT_GAINS));
        myBarChartData.getData().add(new XYChart.Data<>("PROFIT_FROM_TRADES", PROFIT_FROM_TRADES));
        myBarChartData.getData().add(new XYChart.Data<>("BORROWED", BORROWED));
        myBarChart.getData().add(myBarChartData);
    }
}