package GUI;

import Backend.FileOperations;
import Backend.FileSaveException;
import javafx.scene.control.TabPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileTabPane extends TabPane {

    public FileTabPane(){
        //hello0oo
    }

    /**
     * saves the current tab's file
     */
    public void saveCurrent(){
        System.out.println("Attempting save of " + this.getSelectionModel().getSelectedIndex() + ". ");
        try {
            getCurrent().save();
        } catch (FileSaveException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * saves the current tab as a new file
     */
    public void saveCurrentAs(){
        int i = this.getSelectionModel().getSelectedIndex();
        System.out.println("Attempting save of " + i + ". ");
        try {
            getCurrent().saveAs();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * creates a new empty tab
     */
    public void newEmptyTab(){
       createNewFileTab();
    }

    private void createNewFileTab(){
        TextFileTab newTextFile = new TextFileTab();
        this.getTabs().add(newTextFile);
    }

    /**
     * Creates a new tab by opening a previous file
     */
    public void newTabFromPreexistingFile(){
        try{
            FileOperations fileOperations = new FileOperations();
            ArrayList<File> files = fileOperations.openTextFiles();
            for (File file : files){
                createNewFileTab(file);
            }
        }catch (java.lang.NullPointerException e){
            System.out.println("Error attempting to Open new file! The file probably doesn't exist somehow?");
        }
    }

    private void createNewFileTab(File file){
        TextFileTab newTextFile = new TextFileTab(file);
        newTextFile.setTabTitle(file.getName());
        this.getTabs().add(newTextFile);
    }

    public void addNewTab(String name, File file){
        TextFileTab newTextFile = new TextFileTab(file);
        this.getTabs().add(newTextFile);
    }

    /**
     * @return if there is no tabs inside the tabPane
     */
    public boolean isEmpty(){
        return this.getTabs().isEmpty();
    }

    /**
     * Dont worry there is definitely no hacky code in here...
     * @return the current selected tab in the pane
     */
    private TextFileTab getCurrent(){
        return (TextFileTab) this.getSelectionModel().getSelectedItem();
    }

}
