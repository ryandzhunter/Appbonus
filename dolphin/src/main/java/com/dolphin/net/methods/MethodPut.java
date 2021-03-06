package com.dolphin.net.methods;


import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


/**
 * Created at 04.12.13 19:45
 *
 * @author Altero
 */
public class MethodPut extends BaseHttpMethod {

    private static final String REQUEST_METHOD = "PUT";

    public MethodPut(String hostUri, String json, String... apiPath) {

        super(hostUri, json, apiPath);
        requestMethod = REQUEST_METHOD;
    }

    public MethodPut(String hostUri, JSONObject json, String... apiPath) {

        super(hostUri, json.toString(), apiPath);
        requestMethod = REQUEST_METHOD;
    }

    @Override
    protected void configureConnection(URL postUrl) throws IOException {

        super.configureConnection(postUrl);
        this.connection.setRequestProperty("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
        this.connection.setDoInput(true);
        this.connection.setDoOutput(true);
    }
}
