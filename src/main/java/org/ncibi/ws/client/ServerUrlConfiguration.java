package org.ncibi.ws.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

class ServerUrlConfiguration
{
	private final String DEFAULT_CLIENT_CONFIGURATION_FILE = "client.properties";
	
    private Properties properties;

    public ServerUrlConfiguration()
    {
        loadPropertiesFile(DEFAULT_CLIENT_CONFIGURATION_FILE);
    }
    
    public ServerUrlConfiguration(String configFileName)
    {
    	loadPropertiesFile(configFileName);
    }

    private void loadPropertiesFile(String configFileName)
    {
        properties = openServerProperties(configFileName);
        if (properties == null)
        {
            throw new IllegalStateException("No client.properties configured.");
        }
    }

    private Properties openServerProperties(String configFileName)
    {
        InputStream in = openServerPropertiesAsInputStream(configFileName);
        return loadProperties(in);
    }

    private InputStream openServerPropertiesAsInputStream(String configFileName)
    {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream in = openStreamFor(configFileName); 
        if (in == null)
        {
            in = classLoader.getResourceAsStream(configFileName);
        }
        return in;
    }
    
    private InputStream openStreamFor(String filename)
    {
        String os = System.getProperty("os.name");
        String propertyFilePath = null;
        if (os.toLowerCase().contains("windows"))
        {
            propertyFilePath = "c:/" + filename; 
        }
        else
        {
            propertyFilePath = "/etc/" + filename;
        }
        
        try
        {
            return new FileInputStream(propertyFilePath); 
        }
        catch (FileNotFoundException e)
        {
            return null;
        }
    }

    private Properties loadProperties(InputStream in)
    {
        Properties p = new Properties();
        p = loadPropertiesCatchingAnyExceptions(p, in);
        return p;
    }

    private Properties loadPropertiesCatchingAnyExceptions(Properties p, InputStream in)
    {
        try
        {
            p.load(in);
        }
        catch (Exception e)
        {
            return null;
        }

        return p;
    }

    public String urlForServerService(String server, String service)
    {
        String url = retrieveServerPropertyValue(server, service);
        return url;
    }

    private String retrieveServerPropertyValue(String server, String service)
    {
        String serverServiceUrl = getUrlForServerServiceFallingBackToDefaults(server, service);
        return serverServiceUrl;
    }

    private String getUrlForServerServiceFallingBackToDefaults(String server, String service)
    {
        String serverServiceUrl = getServerServiceUrl(server, service);

        if (!isValidUrl(serverServiceUrl))
        {
            serverServiceUrl = getServerUrl(server);
            if (!isValidUrl(serverServiceUrl))
            {
                serverServiceUrl = defaultServerUrl();
            }
        }

        return serverServiceUrl;
    }

    private String getServerServiceUrl(String server, String service)
    {
        return properties.getProperty(server + "." + service + "." + "server.url");
    }

    private boolean isValidUrl(String url)
    {
        return url != null;
    }

    private String getServerUrl(String server)
    {
        return properties.getProperty(server + "." + "server.url");
    }

    private String defaultServerUrl()
    {
        String value = properties.getProperty("default.server.url");

        if (value == null)
        {
            throw new IllegalStateException("default.server.url not configured.");
        }

        return value;
    }
}
