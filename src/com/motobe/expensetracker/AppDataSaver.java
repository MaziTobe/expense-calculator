package com.motobe.expensetracker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppDataSaver {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static String jsonEarnFilePath = "";
    private static String jsonSpendFilePath = "";

    protected static ArrayList<Earning> loadedEarning;
    protected static ArrayList<Spending> loadedSpending;

    private static File saveDirectory;

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

    protected static List<Earning> loadEarnFromJSON(int selectedYear) throws IOException{
        String currentYear= String.valueOf(selectedYear);
        String jsonEarnFilePathToLoad= saveDirectory.getPath()+"\\earnFile"+currentYear+".JSON";
        return mapper.readValue(new File(jsonEarnFilePathToLoad),
                     new TypeReference<ArrayList<Earning>>(){});
    }

    protected static List<Spending> loadSpendFromJSON(int selectedYear) throws IOException{
        String currentYear= String.valueOf(selectedYear);
        String jsonSpendFilePathToLoad= saveDirectory.getPath()+"\\spendFile"+currentYear+".JSON";
        return mapper.readValue(new File(jsonSpendFilePathToLoad),
         new TypeReference<ArrayList<Spending>>(){});
    }

    protected static void createAppDataFolder(){
        String pathToAppDataFolder= System.getProperty("user.home")+"\\"+AppWindow.appName+"\\expense-calc-data";
        Path directory = Paths.get(pathToAppDataFolder);
        saveDirectory = new File(String.valueOf(directory));
        if(!Files.exists(directory)){
            try {
                Files.createDirectories(directory);
                System.out.println("expense-cal-data folder was created!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String currentYear= String.valueOf(LocalDate.now().getYear());
        jsonEarnFilePath= saveDirectory.getPath()+"\\earnFile"+currentYear+".JSON";
        jsonSpendFilePath= saveDirectory.getPath()+"\\spendFile"+currentYear+".JSON";
        if(!Files.exists(Paths.get(jsonEarnFilePath))){
            try {
                Files.createFile(Paths.get(jsonEarnFilePath));
                System.out.println("earnFile.JSON file was created!");
            }catch (IOException exc){
                exc.printStackTrace();
            }
        }
        if(!Files.exists(Paths.get(jsonSpendFilePath))){
            try {
                Files.createFile(Paths.get(jsonSpendFilePath));
                System.out.println("spendFile.JSON file was created!");
            }catch (IOException exc){
                exc.printStackTrace();
            }
        }
        setFoldersAndFilesToReadWriteHidden(new File(saveDirectory.getParent()));
    }

    private static void setFoldersAndFilesToReadWriteHidden(File selectedFolder) {
        if (selectedFolder.isDirectory()) {
            try {
                // Set the folder as hidden (Windows)
                ProcessBuilder builderFolder = new ProcessBuilder(
                        "attrib", "-R", "+H", selectedFolder.getAbsolutePath());
                builderFolder.start().waitFor();

                // Set all files and sub-folders as read-only and hidden recursively
                File[] allFilesInFolder = selectedFolder.listFiles();
                if (allFilesInFolder != null) {
                    for (File fileInFolder : allFilesInFolder) {
                        // Set the file as read-only and hidden (Windows)
                        ProcessBuilder builderFile = new ProcessBuilder(
                                "attrib", "-R", "+H", fileInFolder.getAbsolutePath());
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
