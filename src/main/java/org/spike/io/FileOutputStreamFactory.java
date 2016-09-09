package org.spike.io;

import org.spike.domain.RemoteFile;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by pankajs on 09/09/16.
 */
public class FileOutputStreamFactory {
    public static FileOutputStream make(final RemoteFile remoteFile) throws IOException {
        if (remoteFile.localCopyLength() == 0)
            return new FileOutputStream(remoteFile.targetPath());
        return new FileOutputStream(remoteFile.targetPath(), true);
    }
}
