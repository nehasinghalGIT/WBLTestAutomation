package com.wbl.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


/**
 * Created by svelupula on 8/25/2015.
 */
public class RESTUtil {

    private final Configuration _configuration;
    private Logger _logger;
    private HttpUriRequest request;
    private HttpResponse response;
    private Header[] headers; //response.getAllHeaders();
    private JSONArray jsonArray;
    private JSONObject json;

    public RESTUtil(Configuration configuration) {
        _configuration = configuration;
        _logger = Logger.getLogger(PageDriver.class);
    }

    private void get(String resource, String contentType, String accept, String authorization) throws Exception {
        request = new HttpGet(_configuration.BaseURI + resource);
        if (contentType != null)
            request.setHeader("Content-Type", contentType);
        if (accept != null)
            request.setHeader("Accept", accept);
        if (authorization != null)
            request.setHeader("Authorization", authorization);
        request.addHeader("User-Agent", "USER_AGENT");
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

    public void getJSONArray(String resource) throws Exception {
        get(resource, null, "application/json", null);
        String json = IOUtils.toString(response.getEntity().getContent());
        jsonArray = new JSONArray(json);
    }

    public void getJSONEntity(String resource) throws Exception {
        get(resource, null, "application/json", null);
        String json = IOUtils.toString(response.getEntity().getContent());
        this.json = new JSONObject(json);
    }

    public String parseJsonObject(String mKey)
    {
        String value = null;
        try {
            value = json.get(mKey).toString();
        } catch (JSONException e) {
           _logger.error(e);
        }
        return value;
    }
    public boolean isValidResponse() {
        return (response != null);
    }

    public int getStatusCode() {
        return response.getStatusLine().getStatusCode();
    }

    public String getContentType() {
        return getHeader("Content-Type");
    }

    public int getArrayCount() {
        return jsonArray.length();
    }

    public String getContentLength()
    {
        return getHeader("Content-length") ;
    }

    public String getLocale() { return response.getLocale().toString();}

    public String getServer() { return getHeader("Server");}


}
