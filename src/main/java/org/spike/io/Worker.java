package org.spike.io;

import org.spike.downloader.Callback;
import org.spike.net.HttpRangeConnection;
import org.spike.domain.RemoteFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class Worker implements Runnable {
    private final RemoteFile remoteFile;
    private final int bufferSize;
    private final Callback callback;
    private final HttpRangeConnection connection;

    public Worker(final RemoteFile remoteFile, int bufferSize, Callback callback) throws IOException {
        this.connection = new HttpRangeConnection(remoteFile.sourceUrl(), remoteFile.localCopyLength());
        this.remoteFile = remoteFile;
        this.bufferSize = bufferSize;
        this.callback = callback;
    }

    public void write() throws IOException {
            try(BufferedInputStream in = new BufferedInputStream(connection.getInputStream())){
                try(BufferedOutputStream out = new BufferedOutputStream(FileOutputStreamFactory.make(remoteFile), bufferSize)) {
                    byte[] data = new byte[bufferSize];
                    for (int x = 0; x >= 0; x = in.read(data, 0, bufferSize))
                        out.write(data, 0, x);
                    out.write(data);
                }
            }
    }

    @Override
    public void run() {
        try {
            write();
            callback.invoke("completed");
        } catch (IOException e) {
            e.printStackTrace();
            callback.invoke("failed");
        }
    }
}
