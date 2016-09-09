package org.spike.downloader;

import java.util.Arrays;

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
            String[] shapes = new String[]{"|", "/", "-", "\\"};
            Arrays.stream(shapes).forEach(shape -> draw(message, shape));
        }
    }

    private void draw(String message, String shape) {
        try {
            System.out.printf("\r\b " + message + " " + shape);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
