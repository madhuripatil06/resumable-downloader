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
        double total = 0;
        try(BufferedInputStream in = new BufferedInputStream(connection.getInputStream())){
            try(BufferedOutputStream out = new BufferedOutputStream(FileOutputStreamFactory.make(remoteFile))) {
                byte[] data = new byte[4096];
                for (int x = 0; x >= 0; x = in.read(data)) {
                    out.write(data, 0, x);
                    if(x != 0) {
                        total += x;
                        callback.invoke((int) (total / connection.contentLength() * 100));
                    }
                }
                out.write(data);
            }
        }
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
