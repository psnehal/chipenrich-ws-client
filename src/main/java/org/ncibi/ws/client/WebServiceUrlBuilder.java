package org.ncibi.ws.client;

public class WebServiceUrlBuilder
{
    private final ServerUrlConfiguration urlConfig;
    
    public WebServiceUrlBuilder()
    {
        urlConfig = new ServerUrlConfiguration();
    }
    
    public WebServiceUrlBuilder(String configFileName)
    {
    	urlConfig = new ServerUrlConfiguration(configFileName);
    }
    
    public String toServiceUrl(String server, String service)
    {
        String serverUrl = urlConfig.urlForServerService(server, service);
        if (! serverUrl.endsWith("/"))
        {
            serverUrl += "/";
        }
        return serverUrl + service;
    }
}
