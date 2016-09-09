import domain.RemoteFile;
import downloader.DownloadManager;
import downloader.OutputStream;
import downloader.ProgressBar;

import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloaderApp {
    private static final String SOURCE_PATH = "http://dynamodb-local.s3-website-us-west-2.amazonaws.com/dynamodb_local_2016-05-17.zip";
    private static final String DESTINATION_PATH = "src/main/resources";

    public static void main(String[] args) throws InterruptedException {
        try {
            RemoteFile remoteFile = new RemoteFile(SOURCE_PATH, DESTINATION_PATH);
            OutputStream outputStream = new OutputStream(remoteFile, 1024);
            DownloadManager manager = new DownloadManager(outputStream);
            manager.start();
            new ProgressBar(manager).display("Downloading..");
        } catch (IOException ex) {
            System.out.println("Download complete.");
        }
    }
}
