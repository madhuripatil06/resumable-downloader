package unittests.org.spike.io;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.spike.io.Worker;
import org.spike.net.HttpRangeConnection;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by pankajs on 10/09/16.
 */
public class WorkerTest {
    private HttpRangeConnection connection;
    private final String SOME_RANDOM_TEXT = "some test data for my input stream";

    @Before
    public void setup() throws IOException {
        connection = mock(HttpRangeConnection.class);
        Mockito.doReturn(IOUtils.toInputStream(SOME_RANDOM_TEXT))
                .when(connection)
                .getInputStream();
    }

    @Test
    public void shouldReturnFutureOfBytes() throws IOException {
        Worker worker = new Worker(connection);
        List<Byte> downloadedFile = worker.call();
        Assert.assertNotNull(downloadedFile);
    }
}
