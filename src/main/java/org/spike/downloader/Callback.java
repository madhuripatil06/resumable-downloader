package org.spike.downloader;


public interface Callback {
    void invoke(int status);

    String progress(int i);
}
