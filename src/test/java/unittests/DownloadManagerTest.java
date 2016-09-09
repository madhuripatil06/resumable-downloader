package unittests;

import downloader.DownloadManager;
import downloader.OutputStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloadManagerTest {
    private BufferedOutputStream out;
    private BufferedInputStream in;

    @Before
    public void setup() throws IOException {
        out = mock(BufferedOutputStream.class);
        in = mock(BufferedInputStream.class);
        Mockito.doThrow(new IOException()).when(out).write(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.doReturn(10).when(in).read(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void shouldBeAbleToStartADownload() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        Mockito.doNothing().when(outputStream).write();
        DownloadManager downloadManager = new DownloadManager(outputStream);
        downloadManager.start();
        verify(outputStream).write();
    }

    @Test
    public void shouldBeAbleToPauseAStartedDownload() throws IOException, InterruptedException {
        TestOutputStream outputStream = new TestOutputStream(in, out, 1024);
        DownloadManager downloadManager = new DownloadManager(outputStream);
        downloadManager.start();
        assertTrue(downloadManager.cancel());
    }

    class TestOutputStream extends OutputStream {
        public TestOutputStream(BufferedInputStream in, BufferedOutputStream out, int bufferSize) {
            super(in, out, bufferSize);
        }

        @Override
        public void write() throws IOException {
            while (true);
        }
    }
}
