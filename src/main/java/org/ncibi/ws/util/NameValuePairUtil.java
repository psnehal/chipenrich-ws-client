package org.ncibi.ws.util;

import java.util.Collection;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class NameValuePairUtil
{
    private NameValuePairUtil() {}
    
    public static <T> NameValuePair buildNameValuePairFromCollection(String name, Collection<T> items)
    {
       return buildNameValuePairFromCollectionUsingSeparator(name, items, ",");
    }
    
    public static <T> NameValuePair 
    	buildNameValuePairFromCollectionUsingSeparator(String name, Collection<T> items, String separator)
    {
        NameValuePair pair = new BasicNameValuePair(name, JoinUtils.join(items, separator));
        return pair;
    }

    public static <T> NameValuePair buildNameValuePairFromNameValue(String name, T value)
    {
        NameValuePair pair = new BasicNameValuePair(name, "" + value);
        return pair;
    }
}
