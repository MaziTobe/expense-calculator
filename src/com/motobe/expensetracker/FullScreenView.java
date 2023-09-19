package com.motobe.expensetracker;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class FullScreenView extends Scene {
    protected static TextArea earnedRegister;
    protected static TextArea spentRegister;

    public FullScreenView(BorderPane root) {
        super(root);
        MenuItem selectLog = new MenuItem("SELECT LOG TO SHOW");
        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
        MenuItem backToMain = new MenuItem("GO TO MAIN VIEW");
        backToMain.setOnAction((event) -> {
            AppWindow.mainStage.setScene(AppWindow.mainView);
        });
        Menu menu = new Menu("LOG MENU");
        menu.getItems().addAll(new MenuItem[]{selectLog, separatorMenuItem1, backToMain});
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        earnedRegister = new TextArea();
        earnedRegister.setText("<Log of all Earning>\n\n");
        earnedRegister.setPrefSize(400.0D, 600.0D);
        earnedRegister.setEditable(false);
        earnedRegister.setWrapText(true);
        spentRegister = new TextArea();
        spentRegister.setText("<Log of all Spending>\n\n");
        spentRegister.setPrefSize(400.0D, 600.0D);
        spentRegister.setEditable(false);
        spentRegister.setWrapText(true);
        root.setPrefSize(800.0D, 600.0D);
        root.setBackground(AppState.appBackground);
        root.setTop(menuBar);
        root.setCenter(earnedRegister);
        root.setRight(spentRegister);
    }
}