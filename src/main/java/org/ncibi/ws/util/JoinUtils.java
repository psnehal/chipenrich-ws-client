package org.ncibi.ws.util;

import java.util.Collection;

public class JoinUtils
{
    private JoinUtils() {}
    
    public static <T> String join(Collection<T> listOfValues, String separator)
    {
        StringBuilder sb = new StringBuilder(10000);
        boolean firstTime = true;
        for (T value : listOfValues)
        {
            if (firstTime)
            {
                sb.append("" + value);
                firstTime = false;
            }
            else
            {
                sb.append(separator + value);
            }
        }
        return sb.toString();
    }
}
