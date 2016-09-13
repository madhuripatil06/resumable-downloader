package unittests.org.spike.io;

import builders.RemoteFileBuilder;
import org.apache.commons.io.IOUtils;
import org.spike.domain.RemoteFile;
import org.spike.downloader.Callback;
import org.spike.io.Worker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.spike.net.HttpRangeConnection;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by pankajs on 09/09/16.
 */
public class WorkerTest {
    private HttpRangeConnection httpRangeConnection;
    private InputStream stubInputStream;
    private Callback callback;

    @Before
    public void setup() throws IOException {
        httpRangeConnection = mock(HttpRangeConnection.class);
        callback = mock(Callback.class);
        stubInputStream = spy(IOUtils.toInputStream("some test data for my input stream"));

        Mockito.doReturn(-1).when(stubInputStream).read(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.doReturn(stubInputStream).when(httpRangeConnection).getInputStream();
        Mockito.doNothing().when(callback).invoke(Mockito.anyString());

    }

    @Test
    public void shouldReturnAppendOnlyOutPutStream() throws IOException {
        RemoteFile remoteFile = new RemoteFileBuilder().build();
        Worker worker = new Worker(remoteFile, httpRangeConnection, callback);
        worker.write();
        verify(stubInputStream).read(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
    }
}
