package GUI;

import Backend.FileOperations;
import Backend.FileSaveException;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;

public class TextFileTab extends Tab {
    FileOperations fileOperations;
    private TextArea textSpace;
    private File lastVer; //last saved version of this text document
    private boolean hasChangedSinceSave;
    private static int newTabCount = 0;//keeps count of how many new tabs have been opened

    /**
     * Creates a new tab with no text and a title of "New Untitled"
     */
    public TextFileTab(){
        this(null, null);
    }

    /**
     *  creates a new tab with no text and a specified title
     * @param title of the new tab
     */
    public TextFileTab(String title){
        this(title, null);
    }

    /**
     * Creates a new tab with the content from a file specified
     *  and a title of the file's name.
     * @param preexistingFile the file the new tab will open to
     */
    public TextFileTab(File preexistingFile){
        this(preexistingFile.getName(), preexistingFile);
    }

    /**
     * Creates a new tab with the content from a file specified
     * If file is null the textArea will be empty
     * @param title of the tab
     * @param preexistingFile the file the new tab will open to
     */
    private TextFileTab(String title, File preexistingFile){

        fileOperations = new FileOperations();
        lastVer        = preexistingFile;
        String text    = "";

        if(title == null){
            title = ("Untitled " + ++newTabCount);
        }
        if(lastVer != null) {
            text  = fileOperations.getStringFromTextFile(lastVer);//gets all the text from the preexisting file
        }

        this.setText(title);//Sets the title of the tab
        textSpace = new TextArea();
        this.setContent(textSpace);
        textSpace.setWrapText(false);
        textSpace.setText(text);
    }


    /**
     * @param newName what the tab title will be changed to
     */
    public void setTabTitle(String newName){
        this.setText(newName);
    }

    /**
     * @returns the title of the tab
     */
    public String getTabTitle(){
        return this.getText();
    }

    /**
     * saves the current text file using it's location/name
     */
    public void save() throws FileSaveException, IOException {
        if(lastVer == null) {
            throw new FileSaveException("There is no previous file to base this save off of!");
        }

        try {
            fileOperations.saveTextFile(lastVer.getAbsolutePath(), this.getFileText());
        }catch(IOException e){
            throw new IOException("ERROR attempting IO operation of save");
        }
    }

    /**
     * saves the current text file using a user chosen
     *  location/name
     */
    public void saveAs() throws IOException {

        FileOperations fileOperations = new FileOperations();

        try {

            String fileName = fileOperations.saveTextFileAs(this.getFileText()).getName();
            this.setTabTitle(fileName);
        } catch (IOException e) {
            throw new IOException("ERROR Attempting IO Operation of SAVE_AS");
        }
        hasChangedSinceSave = false;
    }

    /**
     * @return true if current text is different from the saved version
     * does perform a costly read of the previous text doc.
     */
    public boolean checkChangedFromFile(){
        if(lastVer==null) {
            return false;//if the file has never been saved
        }                // return false.


        if(!fileOperations.getStringFromTextFile(lastVer).equals(this.getFileText())){ //if the two are not the same, the file has changed
            return true;
        }else{
            return false;
        }
    }

    public boolean isChanged(){
        return this.hasChangedSinceSave;
    }

    /**
     * @return the contents of the textArea in this tab
     */
    private String getFileText(){
        return textSpace.getText();
    }
}

