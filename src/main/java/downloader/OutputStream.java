package downloader;

import domain.HttpRangeConnection;
import domain.RemoteFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

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
        FileOutputStream fos=(remoteFile.localCopyLength()==0)? new FileOutputStream(remoteFile.targetPath()):
                new FileOutputStream(remoteFile.targetPath(), true);
        this.out = new BufferedOutputStream(fos, bufferSize);
        this.bufferSize = bufferSize;
    }

    public OutputStream(BufferedInputStream in, BufferedOutputStream out, int bufferSize) {
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
