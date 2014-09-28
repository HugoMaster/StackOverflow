package com.polidea.stackoverflow.api.stackoverflow.helpers;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class ApiParams {
	public final static String SERVER = "http://api.stackexchange.com/2.2/";

	private String url;
	private Parser parser;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

}
