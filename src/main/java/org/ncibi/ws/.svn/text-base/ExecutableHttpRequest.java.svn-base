package org.ncibi.ws;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

public interface ExecutableHttpRequest<T>
{
    public Response<T> execute(String url, List<NameValuePair> arguments) throws ClientProtocolException, IOException;
    public void setProxy(WebServiceProxy proxy);
    public void close();
}
