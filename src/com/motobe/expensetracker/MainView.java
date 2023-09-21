package com.motobe.expensetracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;

public class MainView extends Scene {
    protected static PieChart myPieChart;
    private static Label totalEarnTag;
    private static Label totalSpendTag;
    private static ComboBox timeScaleSelector;

    public MainView(VBox root) {
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
        myPieChart = new PieChart();
        myPieChart.setTitle("Total Earned and Spent Chart");
        myPieChart.setBackground(AppState.chartBackground);
        myPieChart.setPrefSize(320.0D, 240.0D);
        myPieChart.setMinSize(320.0D, 240.0D);
        myPieChart.setMaxSize(320.0D, 240.0D);
        myPieChart.setVisible(true);
        myPieChart.setLegendVisible(true);
        myPieChart.setLegendSide(Side.LEFT);
        myPieChart.setLabelLineLength(2.0D);
        myPieChart.setLabelsVisible(false);
        HBox chartBox = new HBox();
        chartBox.setAlignment(Pos.CENTER);
        chartBox.setPrefSize(400.0D, 240.0D);
        chartBox.getChildren().addAll(myPieChart);
        totalEarnTag = new Label();
        totalEarnTag.setBackground(AppState.labelBackground);
        totalEarnTag.setFont(AppState.labelFont);
        totalEarnTag.setTextFill(AppState.labelTextFill);
        totalEarnTag.setAlignment(Pos.CENTER_LEFT);
        totalEarnTag.setPrefSize(320.0D, 30.0D);
        totalSpendTag = new Label();
        totalSpendTag.setBackground(AppState.labelBackground);
        totalSpendTag.setFont(AppState.labelFont);
        totalSpendTag.setTextFill(AppState.labelTextFill);
        totalSpendTag.setAlignment(Pos.CENTER_RIGHT);
        totalSpendTag.setPrefSize(320.0D, 30.0D);
        setChartData();
        Text createSpendButton = new Text();
        createSpendButton.setText("CREATE SPENDING");
        createSpendButton.setTextAlignment(TextAlignment.CENTER);
        createSpendButton.setFont(AppState.textFont);
        createSpendButton.setFill(AppState.textColorNormal);
        createSpendButton.setOnMouseEntered((event) -> createSpendButton.setFill(AppState.textColorHover));
        createSpendButton.setOnMouseExited((event) -> createSpendButton.setFill(AppState.textColorNormal));
        createSpendButton.setOnMousePressed((event) -> {
            AppWindow.setWindowTitle("expense input view");
            AppWindow.mainStage.setScene(AppWindow.spendingView);
        });
        HBox createSpendBox = new HBox();
        createSpendBox.setAlignment(Pos.CENTER);
        createSpendBox.setPrefSize(400.0D, 90.0D);
        createSpendBox.getChildren().addAll(createSpendButton);
        Text createEarnButton = new Text();
        createEarnButton.setText("CREATE EARNING");
        createEarnButton.setTextAlignment(TextAlignment.CENTER);
        createEarnButton.setFont(AppState.textFont);
        createEarnButton.setFill(AppState.textColorNormal);
        createEarnButton.setOnMouseEntered((event) -> createEarnButton.setFill(AppState.textColorHover));
        createEarnButton.setOnMouseExited((event) -> createEarnButton.setFill(AppState.textColorNormal));
        createEarnButton.setOnMousePressed((event) -> {
            AppWindow.setWindowTitle("income input view");
            AppWindow.mainStage.setScene(AppWindow.earningView);
        });
        HBox createEarnBox = new HBox();
        createEarnBox.setAlignment(Pos.CENTER);
        createEarnBox.setPrefSize(400.0D, 60.0D);
        createEarnBox.getChildren().addAll(createEarnButton);
        Text gotoLOGScreenButton = new Text();
        gotoLOGScreenButton.setText("TRANSACTION LOG");
        gotoLOGScreenButton.setTextAlignment(TextAlignment.CENTER);
        gotoLOGScreenButton.setFont(AppState.textFont);
        gotoLOGScreenButton.setFill(AppState.textColorNormal);
        gotoLOGScreenButton.setOnMouseEntered((event) -> gotoLOGScreenButton.setFill(AppState.textColorHover));
        gotoLOGScreenButton.setOnMouseExited((event) -> gotoLOGScreenButton.setFill(AppState.textColorNormal));
        gotoLOGScreenButton.setOnMousePressed((event) -> {
            AppWindow.setWindowTitle("transaction records");
            TransactionRecordView.logRecord("day", LocalDate.now().getDayOfMonth(),
                    LocalDate.now().getMonthValue(),LocalDate.now().getYear());
            AppWindow.mainStage.setScene(AppWindow.transactionRecordView);
        });
        HBox gotoFullScreenBox = new HBox();
        gotoFullScreenBox.setAlignment(Pos.CENTER);
        gotoFullScreenBox.setPrefSize(400.0D, 90.0D);
        gotoFullScreenBox.getChildren().addAll(gotoLOGScreenButton);
        root.setPrefSize(400.0D, 600.0D);
        root.setBackground(AppState.appBackground);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(timeScaleContainer, chartBox, totalEarnTag,
                totalSpendTag, createSpendBox, createEarnBox, gotoFullScreenBox);
    }

    protected static void setChartData(){
        double totalEarning = 0.0D;
        double totalSpending = 0.0D;

        String timeInterval= timeScaleSelector.getSelectionModel().getSelectedItem().toString();

        if(timeInterval.equalsIgnoreCase("TODAY")){
            for (Earning earning: AppDataSaver.loadedEarning){
                boolean isDay= earning.getTransactionDay()==LocalDate.now().getDayOfMonth();
                boolean isMonth= earning.getTransactionMonth()==LocalDate.now().getMonthValue();
                boolean isYear= earning.getTransactionYear()==LocalDate.now().getYear();
                if(isDay&&isMonth&&isYear){
                    totalEarning = totalEarning + earning.getAmountEarned();
                }
            }
            for (Spending spending: AppDataSaver.loadedSpending){
                boolean isDay= spending.getTransactionDay()==LocalDate.now().getDayOfMonth();
                boolean isMonth= spending.getTransactionMonth()==LocalDate.now().getMonthValue();
                boolean isYear= spending.getTransactionYear()==LocalDate.now().getYear();
                if(isDay&&isMonth&&isYear){
                    totalSpending = totalSpending + spending.getAmountSpent();
                }
            }
        }else if(timeInterval.equalsIgnoreCase("THIS_MONTH")){
            for (Earning earning: AppDataSaver.loadedEarning){
                boolean isMonth= earning.getTransactionMonth()==LocalDate.now().getMonthValue();
                boolean isYear= earning.getTransactionYear()==LocalDate.now().getYear();
                if(isMonth&&isYear){
                    totalEarning = totalEarning + earning.getAmountEarned();
                }
            }
            for (Spending spending: AppDataSaver.loadedSpending){
                boolean isMonth= spending.getTransactionMonth()==LocalDate.now().getMonthValue();
                boolean isYear= spending.getTransactionYear()==LocalDate.now().getYear();
                if(isMonth&&isYear){
                    totalSpending = totalSpending + spending.getAmountSpent();
                }
            }
        }else if(timeInterval.equalsIgnoreCase("THIS_YEAR")){
            for (Earning earning: AppDataSaver.loadedEarning){
                boolean isYear= earning.getTransactionYear()==LocalDate.now().getYear();
                if(isYear){
                    totalEarning = totalEarning + earning.getAmountEarned();
                }
            }
            for (Spending spending: AppDataSaver.loadedSpending){
                boolean isYear= spending.getTransactionYear()==LocalDate.now().getYear();
                if(isYear){
                    totalSpending = totalSpending + spending.getAmountSpent();
                }
            }
        }

        ObservableList<Data> myPieChartData = FXCollections.observableArrayList(
                new Data("Earned", totalEarning), new Data("Spent", totalSpending));
        myPieChart.setData(myPieChartData);
        totalEarnTag.setText("\tEARNED: N" + totalEarning);
        totalSpendTag.setText("SPENT: N" + totalSpending + "\t");
    }
}
