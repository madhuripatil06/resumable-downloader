package builders;

import org.spike.domain.RemoteFile;

import java.net.MalformedURLException;

public class RemoteFileBuilder {
    private static final String SOURCE_PATH = "http://dynamodb-local.s3-website-us-west-2.amazonaws.com/dynamodb_local_2016-05-17.zip";
    private static final String DESTINATION_PATH = "src/main/resources";
    private RemoteFile remoteFile;

    public RemoteFileBuilder() throws MalformedURLException {
        this.remoteFile = new RemoteFile(SOURCE_PATH, DESTINATION_PATH);
    }

    public RemoteFile build() {
        return  remoteFile;
    }
}
