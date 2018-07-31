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
     *
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

    public boolean close(){

    }

    public void run() {
        try {
            long startTime   = System.currentTimeMillis();
            final long MIN_LOOP_TIME = 150;


            while(continueRun) {
                if (fileTabPane.isEmpty()) {
                    thread.sleep(200);//so we dont spam if nothing exists
                    continue;
                }

                if (fileTabPane.isFocused()) {
                    fileTabPane.setOnKeyTyped(event -> {
                        this.isChanged = fileTabPane.getCurrent().checkChangedFromFile();
                    });
                }

                thread.sleep(Math.max(0, System.currentTimeMillis()-MIN_LOOP_TIME));//so we dont negative sleep
            }
        }catch (InterruptedException e) {

        }

    }

    public void start () {
        System.out.println("Starting " +  THREAD_NAME );
        if (thread == null) {
            thread = new Thread (this, THREAD_NAME);
            thread.start ();
        }
    }
}
