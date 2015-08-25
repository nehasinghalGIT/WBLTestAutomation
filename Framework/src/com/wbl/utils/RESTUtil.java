package com.wbl.utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;


/**
 * Created by svelupula on 8/25/2015.
 */
public class RESTUtil {

    private final Configuration _configuration;
    private String _baseURI;
    private Logger _logger;
    private HttpUriRequest request;
    private HttpResponse response;
    private Header[] headers = response.getAllHeaders();

    public RESTUtil(Configuration configuration) {
        _configuration = configuration;
        _logger = Logger.getLogger(PageDriver.class);
    }

    public void get(String resource, String contentType, String accept, String authorization) throws Exception {
        request = new HttpGet(_baseURI + resource);
        if (contentType != null)
            request.setHeader("Content-Type", contentType);
        if (accept != null)
            request.setHeader("Accept", accept);
        if (authorization != null)
            request.setHeader("Authorization", authorization);

        response = HttpClientBuilder.create().build().execute(request);
        headers = response.getAllHeaders();
    }

    private String getHeader(String name) {
        for (Header header : headers) {
            if (header.getName().equals(name)) {
                return header.getValue();
            }
        }
        return null;
    }

    public void getJSON(String resource) throws Exception {
        get(resource, null, "application/json", null);
    }

    public boolean isValidResponse() {
        return (response != null);
    }

    public int getStatusCode() {
        return response.getStatusLine().getStatusCode();
    }

}
