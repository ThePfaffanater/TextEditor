package GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class Main extends Application {

    BorderPane root;
    RootGUI mainGui;

    int width  = 650;
    int height = 450;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void init() throws Exception {
        root = new BorderPane();
        mainGui = new RootGUI();
    }


    @Override
    public void start(Stage window) {
        runtime();
        window.setTitle("TextFile Editor");
        StackPane win = new StackPane();
        win.getChildren().add(root);
        window.setScene(new Scene(win, width, height));
        window.show();
        window.setOnCloseRequest(e -> Platform.exit()); //Exit Commands
    }


    void runtime(){
        root = mainGui.getRoot();
    }
}