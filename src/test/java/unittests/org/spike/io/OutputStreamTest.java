package unittests.org.spike.io;

import builders.RemoteFileBuilder;
import org.apache.commons.io.IOUtils;
import org.spike.domain.RemoteFile;
import org.spike.io.OutputStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.spike.net.HttpRangeConnection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by pankajs on 09/09/16.
 */
public class OutputStreamTest {
    private HttpRangeConnection httpRangeConnection;
    private InputStream stubInputStream;


    @Before
    public void setup() throws IOException {
        httpRangeConnection = mock(HttpRangeConnection.class);
        stubInputStream = spy(IOUtils.toInputStream("some test data for my input stream"));

        Mockito.doReturn(-1).when(stubInputStream).read(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.doReturn(stubInputStream).when(httpRangeConnection).getInputStream();
    }

    @Test
    public void shouldReturnAppendOnlyOutPutStream() throws IOException {
        RemoteFile remoteFile = new RemoteFileBuilder().build();
        OutputStream outputStream = new OutputStream(remoteFile, httpRangeConnection, 1024);
        outputStream.write();
        verify(stubInputStream).read(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
    }
}
