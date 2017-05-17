package org.ncibi.ws;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.protocol.HTTP;

public final class HttpPostRequestMethod<T> extends AbstractPostRequestMethod<T>
{    
    public HttpPostRequestMethod(ResponseHandler<Response<T>> responseHandler, HttpRequestRetryHandler retryHandler)
    {
        super(responseHandler, retryHandler);
    }
    
    @Override
    protected HttpEntity createEntityFromArguments(List<NameValuePair> arguments)
    {
        try
        {
            return new UrlEncodedFormEntity(arguments, HTTP.UTF_8);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new IllegalArgumentException("Unable to parse arguments.");
        }
    }
}
