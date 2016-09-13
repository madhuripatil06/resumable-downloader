package org.spike.downloader;

import java.util.Arrays;

/**
 * Created by pankajs on 09/09/16.
 */
public class ProgressBar implements Callback {
    public void display(final String message) throws InterruptedException {
        while (true)
        {
            String[] shapes = new String[]{"|", "/", "-", "\\"};
            Arrays.stream(shapes).forEach(shape -> draw(message, shape));
        }
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
        System.exit(0);
    }
}
