package com.motobe.expensetracker;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.Month;


public class TransactionRecordView extends Scene {
    protected static TextArea earnedRegister;
    protected static TextArea spentRegister;
    private boolean isEarnShowing;
    private VBox registerBox;
    private Menu filterByCategory;
    private static Menu filterByDate;

    public TransactionRecordView(VBox root) {
        super(root);
        isEarnShowing=true;

        MenuItem selectYearToLog = new MenuItem("select year to see record");
        selectYearToLog.setOnAction((event) -> {
            String instruction="Please choose a year to see list of transactions within that year!";
            new SelectionPopUp(false,instruction).show();
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
            }
            setFilterByCategoryItems();
        });
        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();
        MenuItem backToMain = new MenuItem("back to main view");
        backToMain.setOnAction((event) -> {
            AppWindow.setWindowTitle("home");
            AppWindow.mainStage.setScene(AppWindow.mainView);
        });
        Menu menu = new Menu("Record Menu");
        menu.getItems().addAll(selectYearToLog, separatorMenuItem1, switchEarnSpend, separatorMenuItem2, backToMain);

        filterByDate= new Menu("Filter By Date");
        setFilterByDateItems(2030);//LocalDate.now().getYear());
        SeparatorMenuItem separatorMenuItem3 = new SeparatorMenuItem();
        filterByCategory= new Menu("Filter By Category");
        setFilterByCategoryItems();
        SeparatorMenuItem separatorMenuItem4 = new SeparatorMenuItem();
        MenuItem goToSpecificDay= new MenuItem("Go to Specific Day");
        goToSpecificDay.setOnAction(event -> {
            String instruction="Please select date to see transaction on that day!";
            new SelectionPopUp(true,instruction).show();
        });
        Menu menu2 = new Menu("Record Filter");
        menu2.getItems().addAll(filterByDate,separatorMenuItem3,filterByCategory,separatorMenuItem4,goToSpecificDay);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu,menu2);

        earnedRegister = new TextArea();
        earnedRegister.setText("<Log of all Earning>\n\n");
        earnedRegister.setPrefSize(AppState.WIDTH*0.94, AppState.HEIGHT*0.94);
        earnedRegister.setEditable(false);
        earnedRegister.setWrapText(true);
        spentRegister = new TextArea();
        spentRegister.setText("<Log of all Spending>\n\n");
        spentRegister.setPrefSize(AppState.WIDTH*0.94, AppState.HEIGHT*0.94);
        spentRegister.setEditable(false);
        spentRegister.setWrapText(true);

        registerBox= new VBox(earnedRegister);
        registerBox.setPadding(new Insets(AppState.WIDTH*0.03));

        root.setPrefSize(AppState.WIDTH, AppState.HEIGHT);
        root.setBackground(AppState.appBackground);
        root.getChildren().addAll(menuBar,registerBox);
    }

    private static boolean isCurrentYear(int selectedYear){
        return selectedYear==LocalDate.now().getYear();
    }

    private static void setFilterByDateItems(int selectedYear){
        if(isCurrentYear(selectedYear)){
            filterByDate.getItems().clear();
            IntervalCategory[] intervalCategories= IntervalCategory.values();
            for(IntervalCategory interval: intervalCategories){
                MenuItem intervalCatItem= new MenuItem(String.valueOf(interval));
                filterByDate.getItems().add(intervalCatItem);
                intervalCatItem.setOnAction(event -> {
                    System.out.println(interval +" was selected");
                });
            }
        }else{
            filterByDate.getItems().clear();
            Month[] months = Month.values();
            for (Month month : months) {
                MenuItem dateCatItem= new MenuItem(String.valueOf(month));
                filterByDate.getItems().add(dateCatItem);
                dateCatItem.setOnAction(event -> {
                    System.out.println(month +" was selected");
                });
            }
        }
    }

    private void setFilterByCategoryItems(){
        if(isEarnShowing){
            filterByCategory.getItems().clear();
            EarnCategory[] earnCategories= EarnCategory.values();
            for(EarnCategory earning: earnCategories){
                MenuItem earnCatItem= new MenuItem(String.valueOf(earning));
                filterByCategory.getItems().add(earnCatItem);
                earnCatItem.setOnAction(event -> {
                    System.out.println(earning +" was selected");
                });
            }
        }else{
            filterByCategory.getItems().clear();
            SpendCategory[] spendCategories= SpendCategory.values();
            for(SpendCategory spending: spendCategories){
                MenuItem spendCatItem= new MenuItem(String.valueOf(spending));
                filterByCategory.getItems().add(spendCatItem);
                spendCatItem.setOnAction(event -> {
                    System.out.println(spending +" was selected");
                });
            }
        }
    }

    protected static void loadSelectedYearFromFile(int selectedYear,int day, int month, int year){
        System.out.println("Supposed to load new JSON File containing\n " +
                "income and expenses for the selected year: "+selectedYear+"\n" +
                "Then assign them to a new dynamically created ArrayList\n\n");

        if(day!=0&&month!=0&&year!=0){
            System.out.println("Then go ahead and filter the ArrayList to contain only\n " +
                    "income and expenses for the specific day\\month\\year: "+day+"\\"+month+"\\"+year+"\n");
            filterByDate.setDisable(true);
        }else{
            filterByDate.setDisable(false);
        }
        setFilterByDateItems(selectedYear);
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