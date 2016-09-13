package org.spike.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pankajs on 09/09/16.
 */
public class HttpRangeConnection {
    private final HttpURLConnection urlConnection;

    public HttpRangeConnection(final URL url, final long downloadedBytes) throws IOException {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Range", "bytes=" + downloadedBytes + "-");
    }

    public int contentLength(){
        return urlConnection.getContentLength();
    }

    public InputStream getInputStream() throws IOException {
        return urlConnection.getInputStream();
    }
}
