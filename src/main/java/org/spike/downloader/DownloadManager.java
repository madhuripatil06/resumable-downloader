package org.spike.downloader;

import org.spike.domain.RemoteFile;
import org.spike.io.Worker;
import org.spike.net.HttpRangeConnection;

import java.io.IOException;

public class DownloadManager implements Trackable {
    private Worker worker;
    private Worker[] workers;
    private Thread thread;
    private String[] urls;
    private String location;

    public DownloadManager(final Worker worker) {
        this.worker = worker;
    }

    public DownloadManager(RemoteFile remoteFile) throws IOException {
        ProgressBar progressBar = new ProgressBar(30);
        HttpRangeConnection httpRangeConnection = new HttpRangeConnection(remoteFile.sourceUrl(), remoteFile.localCopyLength());
        this.worker = new Worker(remoteFile,httpRangeConnection, progressBar);
    }

    public DownloadManager(String[] urls, String location) {
        this.urls = urls;
        this.location = location;
        this.workers = new Worker[urls.length];
    }


    public DownloadManager start() throws IOException {
        thread = new Thread(worker);
        thread.start();
        return this;
    }

    public String startThread(Worker worker){
        new Thread(worker).start();
        return null;
    }

    public void run() throws IOException, InterruptedException {
        for (int i = 0 ; i < urls.length ; i++) {
            RemoteFile remoteFile = new RemoteFile(urls[i], location);
            ProgressBar progressBar = new ProgressBar(30);
            HttpRangeConnection httpRangeConnection = new HttpRangeConnection(remoteFile.sourceUrl(), remoteFile.localCopyLength());
            workers[i] =  new Worker(remoteFile,httpRangeConnection, progressBar);
            startThread(workers[i]);
        }
        display();
    }

    public void display() throws IOException, InterruptedException {
        while(true){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            String result = "";
            for (Worker worker : workers) {
                result += worker.status() + " \n";
            }
            if(isCompleated(result)) {
                System.exit(0);
            }
            System.out.printf(result);
            Thread.sleep(1000);
        }

    }


    private boolean isCompleated(String result) {
        String shouldBe ="";
        for (Worker worker : workers) {
            shouldBe += "100%";
        }
        return result.contains(shouldBe);

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
