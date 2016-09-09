package org.spike;

import com.google.common.base.Preconditions;
import org.spike.domain.RemoteFile;
import org.spike.downloader.DownloadManager;
import org.spike.io.OutputStream;
import org.spike.downloader.ProgressBar;

import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloaderApp {
    private static  DownloadManager manager;

    public static void main(String[] args) throws InterruptedException {
        try {
            Preconditions.checkArgument(args.length == 2, "url & location are mandatory parameters");

            DownloadManager downloadManager = initialize(args).start();
            ProgressBar progressBar = new ProgressBar(downloadManager);
            progressBar.display("Downloading...");

        } catch (IOException ex) {
            System.out.println("Download complete.");
        }
    }

    private static DownloadManager initialize(String[] args) throws IOException {
        String url = args[0];
        String location = args[1];
        RemoteFile remoteFile = new RemoteFile(url, location);
        OutputStream outputStream = new OutputStream(remoteFile, 1024);
        return new DownloadManager(outputStream);
    }
}
