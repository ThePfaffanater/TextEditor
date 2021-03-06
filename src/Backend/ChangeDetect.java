package Backend;

import Backend.Config.ITextEditorConfig;
import GUI.FileTabPane;

public class ChangeDetect {

    private final ITextEditorConfig CONFIG;
    private final String THREAD1_NAME;
    private final String THREAD2_NAME;
    private static int totalThreadCount = 0;
    private final FileTabPane fileTabPane;
    private boolean isChanged;
    private boolean isModified;
    private boolean continueRun;

    /**
     *Wraps a {@link GUI.FileTab} to continously check for
     * changes between the current version and the last saved.
     * @param fileTabPane the TabPane needed to access the currently focused tabs within own thread
     */
    public ChangeDetect(FileTabPane fileTabPane, final ITextEditorConfig CONFIG){
        this.CONFIG      = CONFIG;
        this.fileTabPane = fileTabPane;

        THREAD1_NAME = ("CHANGE_DETECT_FOCUSED_TAB_" + (++totalThreadCount));
        THREAD2_NAME = ("CHANGE_DETECT_FOCUSED_TAB_" + (++totalThreadCount));

        isChanged   = false;
        isModified  = false;
        continueRun = false;
    }

    public boolean isChanged(){
        return this.isChanged;
    }

    public boolean isModified(){
        return isModified;
    }
    /**
     * will close the thread of change detect for a safe deletion.
     */
    public void close(){
        continueRun = false;
    }

    public void start () {
        if(!continueRun) continueRun = true;
        run();
    }

    private void run() {

        //Check to see if the file we are currently modifying has been changed without us
        new Thread(() -> {
            System.out.println(CONFIG.getNotificationColor() + "Starting new thread: " + THREAD1_NAME + CONFIG.ANSI_RESET);
            while (continueRun) {
                final long MIN_LOOP_TIME = 2500;
                long START_TIME   = System.currentTimeMillis();

                if (!fileTabPane.isEmpty()) {
                    if (fileTabPane.getCurrent().checkFileModified()) {
                        //TODO: prompt the user about reloading to the changed file or saving the current as new file again
                        System.out.println(CONFIG.getWarningColor() + "FILE HAS BEEN MODIFIED" + CONFIG.ANSI_RESET);
                        isModified = true;
                    }
                    else isModified = false;
                }

                try {
                    Thread.sleep(Math.max(0, MIN_LOOP_TIME - (System.currentTimeMillis() - START_TIME)));//so we don't negative sleep
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();

        //Check for change between last saved version and now
        new Thread(() -> {
            System.out.println(CONFIG.getNotificationColor() + "Starting new thread: " + THREAD2_NAME + CONFIG.ANSI_RESET);
            while(continueRun) {//in ram file change check loop
                final long MIN_LOOP_TIME = 350;
                long START_TIME   = System.currentTimeMillis();
                if (fileTabPane.isEmpty()) {
                    try {
                        Thread.sleep(750);//so we dont spam if nothing exists
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    continue;
                }else {
                    if(isModified) this.isChanged = true;
                    else if (fileTabPane.getCurrent().isFocused() && fileTabPane.getCurrent().hasKeyBeenPressed()) {
                        this.isChanged = fileTabPane.getCurrent().checkChangedFromText();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }

                    }else this.isChanged = fileTabPane.getCurrent().isChanged();
                }
                try {
                    Thread.sleep(Math.max(0, MIN_LOOP_TIME-(System.currentTimeMillis()-START_TIME)));//so we don't negative sleep
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }
}
