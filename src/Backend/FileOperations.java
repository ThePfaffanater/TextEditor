package Backend;

import Backend.Config.ITextEditorConfig;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static javafx.stage.FileChooser.ExtensionFilter;

public class FileOperations {

    private final ITextEditorConfig CONFIG;
    private final Stage stage;

    public FileOperations(ITextEditorConfig CONFIG){
        this.CONFIG = CONFIG;
        stage = new Stage();

    }

    public ArrayList<File> openTextFiles(){
        final FileChooser fileChooser = new FileChooser();
        ExtensionFilter extFilter = new ExtensionFilter("Text Files (*.txt)", "*.txt", "*.html");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open .VB File(s)");
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        return new ArrayList<>(list);
    }

    public String readFile(File file) {
        try {
            Scanner inputFile = new Scanner(file);

            String retrunVal = "";

            inputFile.useDelimiter(System.getProperty("line.separator"));
            while (inputFile.hasNext()) {
                 retrunVal += (inputFile.next());

            }

            return retrunVal;
        } catch (FileNotFoundException e) {
            System.out.println(CONFIG.getErrorColor() + "Can not read/find the file [" + file.getAbsolutePath() + "]specified." + CONFIG.ANSI_RESET);
            return"";//TODO: fix this so it just throws and does nothing
        }
    }

    /**
     * Will do the same as saveTextFile but will queuery the user
     * @param content the content file you are wishing ot save
     * @return a File object of the FileTab saved.
     */
    public File saveTextFileAs(String content) throws IOException {
        final FileChooser fileChooser = new FileChooser();
        File selectedDirectory;
        fileChooser.setTitle("Save As");
        ExtensionFilter extFilter = new ExtensionFilter("Text Files (*.txt)", new String[]{"*.txt", "*.html"});
        fileChooser.getExtensionFilters().add(extFilter);
        selectedDirectory = fileChooser.showSaveDialog(stage);

        saveTextFile(selectedDirectory, content);
        return selectedDirectory;
    }

    public void saveTextFile(File saveTo, String content) throws IOException {
        if(saveTo==null){
            throw new FileNotFoundException(CONFIG.getErrorColor() + "There is no file location to save to!!!" + CONFIG.ANSI_RESET);
        }
        PrintWriter fstream = new PrintWriter(saveTo);
        fstream.print(content);
        fstream.close();
        System.out.println(CONFIG.getNotificationColor() + "Saved file to:" + saveTo.getAbsolutePath() + CONFIG.ANSI_RESET);
    }
}
