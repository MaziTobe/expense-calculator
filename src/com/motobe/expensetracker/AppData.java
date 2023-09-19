package com.motobe.expensetracker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AppData {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String jsonEarnFilePath = "expense-calc-data\\earnFile.JSON";
    private static final String jsonSpendFilePath = "expense-calc-data\\spendFile.JSON";

    protected static ArrayList<Earning> loadedEarning;
    protected static ArrayList<Spending> loadedSpending;

    public AppData( ) {

     }

    protected static void saveToJSON(){
        try{
            mapper.writeValue(new File(jsonEarnFilePath), loadedEarning);
        }catch(IOException e){
            new Alert(Alert.AlertType.ERROR,
                    "found no JSON file to log earnings!!!\nSee details below:\n"+e+"\n\n").show();
        }

        try{
            mapper.writeValue(new File(jsonSpendFilePath), loadedSpending);
        }catch(IOException e){
            new Alert(Alert.AlertType.ERROR,
                    "found no JSON file to log spendings!!!\nSee details below:\n"+e+"\n\n").show();
        }
    }

    protected static void loadFromJSON(){
        try {
            loadedEarning = mapper.readValue(new File(jsonEarnFilePath),
                    new TypeReference<ArrayList<Earning>>(){});
        }catch(IOException e){
            loadedEarning = new ArrayList<>();
            new Alert(Alert.AlertType.ERROR,
                    "found no record of previous earnings log!!!\nSee details below:\n"+e+"\n\n").show();
        }

        try {
            loadedSpending = mapper.readValue(new File(jsonSpendFilePath),
                    new TypeReference<ArrayList<Spending>>(){});
        }catch(IOException e){
            loadedSpending= new ArrayList<>();
            new Alert(Alert.AlertType.ERROR,
                    "found no record of previous spendings log!!!\nSee details below:\n"+e+"\n\n").show();
        }

    }
}
