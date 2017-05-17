package org.ncibi.ws;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.ncibi.ws.util.UrlBuilder;

public final class HttpGetRequestMethod<T> extends AbstractHttpRequestMethod<T>
{
    private UrlBuilder urlBuilder;
    
    public HttpGetRequestMethod(ResponseHandler<Response<T>> responseHandler, HttpRequestRetryHandler retryHandler)
    {
        super(responseHandler, retryHandler);
    }
    
    @Override
    protected HttpUriRequest createRequest(String url, List<NameValuePair> arguments)
    {
        String fullUrl = buildFullUrlFromUrlAndArguments(url, arguments);
        HttpGet get = new HttpGet(fullUrl);
        return get;
    }

    private String buildFullUrlFromUrlAndArguments(String url, List<NameValuePair> arguments)
    {
        urlBuilder = new UrlBuilder(url);
        addArgumentsToUrlBuilder(arguments);
        
        return urlBuilder.toString();
    }
    
    private void addArgumentsToUrlBuilder(List<NameValuePair> arguments)
    {
        for (NameValuePair value : arguments)
        {
            urlBuilder.addArgumentNameValue(value.getName(), value.getValue());
        }
    }
}
