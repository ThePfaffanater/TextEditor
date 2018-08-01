package GUI;

import Backend.Config.DefaultConfig;
import Backend.Config.ITextEditorConfig;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

    BorderPane root;
    RootGUI mainGui;

    public ITextEditorConfig config;


    public Main() {
        config = new DefaultConfig();
    }


    public static void main(String[] args) {
        Main main = new Main();
        main.launch(args);
    }


    @Override
    public void init() throws Exception {
        root = new BorderPane();
        mainGui = new RootGUI();
    }


    @Override
    public void start(Stage window) {
        root = mainGui.getRoot();
        window.setTitle("FileTab Editor");
        StackPane win = new StackPane();
        win.getChildren().add(root);
        window.setScene(new Scene(win, config.getInitialWidth(), config.getInitialHeight()));
        window.show();


        window.setOnCloseRequest(e -> {
            System.out.println(config.ANSI_BLUE + "Process finishing...");
            Platform.exit();
            System.exit(0);
        });
    }
}