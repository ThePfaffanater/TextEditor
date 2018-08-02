package Backend;

import GUI.FileTabPane;

public class ChangeDetect extends Thread{

    private final String THREAD_NAME;
    private Thread thread;
    private static int totalThreadCount = 0;
    private final FileTabPane fileTabPane;
    private boolean isChanged;
    private boolean continueRun;

    /**
     *Wraps a {@link GUI.FileTab} to continously check for
     * changes between the current version and the last saved.
     * @param fileTabPane the TabPane needed to access the currently focused tabs within own thread
     */
    public ChangeDetect(FileTabPane fileTabPane){
        this.fileTabPane = fileTabPane;
        this.isChanged   = false;
        THREAD_NAME      = ("CHANGE_DETECT_FOCUSED_TAB_" + (++totalThreadCount));
        continueRun      = true;
    }

    public boolean isChanged(){
        return this.isChanged;
    }

    /**
     * will close the thread of change detect for a safe deletion.
     */
    public void close(){
        continueRun = false;
    }

    public void run() {
        try {
            System.out.println("Starting new thread: " +  THREAD_NAME );
            final long MIN_LOOP_TIME = 350;
            while(continueRun) {
                long START_TIME   = System.currentTimeMillis();
                if (fileTabPane.isEmpty()) {
                    Thread.sleep(750);//so we dont spam if nothing exists
                    continue;
                }else {
                        if (fileTabPane.getCurrent().isFocused() && fileTabPane.getCurrent().hasKeyBeenPressed()) {
                            this.isChanged = fileTabPane.getCurrent().checkChangedFromFile();
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                System.out.println(e.getMessage());
                            }

                        }else this.isChanged = fileTabPane.getCurrent().isChanged();
                }
                Thread.sleep(Math.max(0, MIN_LOOP_TIME-(System.currentTimeMillis()-START_TIME)));//so we don't negative sleep
            }
        }catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    public void start () {
        if (thread == null) {
            thread = new Thread (this, THREAD_NAME);
            thread.start ();
        }
    }
}
