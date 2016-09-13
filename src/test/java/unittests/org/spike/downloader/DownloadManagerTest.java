package unittests.org.spike.downloader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.spike.downloader.DownloadManager;
import org.spike.io.OutputStream;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloadManagerTest {
    private OutputStream outputStream;

    @Before
    public void setup() throws IOException {
        outputStream = mock(OutputStream.class);
        Mockito.doNothing().when(outputStream).write();
    }

    @Test
    public void shouldBeAbleToStartADownload() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        Mockito.doThrow(new IOException()).when(outputStream).write();
        DownloadManager downloadManager = new DownloadManager(outputStream);
        downloadManager.start();
    }

    @Test
    public void shouldBeAbleToPauseAStartedDownload() throws IOException, InterruptedException {
        DownloadManager downloadManager = new DownloadManager(outputStream);
        downloadManager.start();
        assertTrue(downloadManager.cancel());
    }

    @Test
    public void isDownloadCompleteShouldReturnFalseWhenDownloadIsComplete() throws IOException, InterruptedException {
        DownloadManager downloadManager = new DownloadManager(outputStream);
        downloadManager.start();
        downloadManager.cancel();
        assertFalse(downloadManager.isRunning());
    }

}
