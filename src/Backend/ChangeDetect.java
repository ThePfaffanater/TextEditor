package Backend;

import GUI.FileTabPane;

public class ChangeDetect extends Thread{

    public final String THREAD_NAME;
    private Thread thread;
    private static int totalThreadCount = 0;
    private FileTabPane fileTabPane;
    private boolean isChanged;
    private boolean continueRun;

    /**
     *Wraps a {@link GUI.FileTab} to continously check for
     * changes between the current version and the last saved.
     * @param fileTabPane
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

    public void close(){
        continueRun = false;
    }

    public void run() {
        try {
            long startTime   = System.currentTimeMillis();
            final long MIN_LOOP_TIME = 200;
            while(continueRun) {
                if (fileTabPane.isEmpty()) {
                    thread.sleep(400);//so we dont spam if nothing exists
                    continue;
                }

                if (fileTabPane.isFocused()) {
                    fileTabPane.setOnKeyTyped(event -> {
                        this.isChanged = fileTabPane.getCurrent().checkChangedFromFile();
                        try {
                            thread.sleep(50);
                        } catch (InterruptedException e) {
                        }
                    });
                }

                thread.sleep(Math.max(0, System.currentTimeMillis()- MIN_LOOP_TIME));//so we don't negative sleep
            }
        }catch (InterruptedException e) {

        }

    }

    public void start () {
        System.out.println("Starting new thread: " +  THREAD_NAME );
        if (thread == null) {
            thread = new Thread (this, THREAD_NAME);
            thread.start ();
        }
    }
}
