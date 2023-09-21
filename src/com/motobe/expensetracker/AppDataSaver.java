package com.motobe.expensetracker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class AppDataSaver {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static String jsonEarnFilePath = "";
    private static String jsonSpendFilePath = "";

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

    protected static void createAppDataFolder(){
        String pathToAppDataFolder="C:\\Program Files\\expense-calc-data";//+AppWindow.appName+"\\expense-calc-data";
        Path directory = Paths.get(pathToAppDataFolder);
        /**********************************************************************/
        System.out.println("folder does exist: "+Files.exists(directory));
        System.out.println("folder does not exist: "+Files.notExists(directory));
        /*****************************************************************************/
        if(!Files.exists(directory)){
            try {
                Files.createDirectories(directory);
                Files.createDirectory(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**********************************************************************/
        System.out.println("folder exists now: "+Files.exists(directory));
        /**********************************************************************/
        File saveDirectory = new File(String.valueOf(directory));
        jsonEarnFilePath= saveDirectory.getPath()+"\\earnFile.JSON";
        jsonSpendFilePath= saveDirectory.getPath()+"\\spendFile.JSON";
        setFolderAndFilesToReadOnlyAndHidden(saveDirectory);
        /*******************************************************/
        System.out.println("(expense-calc-data) name: "+ saveDirectory.getName());
        System.out.println("(expense-calc-data) exist now: "+ saveDirectory.exists());
        System.out.println("(expense-calc-data) is hidden: "+ saveDirectory.isHidden());
        System.out.println("(expense-calc-data) children: "+ Arrays.toString(saveDirectory.list()));
        /************************************************************/
        System.out.println("earnFile.JSON does exist: "+Files.exists(Paths.get(jsonEarnFilePath)));
        System.out.println("earnFile.JSON is executable: "+Files.isExecutable(Paths.get(jsonEarnFilePath)));
        try{System.out.println("earnFile.JSON is hidden: "+Files.isHidden(Paths.get(jsonEarnFilePath)));}catch(IOException e){}
        System.out.println("earnFile.JSON is readable: "+Files.isReadable(Paths.get(jsonEarnFilePath)));
        System.out.println("earnFile.JSON is writable: "+Files.isWritable(Paths.get(jsonEarnFilePath)));
        /************************************************************/
        System.out.println("spendFile.JSON does exist: "+Files.exists(Paths.get(jsonSpendFilePath)));
        System.out.println("spendFile.JSON is executable: "+Files.isExecutable(Paths.get(jsonSpendFilePath)));
        try{System.out.println("spendFile.JSON is hidden: "+Files.isHidden(Paths.get(jsonSpendFilePath)));}catch(IOException e){}
        System.out.println("spendFile.JSON is readable: "+Files.isReadable(Paths.get(jsonSpendFilePath)));
        System.out.println("spendFile.JSON is writable: "+Files.isWritable(Paths.get(jsonSpendFilePath)));
    }

    private static void setFolderAndFilesToReadOnlyAndHidden(File selectedFolder) {
        if (selectedFolder.isDirectory()) {
            try {
                // Set the folder as hidden (Windows)
                ProcessBuilder builderFolder = new ProcessBuilder(
                        "attrib", "+H", selectedFolder.getAbsolutePath());
                builderFolder.start().waitFor();

                // Set all files and sub-folders as read-only and hidden recursively
                File[] allFilesInFolder = selectedFolder.listFiles();
                if (allFilesInFolder != null) {
                    for (File fileInFolder : allFilesInFolder) {
                        // Set the file as read-only and hidden (Windows)
                        ProcessBuilder builderFile = new ProcessBuilder(
                                "attrib", "+R", "+H", fileInFolder.getAbsolutePath());
                        try {
                            builderFile.start().waitFor();
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
