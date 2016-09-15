package org.spike.downloader;

import org.spike.domain.RemoteFile;
import org.spike.io.Worker;
import org.spike.net.HttpRangeConnection;

import java.io.IOException;
import java.net.MalformedURLException;

public class DownloadManager implements Trackable {
    private final Worker worker;
    private Thread thread;
    private Object mutex=new Object();

    public DownloadManager(final Worker worker) {
        this.worker = worker;
    }

    public DownloadManager(RemoteFile remoteFile) throws IOException {
        ProgressBar progressBar = new ProgressBar(30);
        HttpRangeConnection httpRangeConnection = new HttpRangeConnection(remoteFile.sourceUrl(), remoteFile.localCopyLength());
        this.worker = new Worker(remoteFile,httpRangeConnection, progressBar);
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
