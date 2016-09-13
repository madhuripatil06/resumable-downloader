package builders;

import org.spike.domain.RemoteFile;

import java.net.MalformedURLException;

/**
 * Created by pankajs on 09/09/16.
 */
public class RemoteFileBuilder {
    private static final String SOURCE_PATH = "http://some_url";
    private static final String DESTINATION_PATH = "src/main/resources";
    private RemoteFile remoteFile;

    public RemoteFileBuilder() throws MalformedURLException {
        this.remoteFile = new RemoteFile(SOURCE_PATH, DESTINATION_PATH);
    }

    public RemoteFile build() {
        return  remoteFile;
    }
}
