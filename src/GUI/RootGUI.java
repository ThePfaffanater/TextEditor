package GUI;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class RootGUI {

    private BorderPane root;

    //List of Nodes
    FileTabPane tabPane;
    TextArea textSpace;
    MenuGUI menu;


    //Init
    public RootGUI(){
        root = new BorderPane();
        tabPane = new FileTabPane();
        textSpace = new TextArea();
        menu = new MenuGUI();
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

