package Backend;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOptions {

    Stage stage = new Stage();
    File lastFile = null;

    public ArrayList<File> openTextFiles(){
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", new String[]{"*.txt"});
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open .VB File(s)");
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        ArrayList<File> arrayList = new ArrayList<>(list);
        return arrayList;
    }

    public String getStringFromTextFile(File file){
        String words;
        try {
            return  words = new Scanner(file).useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error Reading File!!");
        }
        return "";
    }

    /**
     * Will do the same as saveTextFile but will queuery the user
     * @param content the content file you are wishing ot save
     * @return a File object of the TextFileTab saved.
     */
    public File saveTextFileAs(String content) throws IOException {
        final FileChooser fileChooser = new FileChooser();
        File selectedDirectory;
        fileChooser.setTitle("Save As");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", new String[]{"*.txt"});
        fileChooser.getExtensionFilters().add(extFilter);
        selectedDirectory = fileChooser.showSaveDialog(stage);

        System.out.println(selectedDirectory.getAbsolutePath());
        //saveTextFile(selectedDirectory.getAbsolutePath(), content);

        return selectedDirectory;

    }

    public void saveTextFile(String filepath, String content) throws IOException {
            PrintWriter fstream = new PrintWriter(new FileWriter("test"));

            for(String word : content.split("\n"))  {
                fstream.println(word);
            }
    }
}
