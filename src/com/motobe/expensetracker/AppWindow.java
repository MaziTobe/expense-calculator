package com.motobe.expensetracker;

import java.io.File;
import java.text.SimpleDateFormat;
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

    private static int sessionStartTime;
    private static int sessionStopTime;

    public AppWindow() {
    }

    public void start(Stage primaryStage) {
        sessionStartTime = Integer.parseInt((new SimpleDateFormat("MMyyyy")).format(new Date()));

        AppData.loadFromJSON();

        mainStage = primaryStage;

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
            AppData.saveToJSON();
            sessionStopTime = Integer.parseInt((new SimpleDateFormat("MMyyyy")).format(new Date()));
        });
    }

    public void getStarted(String[] input) {
        launch(input);
    }

}