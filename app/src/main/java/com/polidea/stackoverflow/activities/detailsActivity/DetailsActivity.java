package com.polidea.stackoverflow.activities.detailsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.polidea.stackoverflow.R;

/**
 * Created by Hubert on 27.09.2014.
 */
public class DetailsActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        webView = (WebView) findViewById(R.id.webView);

        String url = "";

        Bundle b = getIntent().getExtras();
        if(b!=null){
            url = b.getString("url");
        }

        webView.loadUrl(url);
    }
}
