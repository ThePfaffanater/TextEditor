package Backend;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {

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

    public String readFile(File file) throws FileNotFoundException {
        String output = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                output+= (line + "\n");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return output;
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
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", new String[]{"*.txt"});
        fileChooser.getExtensionFilters().add(extFilter);
        selectedDirectory = fileChooser.showSaveDialog(stage);

        saveTextFile(selectedDirectory, content);
        return selectedDirectory;
    }

    public void saveTextFile(File saveTo, String content) throws IOException {
        System.out.println("Saved file to:" + saveTo.getAbsolutePath());

        PrintWriter fstream = new PrintWriter(saveTo);
        for(String line : content.split("[\\r\\n]+"))  {
            fstream.write(line + "\r\n");
        }
        fstream.close();
    }
}
