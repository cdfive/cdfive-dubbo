package com.cdfive.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author cdfive
 */
public class ZipUtil {

    public static final int BUFFER_SIZE = 256;

    public static byte[] compressByZip(String data) {
        try {
            return StringUtils.isEmpty(data) ? null : compressByZip(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("compressByZip error", e);
        }
    }

    public static byte[] compressByZip(byte[] data) {
        if (ArrayUtils.isEmpty(data)) {
            return data;
        } else {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                GZIPOutputStream gzip = new GZIPOutputStream(out);
                gzip.write(data);
                gzip.close();
                return out.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException("compressByZip", e);
            }
        }
    }

    public static byte[] decompressByZip(byte[] data) {
        if (ArrayUtils.isEmpty(data)) {
            return data;
        } else {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                GZIPInputStream gzipInputStream = new GZIPInputStream(in);
                byte[] buffer = new byte[BUFFER_SIZE];

                int n;
                while ((n = gzipInputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }

                gzipInputStream.close();
                in.close();
                return out.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException("decompressByZip error", e);
            }
        }
    }
}
