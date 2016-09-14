package org.spike;

import com.google.common.base.Preconditions;
import org.spike.domain.RemoteFile;
import org.spike.downloader.DownloadManager;
import org.spike.io.Worker;
import org.spike.downloader.ProgressBar;
import org.spike.net.HttpRangeConnection;

import java.io.IOException;
import java.util.Timer;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloaderApp {
    public static void main(String[] args) throws InterruptedException {
        try {
            Preconditions.checkArgument(args.length == 2, "url & location are mandatory parameters");
            ProgressBar progressBar = new ProgressBar(30);
            RemoteFile remoteFile= new RemoteFile(url(args), location(args));
            HttpRangeConnection httpRangeConnection = new HttpRangeConnection(remoteFile.sourceUrl(), remoteFile.localCopyLength());
            Worker worker = new Worker(remoteFile,httpRangeConnection, progressBar);
            DownloadManager downloadManager = new DownloadManager(worker);
            downloadManager.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String location(String[] args) {
        return args[1];
    }

    private static String url(String[] args) {
        return args[0];
    }
}
