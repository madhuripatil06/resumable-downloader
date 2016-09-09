package downloader;

import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloadManager {
    private final OutputStream outputStream;
    private Thread thread;

    public DownloadManager(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void start() throws IOException {
        thread = new Thread(new Runnable() {
            public void run()
            {
                try {
                    outputStream.write();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
}
