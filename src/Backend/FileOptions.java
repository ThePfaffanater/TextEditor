package Backend;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FileOptions {

    Stage stage = new Stage();
    File lastFile = null;

    /**
     *
     * @param text the text file you are wishing ot save
     * @return a File object of the TextFile saved.
     */
    public File saveTextFileAs(String text){
        final FileChooser fileChooser = new FileChooser();
        File selectedDirectory;
        fileChooser.setTitle("Save As");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", new String[]{"*.txt"});
        fileChooser.getExtensionFilters().add(extFilter);
        selectedDirectory = fileChooser.showSaveDialog(stage);
        return selectedDirectory;

    }

    public ArrayList<File> openTextFiles(){
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", new String[]{"*.txt"});
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open .VB File(s)");
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        ArrayList<File> arrayList = new ArrayList<>(list);
        return arrayList;
    }

    public String getStringFromTextDoc(File file){
        String words;
        try {
            return  words = new Scanner(file).useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error Reading File!!");
        }
        return "";
    }

    public File getLastFile(){
        return lastFile;
    }


    private void SaveTextFile(File file, String content){
        try {
            PrintWriter fstream = new PrintWriter(new FileWriter(file));

            for(String word : content.split("\n"))  {
                fstream.println(word);
            }
        } catch (IOException e) {
            System.out.println("ERROR SAVING AS *FILE*!");
            e.printStackTrace();
        }
    }
}
