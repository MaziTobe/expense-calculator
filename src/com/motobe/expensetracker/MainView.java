package com.motobe.expensetracker;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MainView extends Scene {
    private static double totalEarning;
    private static double totalSpending;
    protected static PieChart myPieChart;
    private static Label totalEarnTag;
    private static Label totalSpendTag;
    private static boolean isFirstRun;

    public MainView(VBox root) {
        super(root);
        isFirstRun = true;
        ComboBox timeScaleSelector = new ComboBox();
        timeScaleSelector.getItems().add("Today's Transactions");
        timeScaleSelector.getItems().add("Transactions Month to Date");
        timeScaleSelector.getItems().add("Transactions Year to Date");
        timeScaleSelector.setPrefSize(240.0D, 30.0D);
        HBox timeScaleContainer = new HBox();
        timeScaleContainer.setAlignment(Pos.CENTER);
        timeScaleContainer.setPrefSize(400.0D, 60.0D);
        timeScaleContainer.getChildren().addAll(new Node[]{timeScaleSelector});
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
        chartBox.getChildren().addAll(new Node[]{myPieChart});
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
        createSpendButton.setOnMouseEntered((event) -> {
            createSpendButton.setFill(AppState.textColorHover);
        });
        createSpendButton.setOnMouseExited((event) -> {
            createSpendButton.setFill(AppState.textColorNormal);
        });
        createSpendButton.setOnMousePressed((event) -> {
            AppWindow.mainStage.setScene(AppWindow.spendingView);
        });
        HBox createSpendBox = new HBox();
        createSpendBox.setAlignment(Pos.CENTER);
        createSpendBox.setPrefSize(400.0D, 90.0D);
        createSpendBox.getChildren().addAll(new Node[]{createSpendButton});
        Text createEarnButton = new Text();
        createEarnButton.setText("CREATE EARNING");
        createEarnButton.setTextAlignment(TextAlignment.CENTER);
        createEarnButton.setFont(AppState.textFont);
        createEarnButton.setFill(AppState.textColorNormal);
        createEarnButton.setOnMouseEntered((event) -> {
            createEarnButton.setFill(AppState.textColorHover);
        });
        createEarnButton.setOnMouseExited((event) -> {
            createEarnButton.setFill(AppState.textColorNormal);
        });
        createEarnButton.setOnMousePressed((event) -> {
            AppWindow.mainStage.setScene(AppWindow.earningView);
        });
        HBox createEarnBox = new HBox();
        createEarnBox.setAlignment(Pos.CENTER);
        createEarnBox.setPrefSize(400.0D, 60.0D);
        createEarnBox.getChildren().addAll(new Node[]{createEarnButton});
        Text gotoLOGScreenButton = new Text();
        gotoLOGScreenButton.setText("TRANSACTION LOG");
        gotoLOGScreenButton.setTextAlignment(TextAlignment.CENTER);
        gotoLOGScreenButton.setFont(AppState.textFont);
        gotoLOGScreenButton.setFill(AppState.textColorNormal);
        gotoLOGScreenButton.setOnMouseEntered((event) -> {
            gotoLOGScreenButton.setFill(AppState.textColorHover);
        });
        gotoLOGScreenButton.setOnMouseExited((event) -> {
            gotoLOGScreenButton.setFill(AppState.textColorNormal);
        });
        gotoLOGScreenButton.setOnMousePressed((event) -> {
            AppWindow.mainStage.setScene(AppWindow.fullScreenView);
        });
        HBox gotoFullScreenBox = new HBox();
        gotoFullScreenBox.setAlignment(Pos.CENTER);
        gotoFullScreenBox.setPrefSize(400.0D, 90.0D);
        gotoFullScreenBox.getChildren().addAll(new Node[]{gotoLOGScreenButton});
        root.setPrefSize(400.0D, 600.0D);
        root.setBackground(AppState.appBackground);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(new Node[]{timeScaleContainer, chartBox, totalEarnTag, totalSpendTag, createSpendBox, createEarnBox, gotoFullScreenBox});
    }

    protected static void setChartData() {
        totalEarning = 0.0D;
        totalSpending = 0.0D;
        if (isFirstRun) {
            totalEarning = AppWindow.totalEarning;
            totalSpending = AppWindow.totalSpending;
            isFirstRun = false;
        } else {
            double allEarn = 0.0D;
            double allSpend = 0.0D;
            ArrayList<Earning> earningRecords = EarningView.totalEarning;

            Earning earned;
            for(Iterator var5 = earningRecords.iterator(); var5.hasNext(); allEarn += earned.getAmountEarned()) {
                earned = (Earning)var5.next();
            }

            ArrayList<Spending> spendingRecords = SpendingView.totalSpending;

            Spending spent;
            for(Iterator var10 = spendingRecords.iterator(); var10.hasNext(); allSpend += spent.getAmountSpent()) {
                spent = (Spending)var10.next();
            }

            totalEarning = allEarn + AppWindow.totalEarning;
            totalSpending = allSpend + AppWindow.totalSpending;
        }

        ObservableList<Data> myPieChartData = FXCollections.observableArrayList(new Data[]{new Data("Earned", totalEarning), new Data("Spent", totalSpending)});
        myPieChart.setData(myPieChartData);
        totalEarnTag.setText("\tEARNED: N" + totalEarning);
        totalSpendTag.setText("SPENT: N" + totalSpending + "\t");
    }
}
