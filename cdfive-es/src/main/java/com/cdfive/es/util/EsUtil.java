package com.cdfive.es.util;

import com.cdfive.es.query.SearchQuery;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author cdfive
 */
public class EsUtil {

    public static String genDsl(SearchQuery searchQuery) {
        return searchQuery.toSearchSourceBuilder().toString();
    }

    public static String getHttpRequestBody(HttpRequest request) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ((HttpEntityEnclosingRequest) request).getEntity().writeTo(outStream);
            return new String(outStream.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
