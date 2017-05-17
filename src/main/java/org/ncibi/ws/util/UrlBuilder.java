package org.ncibi.ws.util;

public class UrlBuilder
{
    private final String urlAddress;
    private final StringBuilder urlArgumentBuilder = new StringBuilder(10000);
    private boolean firstArgument = true;
    
    public UrlBuilder(String urlAddress)
    {
        this.urlAddress = urlAddress;
    }
    
    public void addArgumentNameValue(String name, String value)
    {
        if (firstArgument)
        {
            urlArgumentBuilder.append("?" + name + "=" + value);
            firstArgument = false;
        }
        else
        {
            urlArgumentBuilder.append("&" + name + "=" + value);
        }
    }
    
    @Override
    public String toString()
    {
        return urlAddress + urlArgumentBuilder.toString();
        /*
         * TODO: Below encodes the arguments wrong - I need to figure out how to handle.
         */
//        try
//        {
//            return urlAddress + URLEncoder.encode(urlArgumentBuilder.toString(), "UTF-8");
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            throw new IllegalArgumentException("UTF-8 is not a valid encoding type.");
//        }
    }
    
}
