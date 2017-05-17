package org.ncibi.ws;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

abstract class AbstractHttpRequestMethod<T> implements ExecutableHttpRequest<T>
{
    protected abstract HttpUriRequest createRequest(String url, List<NameValuePair> arguments);
    
    private final DefaultHttpClient httpClient;
    private final ResponseHandler<Response<T>> responseHandler;
    
    public AbstractHttpRequestMethod(ResponseHandler<Response<T>> responseHandler, HttpRequestRetryHandler retryHandler)
    {
        this.responseHandler = responseHandler;
        this.httpClient = new DefaultHttpClient();
        this.httpClient.setHttpRequestRetryHandler(retryHandler);
    }
    
    public AbstractHttpRequestMethod(WebServiceProxy proxy, ResponseHandler<Response<T>> responseHandler, HttpRequestRetryHandler retryHandler)
    {
        this(responseHandler, retryHandler);
        setProxy(proxy);
    }
    
    @Override
    public void setProxy(WebServiceProxy proxy)
    {
        if (proxy != null)
        {
            HttpHost hostProxy = new HttpHost(proxy.getHost(), proxy.getPort(), proxy.getProtocol());
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, hostProxy);
        }
    }
    
    @Override
    public Response<T> execute(String url, List<NameValuePair> arguments) throws ClientProtocolException, IOException
    {
        HttpUriRequest uriRequest = createRequest(url, arguments);
        return httpClient.execute(uriRequest, responseHandler);
    }
    
    @Override
    public void close()
    {
        this.httpClient.getConnectionManager().shutdown();
    }
}
