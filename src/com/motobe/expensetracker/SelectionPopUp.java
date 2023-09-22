package com.motobe.expensetracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class SelectionPopUp extends Stage {
    public SelectionPopUp(boolean showSpecificDate,String instruction){
        Label infoDisplay= new Label(instruction);
        infoDisplay.setTextAlignment(TextAlignment.CENTER);
        infoDisplay.setWrapText(true);

        DatePicker datePicker= new DatePicker();

        TextField yearInputFiled= new TextField();
        yearInputFiled.setAlignment(Pos.CENTER);

        Button submitSelection= new Button("submit");
        if(showSpecificDate){
            submitSelection.setOnAction(event1 -> {
                try{
                    LocalDate localDate= datePicker.getValue();
                    int day = localDate.getDayOfMonth();
                    int month = localDate.getMonthValue();
                    int year = localDate.getYear();
                    TransactionRecordView.loadSelectedYearFromFile(year,day,month,year);
                    close();
                }catch (Exception exc){
                    infoDisplay.setTextFill(new Color(1,0,0,1));
                    infoDisplay.setText(String.valueOf(exc));
                }
            });
        }else{
            submitSelection.setOnAction(event1 -> {
                try{
                    int selectedYear= Integer.parseInt(yearInputFiled.getText());
                    TransactionRecordView.loadSelectedYearFromFile(selectedYear,0,0,0);
                    close();
                }catch (Exception exc){
                    infoDisplay.setTextFill(new Color(1,0,0,1));
                    infoDisplay.setText(String.valueOf(exc));
                }
            });
        }

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPrefSize(AppState.WIDTH*0.6,AppState.HEIGHT*0.3);
        container.setSpacing(10);
        container.setPadding(new Insets(5));

        if(showSpecificDate){
            container.getChildren().addAll(infoDisplay,datePicker,submitSelection);
        }else{
            container.getChildren().addAll(infoDisplay,yearInputFiled,submitSelection);
        }

        Scene popUpScene= new Scene(container);
        setScene(popUpScene);
        setTitle("Record Selection");
        initOwner(AppWindow.mainStage);
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setX(AppWindow.mainStage.getX()+AppState.WIDTH*0.2);
        setY(AppWindow.mainStage.getY()+AppState.HEIGHT*0.3);
        toFront();
        show();
    }
}
