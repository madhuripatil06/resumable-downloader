package org.spike.io;

import org.spike.net.HttpRangeConnection;
import org.spike.domain.RemoteFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class OutputStream {
    private final BufferedOutputStream out;
    private final BufferedInputStream in;
    private final int bufferSize;

    public OutputStream(final RemoteFile remoteFile, int bufferSize) throws IOException {
        HttpRangeConnection connection = new HttpRangeConnection(remoteFile.sourceUrl(), remoteFile.localCopyLength());
        this.in = new BufferedInputStream(connection.getInputStream());
        this.out = new BufferedOutputStream(FileOutputStreamFactory.make(remoteFile), bufferSize);
        this.bufferSize = bufferSize;
    }

    public OutputStream(final BufferedInputStream in, final BufferedOutputStream out, int bufferSize) {
        this.bufferSize = bufferSize;
        this.in = in;
        this.out = out;
    }

    public void write() throws IOException {
            byte[] data = new byte[bufferSize];
            for (int x = 0; x >= 0; x = in.read(data, 0, bufferSize))
                out.write(data, 0, x);
            out.write(data);
    }
}
