package GUI;

import Backend.Config.ITextEditorConfig;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class RootGUI {

    private final BorderPane root;

    //List of Nodes
    private final FileTabPane tabPane;
    private final TextEditorMenuBar menuBar;


    //Init
    public RootGUI(final ITextEditorConfig CONFIG){
        root = new BorderPane();
        tabPane = new FileTabPane(CONFIG);
        TextArea textSpace = new TextArea();
        menuBar = new TextEditorMenuBar(tabPane);
    }

    public BorderPane getRoot() {
        root.setCenter(tabPane);
        root.setTop(menuBar);
        return root;
    }


}

