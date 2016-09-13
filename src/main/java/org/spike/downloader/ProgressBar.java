package org.spike.downloader;

import java.util.Arrays;

/**
 * Created by pankajs on 09/09/16.
 */
public class ProgressBar implements Callback {
    private  Thread thread;

    public void display(final String message) throws InterruptedException {
        thread = new Thread() {
            public void run() {
                while (true)
                {
                    String[] shapes = new String[]{"|", "/", "-", "\\"};
                    Arrays.stream(shapes).forEach(shape -> draw(message, shape));
                }
            }
        };

        thread.start();
    }

    private void draw(final String message, final String shape) {
        try {
            System.out.printf("\r\b " + message + shape);
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void invoke(String status) {
        System.out.println("Download " + status);
        thread.stop();
    }
}
