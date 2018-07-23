package GUI;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RootGUI {

    private BorderPane root;
    private List<TextDoc> textDocs;

    //List of Nodes
    JFXTabPane tabPane;
    TextArea textSpace;
    MenuGUI menu;

    //Init
    public RootGUI(){

        this.textDocs = new ArrayList<TextDoc>();
        this.root = new BorderPane();
        this.tabPane = new JFXTabPane();
        this.textSpace = new TextArea();
        this.menu = new MenuGUI();
    }

    public BorderPane getRoot() {
        update();
        return root;
    }

    private void update(){
        menuBar();
        tabPane();

    }

    private void tabPane(){
        System.out.println("help me");
        root.setCenter(tabPane);
    }

    private  void menuBar(){
        root.setTop(menu.getMenuBar(tabPane));
    }
}

