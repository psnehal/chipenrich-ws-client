package org.ncibi.ws.client;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.ncibi.ws.BeanXMLResponseHandlerFactory;
import org.ncibi.ws.DefaultHttpRequestRetryHandler;
import org.ncibi.ws.ExecutableHttpRequest;
import org.ncibi.ws.HttpGetRequestMethod;
import org.ncibi.ws.HttpPostMultiPartFormRequestMethod;
import org.ncibi.ws.HttpPostRequestMethod;
import org.ncibi.ws.HttpRequestType;
import org.ncibi.ws.Response;
import org.ncibi.ws.ResponseStatus;
import org.ncibi.ws.WebServiceProxy;

public class BeanXMLWebServiceClient<T>
{
    private ExecutableHttpRequest<T> httpRequest;

    public BeanXMLWebServiceClient(HttpRequestType requestType)
    {
    	
        httpRequest = createHttpRequestForRequestType(requestType);
       
    }

    public ExecutableHttpRequest<T> createHttpRequestForRequestType(HttpRequestType requestType)
    {
    	
        switch (requestType)
        {
            case GET:
                return createGetHttpRequest();
            case POST:
                return createPostHttpRequest();
            case MULTI_PART_POST:
                return createMultipartFormPostHttpRequest();
            default:
                return null; // can never happen.
        }
    }

    private ExecutableHttpRequest<T> createGetHttpRequest()
    {
    	
        ResponseHandler<Response<T>> responseHandler = BeanXMLResponseHandlerFactory
                .newMetabolicResponseHandler();
        return new HttpGetRequestMethod<T>(responseHandler, new DefaultHttpRequestRetryHandler());
    }

    private ExecutableHttpRequest<T> createPostHttpRequest()
    {
    	
        ResponseHandler<Response<T>> responseHandler = BeanXMLResponseHandlerFactory
                .newMetabolicResponseHandler();
        
        return new HttpPostRequestMethod<T>(responseHandler, new DefaultHttpRequestRetryHandler());
    }

    private ExecutableHttpRequest<T> createMultipartFormPostHttpRequest()
    {
    	
        ResponseHandler<Response<T>> responseHandler = BeanXMLResponseHandlerFactory
                .newMetabolicResponseHandler();
        return new HttpPostMultiPartFormRequestMethod<T>(responseHandler,
                new DefaultHttpRequestRetryHandler());
    }

    public void setProxy(WebServiceProxy proxy)
    {
        httpRequest.setProxy(proxy);
    }

    public Response<T> executeRequest(String url, List<NameValuePair> arguments)
    {
        try
        {
        	
            return httpRequest.execute(url, arguments);
        }
        catch (ClientProtocolException e)
        {
          return createErrorResponse(e);
       }
        catch (IOException e)
        {
            return createErrorResponse(e);
        }
    }

    public Response<T> executeRequest(String url)
    {
    	List<NameValuePair> arguments = Collections.emptyList();
        return executeRequest(url, arguments);
    }

    private Response<T> createErrorResponse(Exception e)
    {
        ResponseStatus rs = new ResponseStatus(null, false, e.getMessage());
        Response<T> r = new Response<T>(rs, null);
        return r;
    }

    public void close()
    {
        httpRequest.close();
    }
}
