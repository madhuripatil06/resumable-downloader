package org.spike.io;

import org.apache.commons.lang3.ArrayUtils;
import org.spike.domain.RemoteFile;
import org.spike.net.HttpRangeConnection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by pankajs on 10/09/16.
 */
public class Worker implements Callable<List<Byte>> {
    private final HttpRangeConnection connection;

    public Worker(RemoteFile remoteFile) throws IOException {
        connection = new HttpRangeConnection(remoteFile.sourceUrl(), remoteFile.localCopyLength());
    }

    public Worker(HttpRangeConnection rangeConnection) throws IOException {
        connection = rangeConnection;
    }

    public List<Byte> read() throws IOException {
        try {
            List<Byte> bytes = new LinkedList<>();
            byte[] chunk = new byte[1024];
            try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream())) {
                for (int x = 0; x >= 0; x = in.read(chunk, 0, 1024))
                    bytes.addAll(Arrays.asList(ArrayUtils.toObject(chunk)));
            }
            return bytes;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public List<Byte> call() throws IOException {
        return read();
    }
}
