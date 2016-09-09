package downloader;

import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloadManager implements Trackable {
    private final OutputStream outputStream;
    private Thread thread;
    private Object mutex=new Object();

    public DownloadManager(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void start() throws IOException {
        thread = new Thread(() -> {
            try {
                synchronized (mutex) {
                    outputStream.write();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
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
