package Backend;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static javafx.stage.FileChooser.ExtensionFilter;

public class FileOperations {

    private final Stage stage = new Stage();
    File lastFile = null;

    public ArrayList<File> openTextFiles(){
        final FileChooser fileChooser = new FileChooser();
        ExtensionFilter extFilter = new ExtensionFilter("Text Files (*.txt)", "*.txt", "*.html");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open .VB File(s)");
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        return new ArrayList<>(list);
    }

    public String readFile(File file) {
        StringBuilder output = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return output.toString();
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
            throw new FileNotFoundException("There is no file location to save to!!!");
        }
        PrintWriter fstream = new PrintWriter(saveTo);
        for(String line : content.split("[\\r\\n]+"))  {
            fstream.write(line + "\r\n");
        }
        fstream.close();
        System.out.println("Saved file to:" + saveTo.getAbsolutePath());
    }
}
