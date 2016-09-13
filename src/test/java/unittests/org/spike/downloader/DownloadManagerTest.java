package unittests.org.spike.downloader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.spike.downloader.DownloadManager;
import org.spike.io.Worker;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by pankajs on 09/09/16.
 */
public class DownloadManagerTest {
    private Worker worker;

    @Before
    public void setup() throws IOException {
        worker = mock(Worker.class);
        Mockito.doNothing().when(worker).write();
    }

    @Test
    public void shouldBeAbleToStartADownload() throws IOException {
        Worker worker = mock(Worker.class);
        Mockito.doThrow(new IOException()).when(worker).write();
        DownloadManager downloadManager = new DownloadManager(worker);
        downloadManager.start();
    }

    @Test
    public void shouldBeAbleToPauseAStartedDownload() throws IOException, InterruptedException {
        DownloadManager downloadManager = new DownloadManager(worker);
        downloadManager.start();
        assertTrue(downloadManager.cancel());
    }

    @Test
    public void isDownloadCompleteShouldReturnFalseWhenDownloadIsComplete() throws IOException, InterruptedException {
        DownloadManager downloadManager = new DownloadManager(worker);
        downloadManager.start();
        downloadManager.cancel();
        assertFalse(downloadManager.isRunning());
    }

}
