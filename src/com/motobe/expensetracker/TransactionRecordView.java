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

public class TransactionRecordView extends Scene {
    protected static TextArea earnedRegister;
    protected static TextArea spentRegister;
    private boolean isEarnShowing;
    private VBox registerBox;

    public TransactionRecordView(VBox root) {
        super(root);
        isEarnShowing=true;
        MenuItem selectDateToLog = new MenuItem("select record to show");
        selectDateToLog.setOnAction((event) -> {
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
            popUpStage.setX(AppWindow.mainStage.getX()+AppState.WIDTH*0.2);
            popUpStage.setY(AppWindow.mainStage.getY()+AppState.HEIGHT*0.3);
            popUpStage.toFront();
            popUpStage.show();
        });
        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
        MenuItem switchEarnSpend = new MenuItem("switch to expense record");
        switchEarnSpend.setOnAction((event) -> {
            if(isEarnShowing){
                registerBox.getChildren().removeAll(earnedRegister,spentRegister);
                registerBox.getChildren().add(spentRegister);
                isEarnShowing=false;
                switchEarnSpend.setText("switch to income record");
            }else{
                registerBox.getChildren().removeAll(earnedRegister,spentRegister);
                registerBox.getChildren().add(earnedRegister);
                isEarnShowing=true;
                switchEarnSpend.setText("switch to expense record");
            };
        });
        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();
        MenuItem backToMain = new MenuItem("back to main view");
        backToMain.setOnAction((event) -> {
            AppWindow.setWindowTitle("home");
            AppWindow.mainStage.setScene(AppWindow.mainView);
        });
        Menu menu = new Menu("RECORD MENU");
        menu.getItems().addAll(selectDateToLog, separatorMenuItem1, switchEarnSpend, separatorMenuItem2, backToMain);
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

        registerBox= new VBox(earnedRegister);

        root.setPrefSize(400.0D, 600.0D);
        root.setBackground(AppState.appBackground);
        root.getChildren().addAll(menuBar,registerBox);

    }

    protected static void logRecord(String timeInterval,int day, int month, int year){
        boolean isDay, isMonth, isYear;
        if(timeInterval.equalsIgnoreCase("day")){
            earnedRegister.setText("Log of all incomes on "+day+"\\"+month+"\\"+year+"\n\n");
            for (Earning earning: AppDataSaver.loadedEarning){
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
            for (Spending spend: AppDataSaver.loadedSpending){
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
            for (Earning earning: AppDataSaver.loadedEarning){
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
            for (Spending spend: AppDataSaver.loadedSpending){
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
            for (Earning earning: AppDataSaver.loadedEarning){
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
            for (Spending spend: AppDataSaver.loadedSpending){
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