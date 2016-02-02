package ru.diaproject.vkplus.authorization;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.database.workers.UserWorker;
import ru.diaproject.vkplus.vkcore.VK;


public class VKWebClient extends WebViewClient {
    private final String logName = VKWebClient.this.getClass().getCanonicalName();
    private VK vk = VK.SINGLETON;

    private AuthorizationActivity activity;

    public VKWebClient (AuthorizationActivity activity){
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d(logName, "pageStarted url = " + url);

        String accessToken = null;
        String expiresIn = null;
        String userId = null;
        String secret = null;
        boolean httpsRequired = false;

        if (url.startsWith(vk.getRedirectURl())) {
            try {
                URL urlClass = null;
                try {
                    urlClass = new URL(url);
                } catch (MalformedURLException mFURLEx) {
                }

                String query = urlClass.getRef();
                String[] params = query.split("&");
                for (int i = 0; i < params.length; i++) {
                    if (params[i].contains("access_token")) {
                        accessToken = params[i].split("=")[1];
                    }
                    if (params[i].contains("expires_in")) {
                        expiresIn = params[i].split("=")[1];
                    }
                    if (params[i].contains("user_id")) {
                        userId = params[i].split("=")[1];
                    }
                    if (params[i].contains("secret")) {
                        secret = params[i].split("=")[1];
                    }
                    if (params[i].contains("secret")) {
                        secret = params[i].split("=")[1];
                    }
                    if (params[i].contains("https_required")) {
                        httpsRequired = true;
                    }

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        if (accessToken != null) {
            Log.d(logName, "accessToken = " + accessToken);
            Log.d(logName, "expiresIn = " + expiresIn);
            Log.d(logName, "userId = " + userId);
            Log.d(logName, "secret = " + secret);

            User user = new User.UserBuilder()
                    .setAccessToken(accessToken)
                    .setAccountId(userId)
                    .setSecret(secret)
                    .build();

            UserWorker.createUser(user, httpsRequired);
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        }
    }
}
