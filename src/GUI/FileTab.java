package GUI;

import Backend.Config.ITextEditorConfig;
import Backend.FileOperations;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileTab extends Tab {
    private final ITextEditorConfig CONFIG;
    private final FileOperations fileOperations;
    private final TextArea textSpace;
    private File lastVer; //last saved version of this text document
    private boolean hasChangedSinceSave;
    private boolean hasKeyBeenPressed;
    private static int newTabCount = 0;//keeps count of how many new tabs have been opened

    /**
     * Creates a new tab with no text and a title of "New Untitled"
     * @param CONFIG the Configuration of the TextEditor
     */
    FileTab(final ITextEditorConfig CONFIG){
        this(null, null, CONFIG);
    }

    /**
     *  creates a new tab with no text and a specified title
     * @param title of the new tab
     * @param CONFIG the Configuration of the TextEditor
     */
    FileTab(String title, final ITextEditorConfig CONFIG){
        this(title, null, CONFIG);
    }

    /**
     * Creates a new tab with the content from a file specified
     *  and a title of the file's name.
     * @param preexistingFile the file the new tab will open to
     * @param CONFIG the Configuration of the TextEditor
     */
    FileTab(File preexistingFile, final ITextEditorConfig CONFIG){
        this(preexistingFile.getName(), preexistingFile, CONFIG);
    }

    /**
     * Creates a new tab with the content from a file specified
     * If file is null the textArea will be empty
     * @param title of the tab
     * @param preexistingFile the file the new tab will open to
     * @param CONFIG the Configuration of the TextEditor
     */
    private FileTab(String title, File preexistingFile, final ITextEditorConfig CONFIG){
        this.CONFIG = CONFIG;
        fileOperations = new FileOperations();
        lastVer        = preexistingFile;
        String text    = "";

        if(title == null){
            title = ("Untitled " + ++newTabCount);
        }
        if(lastVer != null) {
            text  = fileOperations.readFile(lastVer);//gets all the text from the preexisting file
            hasChangedSinceSave = false;
        }else{
            hasChangedSinceSave = true;
        }

        this.setText(title);         //set tab title
        textSpace = new TextArea();  //new text area
        this.setContent(textSpace);  //places textArea inside of this tab
        textSpace.setWrapText(CONFIG.isTextWrapEnabled());//decides textWrap
        textSpace.setText(text);     //places text inside of the textArea from opening.

        this.hasKeyBeenPressed = false;

        textSpace.setOnKeyReleased((e -> this.hasKeyBeenPressed = true));

        this.setOnCloseRequest(e -> {
            //TODO: dont let close if unsaved without confirmation
        });
    }


    /**
     * @param newName what the tab title will be changed to
     */
    private void setTabTitle(String newName){
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
    void save() throws IOException {
        if(lastVer == null) {
            throw new FileNotFoundException("There is no previous file to base this save off of!");
        }

        try {
            fileOperations.saveTextFile(lastVer, this.getFileText());
            hasChangedSinceSave = false;
        }catch(IOException e){
            throw new IOException(CONFIG.ANSI_BLUE  + "ERROR: Attempting IO Operation of SAVE");
        }catch (NullPointerException e){
            System.out.println(CONFIG.ANSI_BLUE + "ERROR: SOMETHING THAT YOU ARE TRYING TO ACCESS DOES NOT EXIST!");
        }
    }

    /**
     * saves the current text file using a user chosen
     *  location/name
     */
    public void saveAs() throws IOException, NullPointerException {

        FileOperations fileOperations = new FileOperations();

        try {

            lastVer = fileOperations.saveTextFileAs(this.getFileText());
            this.setTabTitle(lastVer.getName());
            hasChangedSinceSave = false;
        } catch (IOException e) {
            throw new IOException("ERROR: Attempting IO Operation of SAVE_AS");
        } catch (NullPointerException e){
            System.out.println("ERROR: SOMETHING THAT YOU ARE TRYING TO ACCESS DOES NOT EXIST!");
            e.printStackTrace();
        }
    }

    /**
     * @return true if current text is different from the saved version
     * Does perform a costly read of the previous text fileS.
     */
    public boolean checkChangedFromFile(){
        if(lastVer==null) {
            this.hasChangedSinceSave = true;
            return this.hasChangedSinceSave;//if the file has never been saved
        }                                   // return false.
        else if(!fileOperations.readFile(lastVer).equals(this.getFileText())){
            this.hasChangedSinceSave = true;
            return this.hasChangedSinceSave;//if the two are not the same, the file has changed
        }

        this.hasChangedSinceSave = false;
        return this.hasChangedSinceSave;
    }

    /**
     * @returns true if a key has been pressed in the pane since last time this was called.
     */
    public boolean hasKeyBeenPressed(){
        if(hasKeyBeenPressed) {
           hasKeyBeenPressed = false;
           return true;
        }

        return false;
    }

    /**
     * @return true if the Tab is currently in focus
     */
    public boolean isFocused(){
        return textSpace.isFocused();
    }

    /**
     * @return true if the file has been changed
     * But not updated by manually checking the two files
     * (faster, lighter way of doing it after another thread already manually checked).
     */
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

