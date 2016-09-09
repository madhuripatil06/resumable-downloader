package org.spike;

import org.spike.domain.RemoteFile;
import org.spike.downloader.DownloadManager;
import org.spike.io.OutputStream;
import org.spike.downloader.ProgressBar;

import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloaderApp {
    public static void main(String[] args) throws InterruptedException {
        try {
            RemoteFile remoteFile = new RemoteFile(args[0], args[1]);
            OutputStream outputStream = new OutputStream(remoteFile, 1024);
            DownloadManager manager = new DownloadManager(outputStream);
            manager.start();
            new ProgressBar(manager).display("Downloading..");
        } catch (IOException ex) {
            System.out.println("Download complete.");
        }
    }
}
