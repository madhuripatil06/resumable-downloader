package org.spike.downloader;

/**
 * Created by pankajs on 09/09/16.
 */
public class ProgressBar {
    private final Trackable trackable;

    public ProgressBar(Trackable trackable) {
        this.trackable = trackable;
    }

    public void display(String message) throws InterruptedException {
        while (trackable.isRunning())
        {
            for (String phase : new String[]{"|", "/", "-", "\\"})
            {
                System.out.printf("\r\b " + message + " " + phase);
                Thread.sleep(100);
            }
        }
    }
}
