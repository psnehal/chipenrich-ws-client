package org.ncibi.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

public final class HttpPostMultiPartFormRequestMethod<T> extends AbstractPostRequestMethod<T>
{
    public HttpPostMultiPartFormRequestMethod(ResponseHandler<Response<T>> responseHandler,
            HttpRequestRetryHandler retryHandler)
    {
        super(responseHandler, retryHandler);
    }

    @Override
    protected HttpEntity createEntityFromArguments(List<NameValuePair> arguments)
    {
        MultipartEntity entity = new MultipartEntity();
        addFilesToMultipartEntity(entity, arguments);
        return entity;
    }

    private void addFilesToMultipartEntity(MultipartEntity entity, List<NameValuePair> arguments)
    {
        for (NameValuePair valuePair : arguments)
        {
            if (isFileArgument(valuePair.getName()))
            {
                String filepath = valuePair.getValue();
                entity.addPart(valuePair.getName(), createInputStreamBodyFromFileHandleException(filepath));
            }
            else
            {
                entity.addPart(valuePair.getName(), newStringBody(valuePair.getValue()));
            }
        }
    }

    private boolean isFileArgument(String argument)
    {
        /*
         * Arguments that are filenames start with filename_, which is 9
         * characters.
         */
        if (argument.length() > 5)
        {
            return "file:".equals(argument.subSequence(0, 5));
        }

        return false;
    }

    private StringBody newStringBody(String value)
    {
        StringBody sb = null;

        try
        {
            sb = new StringBody(value);
        }
        catch (UnsupportedEncodingException e)
        {

        }
        
        return sb;

    }

    private InputStreamBody createInputStreamBodyFromFileHandleException(String filepath)
    {
        try
        {
            return createInputStreamBodyFromFile(filepath);
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException("Unable to read file: " + filepath);
        }
    }

    private InputStreamBody createInputStreamBodyFromFile(String filepath) throws FileNotFoundException
    {
        File f = new File(filepath);
        FileInputStream fileStream = new FileInputStream(f);
        InputStreamBody streamBody = new InputStreamBody(fileStream, "application/octet-stream", f.getName());
        return streamBody;
    }
}
