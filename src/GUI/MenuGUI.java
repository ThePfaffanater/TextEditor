package GUI;

import Backend.FileOptions;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.util.ArrayList;

public class MenuGUI{

    MenuBar menuBar;
    JFXTabPane tabPane;
    ArrayList<TextDoc> textDocs;
    int newTabCounter;

    public MenuGUI(){
        menuBar = new MenuBar();
        newTabCounter = 0;
        textDocs = new ArrayList<TextDoc>();
        tabPane = new JFXTabPane();
    }

    public MenuBar getMenuBar(JFXTabPane tabPane1) {
        tabPane = tabPane1;
        menuBar.setUseSystemMenuBar(true);
        menuBar.getMenus().addAll(fileMenu(), viewMenu());

        return menuBar;
    }

    private Menu fileMenu(){
        //FileMenu
        Menu fileMenu = new Menu("File");
            MenuItem saveFile = new MenuItem("Save");
            saveFile.setOnAction(event -> {
                int i = tabPane.getSelectionModel().getSelectedIndex();
                System.out.println("Attempting save of " + i + ". ");
                textDocs.get(i).save();
            }); //Save current file

            MenuItem saveAsFile = new MenuItem("Save As...");
            saveAsFile.setOnAction(event -> {
            int i = tabPane.getSelectionModel().getSelectedIndex();
            System.out.println("Attempting save of " + i + ". ");
            textDocs.get(i).saveAs();
        }); //Save current file

            MenuItem newFile = new MenuItem("New"); //Create new file
            newFile.setOnAction(e -> {
                System.out.println("Making new empty text doc:");
                createNewFile();
                saveAsFile.setDisable(false);
            });

            MenuItem openFile = new MenuItem("Open");
                openFile.setOnAction(e -> {
                   System.out.println("Attempting to open file");
                    openNew();
                });
        //Disables
        if (textDocs.isEmpty()){
            saveFile.setDisable(true);
            saveAsFile.setDisable(true);
        }
        else {
            saveFile.setDisable(false);
            saveAsFile.setDisable(false);
        }
        fileMenu.getItems().addAll(newFile,openFile,saveFile,saveAsFile);
        return fileMenu;
    }

    private Menu viewMenu(){
        //ViewMenu
        Menu viewMenu = new Menu("View");
            viewMenu.getItems().add(new MenuItem("Search"));

        return  viewMenu;
    }

    //FUNCTIONS
    private void createNewFile(){
        newTabCounter++;
        TextDoc newTextDoc = new TextDoc("");
        newTextDoc.setTabName("New " + newTabCounter);
        tabPane.getTabs().add(newTextDoc);
        textDocs.add(newTextDoc);
    }

    private void createNewFile(File file){

        FileOptions fileOptions = new FileOptions();
        String words = fileOptions.getStringFromTextDoc(file);
        newTabCounter++;
        TextDoc newTextDoc = new TextDoc(words);
        newTextDoc.setTabName(file.getName());
        tabPane.getTabs().add(newTextDoc);
        textDocs.add(newTextDoc);
    }

    private void openNew(){
        try{
            FileOptions fileOptions = new FileOptions();
            ArrayList<File> files = fileOptions.openTextFiles();
            for (File file : files){
                createNewFile(file);
            }
        }catch (java.lang.NullPointerException e){
            System.out.println("Canceled Open!");
        }
    }
}
