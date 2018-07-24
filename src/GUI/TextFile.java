package GUI;

import Backend.FileOptions;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.IOException;

public class TextFile extends Tab {
    FileOptions fileOptions;
    private TextArea textSpace;
    private File lastVer; //last saved version of this text dcoument
    private boolean hasChangedSinceSave;

    public TextFile(){
        this(null);
    }

    public TextFile(File preexistingFile){
        fileOptions = new FileOptions();
        lastVer = preexistingFile;
        String text = "";

        if(lastVer != null)
            text = fileOptions.getStringFromTextFile(lastVer);//gets all the text from the preexisting file

        textSpace = new TextArea();
        this.setContent(textSpace);
        textSpace.setWrapText(false);
        textSpace.setText(text);
    }

    public void setTabName(String newName){
        this.setText(newName);
    }

    public String getTabName(){
        return this.getText();
    }

    public void save(){
        if(hasChangedSinceSave) saveAs();

        try {
            fileOptions.saveTextFile(lastVer.getAbsolutePath(), this.getDocText());
        } catch (IOException e) {
            System.out.println("Error attempting to save!");
            e.printStackTrace();
        }
    }

    public void saveAs(){

        FileOptions fileOptions = new FileOptions();

        try {

            String fileName = fileOptions.saveTextFileAs(this.getDocText()).getName();
            this.setTabName(fileName);
        } catch (IOException e) {
            System.out.println("Error attempting to save!");
            e.printStackTrace();
        }
        hasChangedSinceSave = false;
    }

    public void checkChanged(){

    }

    private String getDocText(){
        return textSpace.getText();
    }
}

