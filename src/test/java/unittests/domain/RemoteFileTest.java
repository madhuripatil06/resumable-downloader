package unittests.domain;

import domain.RemoteFile;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pankajs on 08/09/16.
 */
public class RemoteFileTest {
    private static final String SOURCE_PATH = "http://dynamodb-local.s3-website-us-west-2.amazonaws.com/dynamodb_local_2016-05-17.zip";
    private static final String DESTINATION_PATH = "src/main/resources";

    @Test public void shouldExtractTargetFileNameFromSourceUrl() throws MalformedURLException {
        RemoteFile remoteFile = new RemoteFile(SOURCE_PATH, DESTINATION_PATH);
        Assert.assertEquals("dynamodb_local_2016-05-17.zip", remoteFile.sourceFileName());
    }

    @Test public void shouldReturnFullDestinationPath() throws MalformedURLException {
        RemoteFile remoteFile = new RemoteFile(SOURCE_PATH, DESTINATION_PATH);
        Assert.assertEquals("src/main/resources/dynamodb_local_2016-05-17.zip", remoteFile.targetPath());
    }

    @Test public void shouldReturnFullDestinationPathWithFileNameSpecifiedInInput() throws MalformedURLException {
        RemoteFile remoteFile = new RemoteFile(SOURCE_PATH, DESTINATION_PATH + "/some_file_name.txt");
        Assert.assertEquals("src/main/resources/some_file_name.txt", remoteFile.targetPath());
    }

    @Test public void shouldRetunSourcePathAsUrl() throws MalformedURLException {
        RemoteFile remoteFile = new RemoteFile(SOURCE_PATH, DESTINATION_PATH + "/some_file_name.txt");
        Assert.assertTrue(remoteFile.sourceUrl() instanceof URL);
    }

    @Test public void shouldReturn() throws IOException {
        RemoteFile remoteFile = new RemoteFile(SOURCE_PATH, DESTINATION_PATH + "/some_file_name.txt");
        Assert.assertEquals(0, remoteFile.localCopyLength());
    }
}
