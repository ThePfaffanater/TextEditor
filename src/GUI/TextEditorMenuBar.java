package GUI;

import Backend.ChangeDetect;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TextEditorMenuBar extends MenuBar {

    FileTabPane tabPane;
    ChangeDetect changeDetect;

    /**
     * Is a file bar that provides options
     *  based of the context of the
     * @param tabPane is required to provide context and to allow the MenuBar to interact with the current file
     */
    public TextEditorMenuBar(FileTabPane tabPane){
        this.tabPane = tabPane;
        this.getMenus().addAll(fileMenu(), viewMenu());

        changeDetect = new ChangeDetect(tabPane);
        changeDetect.start();
    }

    private Menu fileMenu(){ //TextEditorMenuBar
        Menu fileMenu = new Menu("File");
        MenuItem saveFile = new MenuItem("Save"); //Save current file
        saveFile.setOnAction(event -> {
            tabPane.saveCurrent();
        });

        MenuItem saveAsFile = new MenuItem("Save As..."); //Save current file as a new one
        saveAsFile.setOnAction(event -> {
            tabPane.saveCurrentAs();
        }); //Save current file

        MenuItem newFile = new MenuItem("New");  //Create new file
        newFile.setOnAction(e -> {
            tabPane.newEmptyTab();
            saveAsFile.setDisable(false);
            saveFile.setDisable(false);

        });

        MenuItem openFile = new MenuItem("Open");//Open old file
        openFile.setOnAction(e -> {
            System.out.println("Attempting to open file");
            tabPane.newTabFromPreexistingFile();
        });

        (new Thread() {
            public void run() {
                while (true) {
                    //Disables
                    if (tabPane.isEmpty()) {
                        saveFile.setDisable(true);
                        saveAsFile.setDisable(true);
                    } else {
                        saveAsFile.setDisable(false);

                        if (changeDetect.isChanged()) {//if the current version differs from it's last save allow save
                            saveFile.setDisable(false);
                        }else{
                            saveFile.setDisable(true); //If the current version does not differ from it's last save only allow saveAs
                        }
                    }
                    try {
                        this.sleep(250);
                    } catch (InterruptedException e) {
                        System.out.println("Sleep interrupt!!!");
                    }
                }
            }
        }).start();

        fileMenu.getItems().addAll(newFile,openFile,saveFile,saveAsFile);
        return fileMenu;
    }

    private Menu viewMenu(){ //ViewMenu
        Menu viewMenu = new Menu("View");
        viewMenu.getItems().add(new MenuItem("Search"));//search is currently non-functional

        return  viewMenu;
    }

}
