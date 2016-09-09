package downloader;

import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloadManager {
    private final OutputStream outputStream;
    private Thread thread;
    private Object mutex=new Object();
    private boolean downloadComplete;

    public DownloadManager(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void start() throws IOException {
        thread = new Thread(new Runnable() {
            public void run()
            {
                try {
                    synchronized (mutex) {
                        outputStream.write();
                        downloadComplete = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        downloadComplete = false;
        thread.start();
    }


    public Boolean cancel() {
        downloadComplete = true;
        if (thread.isAlive() && !thread.isInterrupted()) {
            thread.interrupt();
            return thread.isInterrupted();
        }
        return true;
    }

    public boolean isDownloadComplete() {
        return downloadComplete;
    }
}
