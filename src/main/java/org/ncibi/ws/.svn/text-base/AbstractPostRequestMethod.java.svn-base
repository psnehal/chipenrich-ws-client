package org.ncibi.ws;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

abstract class AbstractPostRequestMethod<T> extends AbstractHttpRequestMethod<T>
{
    protected abstract HttpEntity createEntityFromArguments(List<NameValuePair> arguments);

    public AbstractPostRequestMethod(ResponseHandler<Response<T>> responseHandler,
            HttpRequestRetryHandler retryHandler)
    {
        super(responseHandler, retryHandler);
    }
    
    @Override
    protected HttpUriRequest createRequest(String url, List<NameValuePair> arguments)
    {
        HttpPost post = new HttpPost(url);
        HttpEntity entity = createEntityFromArguments(arguments);
        post.setEntity(entity);
        return post;
    }
}
