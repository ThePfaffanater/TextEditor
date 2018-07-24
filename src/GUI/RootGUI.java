package GUI;

import Backend.FileOptions;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RootGUI {

    private BorderPane root;

    //List of Nodes
    JFXTabPane tabPane;
    TextArea textSpace;
    MenuGUI menu;


    //Init
    public RootGUI(){
        root = new BorderPane();
        tabPane = new JFXTabPane();
        textSpace = new TextArea();
        menu = new MenuGUI();
        newTabCounter = 0;
        textFiles = new ArrayList<TextFile>();
    }

    public BorderPane getRoot() {
        root.setCenter(tabPane);
        root.setTop(menu.getMenuBar(tabPane));
        return root;
    }

    public void Update(){

    }

    //FUNCTIONS

    public void saveCurrent(){

    }
}

