package GUI;

import Backend.FileOptions;
import javafx.scene.control.TabPane;

import java.io.File;
import java.util.ArrayList;

public class FileTabs extends TabPane {

    public void saveCurrent(){
        int i = tabPane.getSelectionModel().getSelectedIndex();
        System.out.println("Attempting save of " + i + ". ");
        textFiles.get(i).save();
    }

    public void saveCurrentAs(){
        int i = tabPane.getSelectionModel().getSelectedIndex();
        System.out.println("Attempting save of " + i + ". ");
        textFiles.get(i).saveAs();
    }

    public void newEmptyTab(){
        System.out.println("Making new empty text doc:");
        createNewFileTab();
    }

    private void createNewFileTab(){
        newTabCounter++;
        TextFile newTextFile = new TextFile();
        newTextFile.setTabName("Untitled " + newTabCounter);
        tabPane.getTabs().add(newTextFile);
        textFiles.add(newTextFile);
    }

    public void newTabFromPrexistingFile(){
        try{
            FileOptions fileOptions = new FileOptions();
            ArrayList<File> files = fileOptions.openTextFiles();
            for (File file : files){
                createNewFileTab(file);
            }
        }catch (java.lang.NullPointerException e){
            System.out.println("Error attempting to Open new file!");
        }
    }

    private void createNewFileTab(File file){

        FileOptions fileOptions = new FileOptions();
        newTabCounter++;
        TextFile newTextFile = new TextFile(file);
        newTextFile.setTabName(file.getName());
        tabPane.getTabs().add(newTextFile);
        textFiles.add(newTextFile);
    }

    public boolean isEmpty(){
        return textFiles.isEmpty();
    }


}
