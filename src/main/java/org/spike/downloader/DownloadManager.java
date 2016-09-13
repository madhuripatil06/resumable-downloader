package org.spike.downloader;

import org.spike.io.Worker;

import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloadManager implements Trackable {
    private final Worker worker;
    private Thread thread;
    private Object mutex=new Object();

    public DownloadManager(final Worker worker) {
        this.worker = worker;
    }

    public DownloadManager start() throws IOException {
        thread = new Thread(worker);
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
