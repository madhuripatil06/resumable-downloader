package unittests;

import downloader.OutputStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import static org.mockito.Mockito.mock;

/**
 * Created by pankajs on 09/09/16.
 */
public class OutputStreamTest {
    private BufferedOutputStream out;
    private BufferedInputStream in;

    @Before
    public void setup() throws IOException {
        out = mock(BufferedOutputStream.class);
        in = mock(BufferedInputStream.class);
        Mockito.doThrow(new IOException()).when(out).write(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.doReturn(10).when(in).read(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test(expected = IOException.class)
    public void shouldReturnAppendOnlyOutPutStream() throws IOException {
        OutputStream outputStream = new OutputStream(in, out, 1024);
        outputStream.write();
    }
}
