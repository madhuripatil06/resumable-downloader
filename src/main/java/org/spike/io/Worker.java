package org.spike.io;

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
    private final HttpRangeConnection connection;

    public Worker(final RemoteFile remoteFile, HttpRangeConnection connection, int bufferSize) throws IOException {
        this.connection = connection;
        this.remoteFile = remoteFile;
        this.bufferSize = bufferSize;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
