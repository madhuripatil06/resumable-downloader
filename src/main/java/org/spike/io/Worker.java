package org.spike.io;

import org.spike.downloader.Callback;
import org.spike.net.HttpRangeConnection;
import org.spike.domain.RemoteFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class Worker implements Runnable {
    private final RemoteFile remoteFile;
    private final Callback callback;
    private final HttpRangeConnection connection;
    private long downloadedFileLength;
    private double total = downloadedFileLength;

    public Worker(final RemoteFile remoteFile,HttpRangeConnection HttpRangeConnection, Callback callback) {
        this.connection = HttpRangeConnection;
        this.remoteFile = remoteFile;
        this.callback = callback;
    }

    public void write() throws IOException {
        downloadedFileLength = remoteFile.localCopyLength();
        total = downloadedFileLength;
        try(BufferedInputStream in = new BufferedInputStream(connection.getInputStream())){
            try(BufferedOutputStream out = new BufferedOutputStream(FileOutputStreamFactory.make(remoteFile))) {
                byte[] data = new byte[4096];
                for (int x = in.read(data); x >= 0; x = in.read(data)) {
                    out.write(data, 0, x);
                    total += x;
                }
                out.write(data);
            }
        }
    }

    public String status() throws IOException {
        return callback.progress(progressInPercent(total, downloadedFileLength));
    }

    private int progressInPercent(double total, long downloadedFileLength) throws IOException {
        long actualContentLength = connection.contentLength() + downloadedFileLength;
        return (int) (total / actualContentLength * 100);
    }

    @Override
    public void run() {
        try {
            write();
        } catch (IOException e) {
            System.out.print("Make Sure file doesent exists already exists with 100% download ");
        }
    }
}
