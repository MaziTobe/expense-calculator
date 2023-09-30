package com.motobe.expensetracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


public class TransactionRecordView extends Scene {
    protected static TextArea earnedRegister;
    protected static TextArea spentRegister;
    private static boolean isEarnShowing;
    private VBox registerBox;
    private final Menu filterByCategory;
    private static Menu filterByDate;
    private static int dayInUse;
    private static int monthInUse;
    private static int yearInUse;
    private static String earnCatInUse;
    private static String spendCatInUse;
    private static List<Earning> earningListInUse;
    private static List<Spending> spendingListInUse;
    private static Label earnHeaderLabel;
    private static Label spendHeaderLabel;
    private static final double REG_WIDTH= AppState.WIDTH*0.94;
    private static final double REG_HEIGHT= AppState.HEIGHT*0.84;
    private static final double HEADER_WIDTH= AppState.WIDTH*0.94;
    private static final double HEADER_HEIGHT= AppState.HEIGHT*0.10;

    public TransactionRecordView(VBox root) {
        super(root);

        isEarnShowing=true;
        setFilterParameters(LocalDate.now().getDayOfMonth(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear(),"ALL","ALL",
                AppDataSaver.earningArrayList, AppDataSaver.spendingArrayList);

        MenuItem selectYearToLog = new MenuItem("select year to see record");
        selectYearToLog.setOnAction((event) -> {
            String instruction="Please choose a year to see list of transactions within that year!";
            new SelectionPopUp(false,instruction).show();
        });
        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
        MenuItem switchEarnSpend = new MenuItem("switch to expense record");
        switchEarnSpend.setOnAction((event) -> {
            if(isEarnShowing){
                registerBox.getChildren().removeAll(earnHeaderLabel,spendHeaderLabel,earnedRegister,spentRegister);
                registerBox.getChildren().addAll(spendHeaderLabel,spentRegister);
                isEarnShowing=false;
                switchEarnSpend.setText("switch to income record");
                logRecord();
            }else{
                registerBox.getChildren().removeAll(earnHeaderLabel,spendHeaderLabel,earnedRegister,spentRegister);
                registerBox.getChildren().addAll(earnHeaderLabel,earnedRegister);
                isEarnShowing=true;
                switchEarnSpend.setText("switch to expense record");
                logRecord();
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
            filterByDate.setDisable(false);
            setFilterParameters(0, 0, yearInUse,"ALL","ALL",
                    earningListInUse, spendingListInUse);
            logRecord();
        });
        Menu menu2 = new Menu("Record Filter");
        menu2.getItems().addAll(filterByDate,separatorMenuItem3,filterByCategory,separatorMenuItem4,
                goToSpecificDay, separatorMenuItem5,clearAllFilters);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu,menu2);

        earnHeaderLabel = new Label();
        earnHeaderLabel.setBackground(AppState.labelBackground);
        earnHeaderLabel.setFont(AppState.labelFont);
        earnHeaderLabel.setTextFill(AppState.labelTextFill);
        earnHeaderLabel.setAlignment(Pos.CENTER);
        earnHeaderLabel.setTextAlignment(TextAlignment.CENTER);
        earnHeaderLabel.setPrefSize(HEADER_WIDTH,HEADER_HEIGHT);
        spendHeaderLabel = new Label();
        spendHeaderLabel.setBackground(AppState.labelBackground);
        spendHeaderLabel.setFont(AppState.labelFont);
        spendHeaderLabel.setTextFill(AppState.labelTextFill);
        spendHeaderLabel.setAlignment(Pos.CENTER);
        spendHeaderLabel.setTextAlignment(TextAlignment.CENTER);
        spendHeaderLabel.setPrefSize(HEADER_WIDTH,HEADER_HEIGHT);

        earnedRegister = new TextArea();
        earnedRegister.setPrefSize(REG_WIDTH, REG_HEIGHT);
        earnedRegister.setEditable(false);
        earnedRegister.setWrapText(true);
        spentRegister = new TextArea();
        spentRegister.setPrefSize(REG_WIDTH, REG_HEIGHT);
        spentRegister.setEditable(false);
        spentRegister.setWrapText(true);

        registerBox= new VBox(earnHeaderLabel,earnedRegister);
        registerBox.setSpacing(AppState.HEIGHT*0.01);
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
                    int setDay=0;
                    int setMonth=0;
                    switch(myIntervalCat){
                        case "TODAY":
                            setDay=LocalDate.now().getDayOfMonth();
                            setMonth=LocalDate.now().getMonthValue();
                            setFilterParameters(setDay, setMonth, yearInUse,earnCatInUse,
                                    spendCatInUse, earningListInUse, spendingListInUse);
                            break;
                        case "THIS_MONTH":
                            setMonth=LocalDate.now().getMonthValue();
                            setFilterParameters(setDay, setMonth, yearInUse,earnCatInUse,
                                    spendCatInUse, earningListInUse, spendingListInUse);
                            break;
                        case "THIS_YEAR":
                            setFilterParameters(setDay, setMonth, yearInUse,earnCatInUse,
                                    spendCatInUse, earningListInUse, spendingListInUse);
                            break;
                        default:
                            break;
                    }
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
                            spendCatInUse, earningListInUse, spendingListInUse);
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
                            spendCatInUse, earningListInUse, spendingListInUse);
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
                            mySpendCat, earningListInUse, spendingListInUse);
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
                    earningListInUse, spendingListInUse);
        }else{
            filterByDate.setDisable(false);
            setFilterParameters(0, 0, selectedYear,"ALL","ALL",
                    earningListInUse, spendingListInUse);
        }
        setFilterByDateItems(selectedYear);
    }

    private static void setFilterParameters(int day,int month,int year, String earnCat,String spendCat,
                                             List<Earning> earnList, List<Spending> spendList){
        dayInUse = day; monthInUse = month; yearInUse = year;
        earnCatInUse = earnCat; spendCatInUse = spendCat;
        earningListInUse = earnList; spendingListInUse = spendList;
    }

    protected static void logRecord(){
        if(isEarnShowing){
            earnedRegister.setText("");
            if(monthInUse!=0){
                if(dayInUse!=0){
                    if(!earnCatInUse.equalsIgnoreCase("ALL")){
                        for(Earning myEarn: earningListInUse){
                            if(myEarn.getTransactionCategory().equalsIgnoreCase(earnCatInUse)
                                    &&myEarn.getTransactionDay()==dayInUse
                                    &&myEarn.getTransactionMonth()==monthInUse){
                                earnedRegister.appendText("ID: "+myEarn.getTransactionID()+"\n"
                                        +"Amount: "+myEarn.getAmountEarned()+"\n"
                                        +"Category: "+myEarn.getTransactionCategory()+"\n"
                                        +"Description: "+myEarn.getTransactionDescription()+"\n"
                                        +"Date: "+myEarn.getTransactionDay()+"\\"+myEarn.getTransactionMonth()
                                        +"\\"+myEarn.getTransactionYear()+"\n\n");
                            }
                        }
                    }else{
                        for(Earning myEarn: earningListInUse){
                            if(myEarn.getTransactionDay()==dayInUse
                                    &&myEarn.getTransactionMonth()==monthInUse){
                                earnedRegister.appendText("ID: "+myEarn.getTransactionID()+"\n"
                                        +"Amount: "+myEarn.getAmountEarned()+"\n"
                                        +"Category: "+myEarn.getTransactionCategory()+"\n"
                                        +"Description: "+myEarn.getTransactionDescription()+"\n"
                                        +"Date: "+myEarn.getTransactionDay()+"\\"+myEarn.getTransactionMonth()
                                        +"\\"+myEarn.getTransactionYear()+"\n\n");
                            }
                        }
                    }
                }else{
                    if(!earnCatInUse.equalsIgnoreCase("ALL")){
                        for(Earning myEarn: earningListInUse){
                            if(myEarn.getTransactionCategory().equalsIgnoreCase(earnCatInUse)
                                    &&myEarn.getTransactionMonth()==monthInUse){
                                earnedRegister.appendText("ID: "+myEarn.getTransactionID()+"\n"
                                        +"Amount: "+myEarn.getAmountEarned()+"\n"
                                        +"Category: "+myEarn.getTransactionCategory()+"\n"
                                        +"Description: "+myEarn.getTransactionDescription()+"\n"
                                        +"Date: "+myEarn.getTransactionDay()+"\\"+myEarn.getTransactionMonth()
                                        +"\\"+myEarn.getTransactionYear()+"\n\n");
                            }
                        }
                    }else{
                        for(Earning myEarn: earningListInUse){
                            if(myEarn.getTransactionMonth()==monthInUse){
                                earnedRegister.appendText("ID: "+myEarn.getTransactionID()+"\n"
                                        +"Amount: "+myEarn.getAmountEarned()+"\n"
                                        +"Category: "+myEarn.getTransactionCategory()+"\n"
                                        +"Description: "+myEarn.getTransactionDescription()+"\n"
                                        +"Date: "+myEarn.getTransactionDay()+"\\"+myEarn.getTransactionMonth()
                                        +"\\"+myEarn.getTransactionYear()+"\n\n");
                            }
                        }
                    }
                }
            }else{
                if(!earnCatInUse.equalsIgnoreCase("ALL")){
                    for(Earning myEarn: earningListInUse){
                        if(myEarn.getTransactionCategory().equalsIgnoreCase(earnCatInUse)){
                            earnedRegister.appendText("ID: "+myEarn.getTransactionID()+"\n"
                                    +"Amount: "+myEarn.getAmountEarned()+"\n"
                                    +"Category: "+myEarn.getTransactionCategory()+"\n"
                                    +"Description: "+myEarn.getTransactionDescription()+"\n"
                                    +"Date: "+myEarn.getTransactionDay()+"\\"+myEarn.getTransactionMonth()
                                    +"\\"+myEarn.getTransactionYear()+"\n\n");
                        }
                    }
                }else{
                    for(Earning myEarn: earningListInUse){
                        earnedRegister.appendText("ID: "+myEarn.getTransactionID()+"\n"
                                +"Amount: "+myEarn.getAmountEarned()+"\n"
                                +"Category: "+myEarn.getTransactionCategory()+"\n"
                                +"Description: "+myEarn.getTransactionDescription()+"\n"
                                +"Date: "+myEarn.getTransactionDay()+"\\"+myEarn.getTransactionMonth()
                                +"\\"+myEarn.getTransactionYear()+"\n\n");
                    }
                }
            }
            earnHeaderLabel.setText("Showing INCOME records for: "
                    +dayInUse+"/"+ monthInUse+"/"+yearInUse+"\n"
                    +"EarnCategory of: "+earnCatInUse);
        }else{
            spentRegister.setText("");
            if(monthInUse!=0){
                if(dayInUse!=0){
                    if(!spendCatInUse.equalsIgnoreCase("ALL")){
                        for(Spending mySpend: spendingListInUse){
                            if(mySpend.getTransactionCategory().equalsIgnoreCase(spendCatInUse)
                                    &&mySpend.getTransactionDay()==dayInUse
                                    &&mySpend.getTransactionMonth()==monthInUse){
                                spentRegister.appendText("ID: "+mySpend.getTransactionID()+"\n"
                                        +"Amount: "+mySpend.getAmountSpent()+"\n"
                                        +"Category: "+mySpend.getTransactionCategory()+"\n"
                                        +"Description: "+mySpend.getTransactionDescription()+"\n"
                                        +"Date: "+mySpend.getTransactionDay()+"\\"+mySpend.getTransactionMonth()
                                        +"\\"+mySpend.getTransactionYear()+"\n\n");
                            }
                        }
                    }else{
                        for(Spending mySpend: spendingListInUse){
                            if(mySpend.getTransactionDay()==dayInUse
                                    &&mySpend.getTransactionMonth()==monthInUse){
                                spentRegister.appendText("ID: "+mySpend.getTransactionID()+"\n"
                                        +"Amount: "+mySpend.getAmountSpent()+"\n"
                                        +"Category: "+mySpend.getTransactionCategory()+"\n"
                                        +"Description: "+mySpend.getTransactionDescription()+"\n"
                                        +"Date: "+mySpend.getTransactionDay()+"\\"+mySpend.getTransactionMonth()
                                        +"\\"+mySpend.getTransactionYear()+"\n\n");
                            }
                        }
                    }
                }else{
                    if(!spendCatInUse.equalsIgnoreCase("ALL")){
                        for(Spending mySpend: spendingListInUse){
                            if(mySpend.getTransactionCategory().equalsIgnoreCase(spendCatInUse)
                                    &&mySpend.getTransactionMonth()==monthInUse){
                                spentRegister.appendText("ID: "+mySpend.getTransactionID()+"\n"
                                        +"Amount: "+mySpend.getAmountSpent()+"\n"
                                        +"Category: "+mySpend.getTransactionCategory()+"\n"
                                        +"Description: "+mySpend.getTransactionDescription()+"\n"
                                        +"Date: "+mySpend.getTransactionDay()+"\\"+mySpend.getTransactionMonth()
                                        +"\\"+mySpend.getTransactionYear()+"\n\n");
                            }
                        }
                    }else{
                        for(Spending mySpend: spendingListInUse){
                            if(mySpend.getTransactionMonth()==monthInUse){
                                spentRegister.appendText("ID: "+mySpend.getTransactionID()+"\n"
                                        +"Amount: "+mySpend.getAmountSpent()+"\n"
                                        +"Category: "+mySpend.getTransactionCategory()+"\n"
                                        +"Description: "+mySpend.getTransactionDescription()+"\n"
                                        +"Date: "+mySpend.getTransactionDay()+"\\"+mySpend.getTransactionMonth()
                                        +"\\"+mySpend.getTransactionYear()+"\n\n");
                            }
                        }
                    }
                }
            }else{
                if(!spendCatInUse.equalsIgnoreCase("ALL")){
                    for(Spending mySpend: spendingListInUse){
                        if(mySpend.getTransactionCategory().equalsIgnoreCase(spendCatInUse)){
                            spentRegister.appendText("ID: "+mySpend.getTransactionID()+"\n"
                                    +"Amount: "+mySpend.getAmountSpent()+"\n"
                                    +"Category: "+mySpend.getTransactionCategory()+"\n"
                                    +"Description: "+mySpend.getTransactionDescription()+"\n"
                                    +"Date: "+mySpend.getTransactionDay()+"\\"+mySpend.getTransactionMonth()
                                    +"\\"+mySpend.getTransactionYear()+"\n\n");
                        }
                    }
                }else{
                    for(Spending mySpend: spendingListInUse){
                        spentRegister.appendText("ID: "+mySpend.getTransactionID()+"\n"
                                +"Amount: "+mySpend.getAmountSpent()+"\n"
                                +"Category: "+mySpend.getTransactionCategory()+"\n"
                                +"Description: "+mySpend.getTransactionDescription()+"\n"
                                +"Date: "+mySpend.getTransactionDay()+"\\"+mySpend.getTransactionMonth()
                                +"\\"+mySpend.getTransactionYear()+"\n\n");
                    }
                }
            }
            spendHeaderLabel.setText("Showing EXPENSE records for: "
                    +dayInUse+"/"+ monthInUse+"/"+yearInUse+"\n"
                    +"SpendCategory of: "+spendCatInUse);
        }
    }
}