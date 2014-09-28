package com.polidea.stackoverflow.api.stackoverflow.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.polidea.stackoverflow.listeners.ConnectionListener;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;


public class ApiAsyncTask extends AsyncTask<ApiParams, Void, Void> {

    private static final String TAG = "ServerAsyncTask";

    ConnectionListener connectionListener;
    public ApiAsyncTask(ConnectionListener connectionListener){
        this.connectionListener = connectionListener;
    }
	
	@Override
	protected Void doInBackground(ApiParams... params) {


		HttpClient httpClient = new DefaultHttpClient();
        ApiParams apiParams = params[0];

        HttpUriRequest request = new HttpGet(apiParams.getUrl());
        request.addHeader("Accept-Encoding","gzip");
        try{
            HttpResponse httpResponse = httpClient.execute(request);
            InputStream inputStream = httpResponse.getEntity().getContent();
            Header contentEncoding = httpResponse.getFirstHeader("Content-Encoding");
            if(contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")){
                inputStream = new GZIPInputStream(inputStream);
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, HTTP.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder result = new StringBuilder();
            String tempStr;
            while ((tempStr = bufferedReader.readLine())!=null){
                result.append(tempStr);
            }

            if(apiParams.getParser()!=null){
                apiParams.getParser().parse(result.toString());
            }else{
                Log.d(TAG,"Response: "+result);
            }

        } catch (ClientProtocolException cpe){
            if(connectionListener!=null)
                connectionListener.onErrorConnection("Internet connection problem occurred, or server is temporarily not available");
        } catch (IOException ioe){
            if(connectionListener!=null)connectionListener.onErrorConnection("Internet connection problem occurred, or server is temporarily not available");
        }

		return null;
	}
}
