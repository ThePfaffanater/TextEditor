package GUI;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class RootGUI {

    private BorderPane root;

    //List of Nodes
    FileTabPane tabPane;
    TextArea textSpace;
    TextEditorMenuBar menuBar;


    //Init
    public RootGUI(){
        root = new BorderPane();
        tabPane = new FileTabPane();
        textSpace = new TextArea();
        menuBar = new TextEditorMenuBar(tabPane);
    }

    public BorderPane getRoot() {
        root.setCenter(tabPane);
        root.setTop(menuBar);
        return root;
    }


}

