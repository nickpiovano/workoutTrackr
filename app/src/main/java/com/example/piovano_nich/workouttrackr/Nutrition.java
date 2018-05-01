package com.example.piovano_nich.workouttrackr;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class Nutrition extends Activity {
    private EditText urlText;
    private Button goButton;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // ...
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition);

        // Get a handle to all user interface elements
        webView = (WebView) findViewById(R.id.webViewNutrition);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.nutrition.gov/");
    }


    //the back key navigates back to the previous web page
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
