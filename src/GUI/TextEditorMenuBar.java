package GUI;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TextEditorMenuBar extends MenuBar {

    FileTabPane tabPane;

    /**
     * Is a file bar that provides options
     *  based of the context of the
     * @param tabPane is required to provide context and to allow the MenuBar to interact with the current file
     */
    public TextEditorMenuBar(FileTabPane tabPane){
        this.tabPane = tabPane;
        this.getMenus().addAll(fileMenu(), viewMenu());
    }

    private Menu fileMenu(){ //TextEditorMenuBar
        Menu fileMenu = new Menu("File");
        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(event -> {
            tabPane.saveCurrent();
        });

        MenuItem saveAsFile = new MenuItem("Save As...");
        saveAsFile.setOnAction(event -> {
            tabPane.saveCurrentAs();
        }); //Save current file

        MenuItem newFile = new MenuItem("New"); //Create new file
        newFile.setOnAction(e -> {
            tabPane.newEmptyTab();
            saveAsFile.setDisable(false);

        });

        MenuItem openFile = new MenuItem("Open");
        openFile.setOnAction(e -> {
            System.out.println("Attempting to open file");
            tabPane.newTabFromPreexistingFile();
        });
        //Disables
        if (tabPane.isEmpty()){
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

    private Menu viewMenu(){ //ViewMenu

        Menu viewMenu = new Menu("View");
        viewMenu.getItems().add(new MenuItem("Search"));

        return  viewMenu;
    }

}
