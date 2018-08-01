package GUI;

import Backend.FileOperations;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileTab extends Tab {
    FileOperations fileOperations;
    private TextArea textSpace;
    private File lastVer; //last saved version of this text document
    private boolean hasChangedSinceSave;
    private static int newTabCount = 0;//keeps count of how many new tabs have been opened

    /**
     * Creates a new tab with no text and a title of "New Untitled"
     */
    FileTab(){
        this(null, null);
    }

    /**
     *  creates a new tab with no text and a specified title
     * @param title of the new tab
     */
    FileTab(String title){
        this(title, null);
    }

    /**
     * Creates a new tab with the content from a file specified
     *  and a title of the file's name.
     * @param preexistingFile the file the new tab will open to
     */
    FileTab(File preexistingFile){
        this(preexistingFile.getName(), preexistingFile);
    }

    /**
     * Creates a new tab with the content from a file specified
     * If file is null the textArea will be empty
     * @param title of the tab
     * @param preexistingFile the file the new tab will open to
     */
    private FileTab(String title, File preexistingFile){

        fileOperations = new FileOperations();
        lastVer        = preexistingFile;
        String text    = "";

        if(title == null){
            title = ("Untitled " + ++newTabCount);
        }
        if(lastVer != null) {
            try {
                text  = fileOperations.readFile(lastVer);//gets all the text from the preexisting file
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        this.setText(title);         //set tab title
        textSpace = new TextArea();  //new text area
        this.setContent(textSpace);  //places textArea inside of this tab
        textSpace.setWrapText(false);//disables textWrap TODO: make a global settings var control this
        textSpace.setText(text);     //places text inside of the textArea from opening.


        this.setOnCloseRequest(e -> {
        });
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
    public void save() throws FileNotFoundException, IOException {
        if(lastVer == null) {
            throw new FileNotFoundException("There is no previous file to base this save off of!");
        }

        try {
            fileOperations.saveTextFile(lastVer, this.getFileText());
        }catch(IOException e){
            throw new IOException("ERROR: Attempting IO Operation of SAVE");
        }catch (NullPointerException e){
            System.out.println("\u001B[31m" + "ERROR: SOMETHING THAT YOU ARE TRYING TO ACCESS DOES NOT EXIST!");
            e.printStackTrace();
        }
    }

    /**
     * saves the current text file using a user chosen
     *  location/name
     */
    public void saveAs() throws IOException, NullPointerException {

        FileOperations fileOperations = new FileOperations();

        try {

            String fileName = fileOperations.saveTextFileAs(this.getFileText()).getName();
            this.setTabTitle(fileName);
        } catch (IOException e) {
            throw new IOException("ERROR: Attempting IO Operation of SAVE_AS");
        } catch (NullPointerException e){
            System.out.println("ERROR: SOMETHING THAT YOU ARE TRYING TO ACCESS DOES NOT EXIST!");
            e.printStackTrace();
        }

        hasChangedSinceSave = false;

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

        try {
            if(!fileOperations.readFile(lastVer).equals(this.getFileText())){
                System.out.println("Comparing both vers");
                this.hasChangedSinceSave = true;
                return this.hasChangedSinceSave;//if the two are not the same, the file has changed
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        this.hasChangedSinceSave = false;
        return this.hasChangedSinceSave;
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

