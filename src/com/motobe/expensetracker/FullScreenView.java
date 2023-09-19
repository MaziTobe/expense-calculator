package com.motobe.expensetracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class FullScreenView extends Scene {
    protected static TextArea earnedRegister;
    protected static TextArea spentRegister;

    public FullScreenView(BorderPane root) {
        super(root);
        MenuItem selectLog = new MenuItem("SELECT LOG TO SHOW");
        selectLog.setOnAction((event) -> {
            Stage popUpStage= new Stage();
            Label infoDisplay= new Label("Please select starting date and Interval to see list of transactions within that interval!");
            infoDisplay.setTextAlignment(TextAlignment.CENTER);
            infoDisplay.setWrapText(true);
            DatePicker datePicker= new DatePicker();
            Label timeIntervalTag= new Label("Record Interval: ");
            ComboBox timeIntervalSelector = new ComboBox();
            timeIntervalSelector.getItems().add("day");
            timeIntervalSelector.getItems().add("month");
            timeIntervalSelector.getItems().add("year");
            HBox timeIntervalBox= new HBox(timeIntervalTag,timeIntervalSelector);
            timeIntervalBox.setSpacing(5);
            timeIntervalBox.setAlignment(Pos.CENTER);
            Button submitSelection= new Button("submit");
            submitSelection.setOnAction(event1 -> {
                try{
                    String timeInterval = timeIntervalSelector.getSelectionModel().getSelectedItem().toString();
                    LocalDate localDate= datePicker.getValue();
                    int day = localDate.getDayOfMonth();
                    int month = localDate.getMonthValue();
                    int year = localDate.getYear();
                    logRecord(timeInterval,day,month,year);
                    popUpStage.close();
                }catch (Exception exc){
                    infoDisplay.setTextFill(new Color(1,0,0,1));
                    infoDisplay.setText("Select starting date and interval!!!");
                }
            });
            VBox container = new VBox();
            container.setAlignment(Pos.CENTER);
            container.setPrefSize(AppState.WIDTH*0.6,AppState.HEIGHT*0.3);
            container.setSpacing(10);
            container.setPadding(new Insets(5));
            container.getChildren().addAll(infoDisplay,datePicker,timeIntervalBox,submitSelection);
            Scene popUpScene= new Scene(container);
            popUpStage.setScene(popUpScene);
            popUpStage.setTitle("Record Selection");
            popUpStage.initOwner(AppWindow.mainStage);
            popUpStage.initStyle(StageStyle.UTILITY);
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            popUpStage.show();
        });
        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
        MenuItem backToMain = new MenuItem("GO TO MAIN VIEW");
        backToMain.setOnAction((event) -> AppWindow.mainStage.setScene(AppWindow.mainView));
        Menu menu = new Menu("LOG MENU");
        menu.getItems().addAll(selectLog, separatorMenuItem1, backToMain);
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

    protected void logRecord(String timeInterval,int day, int month, int year){
        boolean isDay, isMonth, isYear;
        if(timeInterval.equalsIgnoreCase("day")){
            earnedRegister.setText("Log of all incomes on "+day+"\\"+month+"\\"+year+"\n\n");
            for (Earning earning: AppData.loadedEarning){
                isDay= earning.getTransactionDay()==day;
                isMonth= earning.getTransactionMonth()==month;
                isYear= earning.getTransactionYear()==year;
               if(isDay&&isMonth&&isYear){
                   earnedRegister.appendText("ID: "+earning.getTransactionID()+"\n"
                           +"Amount: "+earning.getAmountEarned()+"\n"
                           +"Category: "+earning.getTransactionCategory()+"\n"
                           +"Description: "+earning.getTransactionDescription()+"\n"
                           +"Date: "+earning.getTransactionDay()+"\\"+earning.getTransactionMonth()
                           +"\\"+earning.getTransactionYear()+"\n\n");
               }
            }
            spentRegister.setText("Log of all expenses on "+day+"\\"+month+"\\"+year+"\n\n");
            for (Spending spend: AppData.loadedSpending){
                isDay= spend.getTransactionDay()==day;
                isMonth= spend.getTransactionMonth()==month;
                isYear= spend.getTransactionYear()==year;
                if(isDay&&isMonth&&isYear){
                    spentRegister.appendText("ID: "+spend.getTransactionID()+"\n"
                            +"Amount: "+spend.getAmountSpent()+"\n"
                            +"Category: "+spend.getTransactionCategory()+"\n"
                            +"Description: "+spend.getTransactionDescription()+"\n"
                            +"Date: "+spend.getTransactionDay()+"\\"+spend.getTransactionMonth()
                            +"\\"+spend.getTransactionYear()+"\n\n");
                }
            }
        }else if(timeInterval.equalsIgnoreCase("month")){
            earnedRegister.setText("Log of all incomes in "+month+"\\"+year+"\n\n");
            for (Earning earning: AppData.loadedEarning){
                isMonth= earning.getTransactionMonth()==month;
                isYear= earning.getTransactionYear()==year;
                if(isMonth&&isYear){
                    earnedRegister.appendText("ID: "+earning.getTransactionID()+"\n"
                            +"Amount: "+earning.getAmountEarned()+"\n"
                            +"Category: "+earning.getTransactionCategory()+"\n"
                            +"Description: "+earning.getTransactionDescription()+"\n"
                            +"Date: "+earning.getTransactionDay()+"\\"+earning.getTransactionMonth()
                            +"\\"+earning.getTransactionYear()+"\n\n");
                }
            }
            spentRegister.setText("Log of all expenses in "+month+"\\"+year+"\n\n");
            for (Spending spend: AppData.loadedSpending){
                isMonth= spend.getTransactionMonth()==month;
                isYear= spend.getTransactionYear()==year;
                if(isMonth&&isYear){
                    spentRegister.appendText("ID: "+spend.getTransactionID()+"\n"
                            +"Amount: "+spend.getAmountSpent()+"\n"
                            +"Category: "+spend.getTransactionCategory()+"\n"
                            +"Description: "+spend.getTransactionDescription()+"\n"
                            +"Date: "+spend.getTransactionDay()+"\\"+spend.getTransactionMonth()
                            +"\\"+spend.getTransactionYear()+"\n\n");
                }
            }
        }else if(timeInterval.equalsIgnoreCase("year")){
            earnedRegister.setText("Log of all incomes in "+year+"\n\n");
            for (Earning earning: AppData.loadedEarning){
                isYear= earning.getTransactionYear()==year;
                if(isYear){
                    earnedRegister.appendText("ID: "+earning.getTransactionID()+"\n"
                            +"Amount: "+earning.getAmountEarned()+"\n"
                            +"Category: "+earning.getTransactionCategory()+"\n"
                            +"Description: "+earning.getTransactionDescription()+"\n"
                            +"Date: "+earning.getTransactionDay()+"\\"+earning.getTransactionMonth()
                            +"\\"+earning.getTransactionYear()+"\n\n");
                }
            }
            spentRegister.setText("Log of all expenses in "+year+"\n\n");
            for (Spending spend: AppData.loadedSpending){
                isYear= spend.getTransactionYear()==year;
                if(isYear){
                    spentRegister.appendText("ID: "+spend.getTransactionID()+"\n"
                            +"Amount: "+spend.getAmountSpent()+"\n"
                            +"Category: "+spend.getTransactionCategory()+"\n"
                            +"Description: "+spend.getTransactionDescription()+"\n"
                            +"Date: "+spend.getTransactionDay()+"\\"+spend.getTransactionMonth()
                            +"\\"+spend.getTransactionYear()+"\n\n");
                }
            }
        }
    }
}