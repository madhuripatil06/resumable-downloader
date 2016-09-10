package org.spike.downloader;

import org.spike.io.OutputStream;

import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloadManager implements Trackable {
    private final OutputStream outputStream;
    private Thread thread;
    private Object mutex=new Object();

    public DownloadManager(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public DownloadManager start() throws IOException {
        thread = new Thread(outputStream);
        thread.start();
        return this;
    }


    public Boolean cancel() {
        if (thread.isAlive() && !thread.isInterrupted()) {
            thread.interrupt();
            return thread.isInterrupted();
        }
        return true;
    }

    public boolean isRunning() {
        return thread.isAlive() && !thread.isInterrupted();
    }
}
