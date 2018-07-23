package GUI;

import Backend.FileOptions;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class TextDoc extends Tab {
    private TextArea textSpace;
    private String tabName;

    private boolean saved = false;

    public TextDoc(String text){
        init();
        textSpace.setText(text);
    }

    private void init(){
        textSpace = new TextArea();
        tabName = "NewFile";
        this.setContent(textSpace);
        textSpace.setWrapText(false);
    }

    public void setTabName(String newName){
        this.tabName = newName;
        this.setText(tabName);
    }

    public void save(){
        if(!saved) saveAs();
        FileOptions fileOptions = new FileOptions();
        fileOptions.saveTextFileAs(this.getDocText());
    }

    public void saveAs(){

        FileOptions fileOptions = new FileOptions();

        try {
            fileOptions.saveTextFileAs(this.getDocText());
            String fileName = fileOptions.getLastFile().getName();
            this.setTabName(fileName);
        }catch (java.lang.NullPointerException e){
            System.out.println("Canceled Save!");
            return;
        }
        saved = true;
    }

    public String getDocText(){
        return textSpace.getText();
    }
}

