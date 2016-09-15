package org.spike;

import com.google.common.base.Preconditions;
import org.spike.domain.RemoteFile;
import org.spike.downloader.DownloadManager;

import java.io.IOException;


public class DownloaderApp {
    public static void main(String[] args) throws InterruptedException {
        try {
            Preconditions.checkArgument(args.length >= 2, "url & location are mandatory parameters");
            processs(args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void processs(String[] args) throws IOException {
        for (String url : urls(args)) {
            downloadFile(url, location(args));
        }
    }

    private static void downloadFile(String url, String location) throws IOException {
        RemoteFile remoteFile= new RemoteFile(url, location);
        DownloadManager downloadManager = new DownloadManager(remoteFile);
        downloadManager.start();
    }


    private static String location(String[] args) {
        return args[args.length-1];
    }

    private static String[] urls(String[] args) {
        String [] allUrls = new String[args.length-1];
        for(int i = 0 ; i < args.length-1 ; i++)
            allUrls[i] = args[i];
        return allUrls;
    }
}
