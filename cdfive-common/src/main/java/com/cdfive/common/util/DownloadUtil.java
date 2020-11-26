package com.cdfive.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author cdfive
 */
public class DownloadUtil {

    private static final int BUFFER_SIZE = 32 * 1024;

    private DownloadUtil() {

    }

    public static InputStream getInputStreamFromHttpUrl(String httpFileURL) {

        try {
            URLConnection urlConnection = null;
            urlConnection = new URL(httpFileURL).openConnection();
            urlConnection.connect();
            return urlConnection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("getInputStreamFromHttpUrl exception", e);
        }
    }

    public static byte[] getBytesFromHttpUrl(String httpUrl) {

        InputStream bufferedInputStream = null;
        try {
            bufferedInputStream = getInputStreamFromHttpUrl(httpUrl);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            for (int len = 0; (len = bufferedInputStream.read(buffer)) != -1; ) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException("getBytesFromHttpUrl exception", e);
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("getBytesFromHttpUrl close exception", e);
                }
            }
        }
    }
}
