package GUI;

import Backend.TabHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuGUI{

    MenuBar menuBar;
    TabHandler tabHandler;

    public MenuGUI(){
        menuBar = new MenuBar();

    }

    public MenuBar getMenuBar(TabHandler tabHandler) {
        this.tabHandler = tabHandler;
        menuBar.setUseSystemMenuBar(true);
        menuBar.getMenus().addAll(fileMenu(), viewMenu());

        return menuBar;
    }

    private Menu fileMenu(){
        //FileMenu
        Menu fileMenu = new Menu("File");
            MenuItem saveFile = new MenuItem("Save");
            saveFile.setOnAction(event -> {
                tabHandler.saveCurrent();
            });

            MenuItem saveAsFile = new MenuItem("Save As...");
            saveAsFile.setOnAction(event -> {
                tabHandler.saveCurrentAs();
        }); //Save current file

            MenuItem newFile = new MenuItem("New"); //Create new file
            newFile.setOnAction(e -> {
                tabHandler.newEmptyTab();
                saveAsFile.setDisable(false);

            });

            MenuItem openFile = new MenuItem("Open");
                openFile.setOnAction(e -> {
                   System.out.println("Attempting to open file");
                    tabHandler.newTabFromPrexistingFile();
                });
        //Disables
        if (textFiles.isEmpty()){
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



}
