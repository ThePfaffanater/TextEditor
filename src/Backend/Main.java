package Backend;

import Backend.Config.DefaultConfig;
import Backend.Config.ITextEditorConfig;
import GUI.RootGUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

    private final ITextEditorConfig CONFIG;
    private BorderPane root;
    private RootGUI mainGui;


    public Main() {
        CONFIG = new DefaultConfig();
    }


    public static void main(String[] args) {
        Main main = new Main();
        main.launch(args);
    }


    @Override
    public void init() {
        root = new BorderPane();
        mainGui = new RootGUI(CONFIG);
    }


    @Override
    public void start(Stage window) {
        root = mainGui.getRoot();
        window.setTitle("FileTab Editor");
        StackPane win = new StackPane();
        win.getChildren().add(root);
        window.setScene(new Scene(win, CONFIG.getInitialWidth(), CONFIG.getInitialHeight()));
        window.show();


        window.setOnCloseRequest(e -> {
            System.out.println(CONFIG.ANSI_BLUE + "Process finishing...");
            Platform.exit();
            System.exit(0);
        });
    }
}