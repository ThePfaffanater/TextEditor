package GUI;

import Backend.Config.ITextEditorConfig;
import Backend.FileOperations;
import javafx.scene.control.TabPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileTabPane extends TabPane {

    private final ITextEditorConfig CONFIG;

    /**
     * Contains multiple open text files in tabs
     * with options to interact with the currently viewed file.
     */
    FileTabPane(final ITextEditorConfig CONFIG){
        this.CONFIG = CONFIG;
    }

    /**
     * @return if there is no tabs inside the tabPane
     */
    public boolean isEmpty(){
        return this.getTabs().isEmpty();
    }

    /**
     * saves the current tab's file
     */
    public void saveCurrent(){
       try {
            getCurrent().save();
        } catch (FileNotFoundException e) {
            System.out.println("Performing a SaveAs instead:");
            saveCurrentAs();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * saves the current tab as a new file
     */
    void saveCurrentAs(){
        int i = this.getSelectionModel().getSelectedIndex();
        try {
            getCurrent().saveAs();
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * creates a new empty tab
     */
    void newEmptyTab(){
        FileTab newTextFile = new FileTab(CONFIG);
        this.getTabs().add(newTextFile);
    }

    /**
     * Creates a new tab by opening a previous file
     */
    void newTabFromPreexistingFile(){
        try{
            FileOperations fileOperations = new FileOperations();
            ArrayList<File> files = fileOperations.openTextFiles(); //list of files chosen to open
            for (File file : files){
                FileTab newTextFile = new FileTab(file,CONFIG);
                this.getTabs().add(newTextFile);
            }
        }catch (java.lang.NullPointerException e){
            System.out.println("Error attempting to Open new file! The file probably doesn't exist somehow?");
        }
    }

    /**
     * Dont worry there is definitely no hacky code in here...
     * @return the current selected tab in the pane
     */
    public FileTab getCurrent(){
        return (FileTab) this.getSelectionModel().getSelectedItem();
    }

}
