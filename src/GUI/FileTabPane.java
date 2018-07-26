package GUI;

import Backend.FileOptions;
import javafx.beans.binding.Bindings;
import javafx.scene.control.TabPane;

import java.io.File;
import java.util.ArrayList;

public class FileTabPane extends TabPane {
    int newTabCounter;

    public FileTabPane(){
        newTabCounter = 0;
    }

    public void saveCurrent(){
        System.out.println("Attempting save of " + this.getSelectionModel().getSelectedIndex() + ". ");
        getCurrent().save();
    }

    public void saveCurrentAs(){
        int i = this.getSelectionModel().getSelectedIndex();
        System.out.println("Attempting save of " + i + ". ");
        getCurrent().saveAs();
    }

    public void newEmptyTab(){
        System.out.println("Making new empty text doc:");
        createNewFileTab();
    }

    private void createNewFileTab(){
        newTabCounter++;
        TextFileTab newTextFile = new TextFileTab();
        newTextFile.setTabName("Untitled " + newTabCounter);
        this.getTabs().add(newTextFile);
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
        TextFileTab newTextFile = new TextFileTab(file);
        newTextFile.setTabName(file.getName());
        this.getTabs().add(newTextFile);
    }

    public boolean isEmpty(){
        return this.getTabs().isEmpty();
    }

    private TextFileTab getCurrent(){
        return (TextFileTab) this.getSelectionModel().getSelectedItem();
    }

}
