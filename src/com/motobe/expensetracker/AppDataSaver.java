package com.motobe.expensetracker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AppDataSaver {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String jsonEarnFilePath = "expense-calc-data\\earnFile.JSON";
    private static final String jsonSpendFilePath = "expense-calc-data\\spendFile.JSON";

    protected static ArrayList<Earning> loadedEarning;
    protected static ArrayList<Spending> loadedSpending;

    public AppDataSaver( ) {

     }

    protected static void saveToJSON(){
        try{
            mapper.writeValue(new File(jsonEarnFilePath), loadedEarning);
        }catch(IOException e){
            new ErrorMessage("Error Saving Income Data!",
                    "Missing JSON file to log app data!!!\nSee details below:\n"+e+"\n\n").show();
        }
        try{
            mapper.writeValue(new File(jsonSpendFilePath), loadedSpending);
        }catch(IOException e){
            new ErrorMessage("Error Saving Expense Data!",
                    "Missing JSON file to log app data!!!\nSee details below:\n"+e+"\n\n").show();
        }
    }

    protected static void loadFromJSON(){
        try {
            loadedEarning = mapper.readValue(new File(jsonEarnFilePath),
                    new TypeReference<ArrayList<Earning>>(){});
            loadedSpending = mapper.readValue(new File(jsonSpendFilePath),
                    new TypeReference<ArrayList<Spending>>(){});
        }catch(IOException e){
            loadedEarning = new ArrayList<>();
            loadedSpending= new ArrayList<>();
        }
    }
}
