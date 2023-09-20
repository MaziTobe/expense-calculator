package com.motobe.expensetracker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppWindow extends Application {
    protected static Stage mainStage;

    protected static MainView mainView;
    protected static EarningView earningView;
    protected static SpendingView spendingView;
    protected static TransactionRecordView transactionRecordView;

    private static int sessionStartTime;
    private static int sessionStopTime;

    public AppWindow() {
    }

    public void start(Stage primaryStage) {
        sessionStartTime = Integer.parseInt((new SimpleDateFormat("MMyyyy")).format(new Date()));

        AppDataSaver.loadFromJSON();

        mainStage = primaryStage;

        mainView = new MainView(new VBox());
        earningView = new EarningView(new VBox());
        spendingView = new SpendingView(new VBox());
        transactionRecordView = new TransactionRecordView(new VBox());

        setWindowTitle("home");
        mainStage.setScene(mainView);
        mainStage.sizeToScene();
        mainStage.setResizable(true);
        mainStage.getIcons().add(new Image((new File("app-res\\icon-exp-calc.png")).toURI().toString()));
        mainStage.show();
        mainStage.setOnCloseRequest((event) -> {
            AppDataSaver.saveToJSON();
            sessionStopTime = Integer.parseInt((new SimpleDateFormat("MMyyyy")).format(new Date()));
        });
    }

    public void getStarted(String[] input) {
        launch(input);
    }

    protected static void setWindowTitle(String suffixText){
        mainStage.setTitle("My Expense Tracker - "+suffixText);
    }

}