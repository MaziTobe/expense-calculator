package com.motobe.expensetracker;

import java.io.File;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppWindow extends Application {
    protected static Stage mainStage;
    protected static String appName;

    protected static MainView mainView;
    protected static EarningView earningView;
    protected static SpendingView spendingView;
    protected static TransactionRecordView transactionRecordView;

    public void start(Stage primaryStage) {
        appName="My Expense Tracker";

        AppDataSaver.createAppDataFolder();
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
        mainStage.setOnCloseRequest((event) -> AppDataSaver.saveToJSON());
    }

    public void getStarted(String[] input) {
        launch(input);
    }

    protected static void setWindowTitle(String suffixText){
        mainStage.setTitle(appName+" - "+suffixText);
    }

}