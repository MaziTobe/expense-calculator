package com.motobe.expensetracker;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


public class TransactionRecordView extends Scene {
    protected static TextArea earnedRegister;
    protected static TextArea spentRegister;
    private boolean isEarnShowing;
    private VBox registerBox;
    private final Menu filterByCategory;
    private static Menu filterByDate;
    private static int dayInUse;
    private static int monthInUse;
    private static int yearInUse;
    private static String earnCatInUse;
    private static String spendCatInUse;
    private static String intervalInUse;
    private static List<Earning> earningListInUse;
    private static List<Spending> spendingListInUse;

    public TransactionRecordView(VBox root) {
        super(root);
        isEarnShowing=true;
        setFilterParameters(LocalDate.now().getDayOfMonth(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear(),"ALL","ALL",
                "ALL_YEAR",AppDataSaver.loadedEarning, AppDataSaver.loadedSpending);

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
        SeparatorMenuItem separatorMenuItem5 = new SeparatorMenuItem();
        MenuItem clearAllFilters= new MenuItem("Clear All Filters");
        clearAllFilters.setOnAction(event -> {
            setFilterParameters(0, 0, yearInUse,"ALL","ALL",
                    "ALL_YEAR",earningListInUse, spendingListInUse);
            logRecord();
        });
        Menu menu2 = new Menu("Record Filter");
        menu2.getItems().addAll(filterByDate,separatorMenuItem3,filterByCategory,separatorMenuItem4,
                goToSpecificDay, separatorMenuItem5,clearAllFilters);

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
                    String myIntervalCat= String.valueOf(interval);
                    setFilterParameters(dayInUse, monthInUse, yearInUse,earnCatInUse,
                            spendCatInUse, myIntervalCat,earningListInUse, spendingListInUse);
                    logRecord();
                });
            }
        }else{
            filterByDate.getItems().clear();
            Month[] months = Month.values();
            for (Month month : months) {
                MenuItem dateCatItem= new MenuItem(String.valueOf(month));
                filterByDate.getItems().add(dateCatItem);
                dateCatItem.setOnAction(event -> {
                   int myMonth= month.getValue();
                    setFilterParameters(dayInUse, myMonth, yearInUse,earnCatInUse,
                            spendCatInUse, "ALL_IN_SELECTED_MONTH",earningListInUse, spendingListInUse);
                    logRecord();
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
                    String myEarnCat= String.valueOf(earning);
                    setFilterParameters(dayInUse, monthInUse, yearInUse,myEarnCat,
                            spendCatInUse, intervalInUse,earningListInUse, spendingListInUse);
                    logRecord();
                });
            }
        }else{
            filterByCategory.getItems().clear();
            SpendCategory[] spendCategories= SpendCategory.values();
            for(SpendCategory spending: spendCategories){
                MenuItem spendCatItem= new MenuItem(String.valueOf(spending));
                filterByCategory.getItems().add(spendCatItem);
                spendCatItem.setOnAction(event -> {
                    String mySpendCat= String.valueOf(spending);
                    setFilterParameters(dayInUse, monthInUse, yearInUse,earnCatInUse,
                            mySpendCat, intervalInUse,earningListInUse, spendingListInUse);
                    logRecord();
                });
            }
        }
    }

    protected static void loadSelectedYearFromFile(int selectedYear,int month, int day){
        if(selectedYear != yearInUse){
            try{
                earningListInUse= AppDataSaver.loadEarnFromJSON(selectedYear);
                spendingListInUse= AppDataSaver.loadSpendFromJSON(selectedYear);
            }catch(Exception exc){
                new ErrorMessage("Record Fetching Error","Unable to " +
                         "find and/or load the record for "+selectedYear+"!!!").show();
            }
        }

        if(day!=0&&month!=0){
            filterByDate.setDisable(true);
            setFilterParameters(day, month, selectedYear,"ALL","ALL",
                    "NONE_SELECTED",earningListInUse, spendingListInUse);
        }else{
            filterByDate.setDisable(false);
            setFilterParameters(0, 0, selectedYear,"ALL","ALL",
                    "ALL_YEAR",earningListInUse, spendingListInUse);
        }
        setFilterByDateItems(selectedYear);
    }

    private static void setFilterParameters(int day,int month,int year, String earnCat,String spendCat,
                                            String interval, List<Earning> earnList, List<Spending> spendList){
        dayInUse = day; monthInUse = month; yearInUse = year;
        earnCatInUse = earnCat; spendCatInUse = spendCat; intervalInUse = interval;
        earningListInUse = earnList; spendingListInUse = spendList;
    }

    protected static void logRecord(){
        earnedRegister.setText("Showing INCOME records for: \n" +
                "Day: "+ dayInUse+",___ Month: "+ monthInUse+",___ Year: "+yearInUse+"\n" +
                "Interval set to: "+intervalInUse+"\n"+
                "EarnCategory of: "+earnCatInUse+"\n\n");

        spentRegister.setText("Showing EXPENSE records for: \n" +
                "Day: "+ dayInUse+",___ Month: "+ monthInUse+",___ Year: "+yearInUse+"\n" +
                "Interval set to: "+intervalInUse+"\n"+
                "SpendCategory of: "+spendCatInUse+"\n\n");
    }

    /**protected static void logRecord(){
        boolean isDay, isMonth, isYear;
        if(timeInterval.equalsIgnoreCase("day")){
            earnedRegister.setText("Log of all incomes on "+day+"\\"+month+"\\"+year+"\n\n");
            for (Earning earning: earningListInUse){
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
            for (Spending spend: spendingListInUse){
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
            for (Earning earning: earningListInUse){
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
            for (Spending spend: spendingListInUse){
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
            for (Earning earning: earningListInUse){
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
            for (Spending spend: spendingListInUse){
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
    }**/
}