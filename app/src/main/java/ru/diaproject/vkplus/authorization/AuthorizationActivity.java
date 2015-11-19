package ru.diaproject.vkplus.authorization;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivity;
import ru.diaproject.vkplus.vkcore.VK;

public class AuthorizationActivity extends ParentActivity {
    private final String logName = AuthorizationActivity.this.getClass().getCanonicalName();
    private WebView webView;

    @Override
    protected void initContext(Bundle savedInstanceState) {

    }

    @Override
    protected void initBackend(Bundle savedInstanceState) {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initUI(Bundle savedInstanceState) {
        hideTopbar();

        setContentView(R.layout.authorization_layout);
         CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        //cookieManager.setAcceptCookie(false);

        webView = (WebView) findViewById(R.id.authorization_web_view);
       /* webView.getSettings().setSaveFormData(false);
        webView.getSettings().setJavaScriptEnabled(true);*/
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(false);
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                    }
                    break;
                }
                return false;
            }
        });
        webView.setWebViewClient(new VKWebClient(this));
        webView.loadUrl(VK.SINGLETON.getAuthorizationUrl());

    }

    @Override
    protected String getLogName() {
        return logName;
    }
}
