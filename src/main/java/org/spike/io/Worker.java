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

    public Worker(final RemoteFile remoteFile,HttpRangeConnection HttpRangeConnection, Callback callback) throws IOException {
        this.connection = HttpRangeConnection;

        this.remoteFile = remoteFile;
        this.callback = callback;
    }

    public void write() throws IOException {
        long downloadedFileLength = remoteFile.localCopyLength();
        double total = downloadedFileLength;
        try(BufferedInputStream in = new BufferedInputStream(connection.getInputStream())){
            try(BufferedOutputStream out = new BufferedOutputStream(FileOutputStreamFactory.make(remoteFile))) {
                byte[] data = new byte[4096];
                for (int x = in.read(data); x >= 0; x = in.read(data)) {
                    out.write(data, 0, x);
                    total += x;
                    callback.invoke(progressInPercent(total, downloadedFileLength));
                }
                out.write(data);
            }
        }
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
            e.printStackTrace();
        }
    }
}
