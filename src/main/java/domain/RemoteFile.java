package domain;

import com.google.common.base.Preconditions;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by pankajs on 08/09/16.
 */
public class RemoteFile {
    private final URL originalSourceUrl;
    private final String originalDestinationPath;
    private final String fullTargetPath;
    private String originalFileName;

    public RemoteFile(final String remoteUrl, final String localPath) throws MalformedURLException {
        Preconditions.checkArgument(directoryExists(localPath));
        this.originalSourceUrl = new URL(remoteUrl);
        this.originalDestinationPath = localPath;
        this.originalFileName = FilenameUtils.getName(remoteUrl);
        this.fullTargetPath = targetPath(localPath);
    }

    private boolean directoryExists(String destinationPath) {
        String directory = FilenameUtils.getPath(destinationPath);
        return Files.exists(Paths.get(directory));
    }

    private String targetPath(final String destinationPath) {
        if (Files.isDirectory(Paths.get(destinationPath)))
             return FilenameUtils.concat(originalDestinationPath, originalFileName);
        return destinationPath;
    }

    public long localCopyLength() throws IOException {
        Path path = Paths.get(fullTargetPath);
        if(Files.exists(path))
            return Files.size(path);
        return 0;
    }

    public String sourceFileName() {
        return originalFileName;
    }

    public String targetPath() {
        return fullTargetPath;
    }

    public URL sourceUrl() throws MalformedURLException {
        return originalSourceUrl;
    }
}
