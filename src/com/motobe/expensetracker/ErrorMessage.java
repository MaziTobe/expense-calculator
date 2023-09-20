package com.motobe.expensetracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ErrorMessage extends Stage {
    public ErrorMessage(String errorTitle, String errorInfo){
        TextArea infoDisplay= new TextArea(errorInfo);
        infoDisplay.setPrefSize(AppState.WIDTH*0.6,AppState.HEIGHT*0.3);
        infoDisplay.setEditable(false);
        infoDisplay.setWrapText(true);
        VBox container = new VBox(infoDisplay);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));
        Scene popUpScene= new Scene(container);
        setScene(popUpScene);
        setTitle(errorTitle);
        initOwner(AppWindow.mainStage);
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setX(AppWindow.mainStage.getX()+AppState.WIDTH*0.2);
        setY(AppWindow.mainStage.getY()+AppState.HEIGHT*0.3);
        toFront();
    }
}
