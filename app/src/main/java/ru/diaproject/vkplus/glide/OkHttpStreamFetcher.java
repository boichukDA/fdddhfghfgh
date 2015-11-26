package ru.diaproject.vkplus.glide;

import android.support.v7.util.AsyncListUtil;
import android.util.Log;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.bumptech.glide.util.ContentLengthInputStream;
import  com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

public class OkHttpStreamFetcher implements DataFetcher<InputStream> {
    private static final String TAG = "OkHttpFetcher";
    private final OkHttpClient client;
    private final GlideUrl url;
    private InputStream stream;
    private ResponseBody responseBody;

    public OkHttpStreamFetcher(OkHttpClient client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request.Builder requestBuilder = new Request.Builder().url(url.toStringUrl());
        for (Map.Entry<String, String> headerEntry : url.getHeaders().entrySet()) {
            String key = headerEntry.getKey();
            requestBuilder.addHeader(key, headerEntry.getValue());
        }
        Request request = requestBuilder.build();

        client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, "OkHttp failed to obtain result", e);
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    long contentLength = response.body().contentLength();
                    responseBody = response.body();
                    stream = ContentLengthInputStream.obtain(responseBody.byteStream(), contentLength);
                } else if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, "OkHttp got error response: " + response.code() + ", " + response.message());
                }
            }
        });
        return stream;
    }

    @Override
    public void cleanup() {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            // Ignored
        }
        if (responseBody != null) {
            try {
                responseBody.close();
            } catch (IOException e) {
                // Ignored.
            }
        }
    }

    @Override
    public String getId() {
        return url.toString();
    }

    @Override
    public void cancel() {
        // TODO: call cancel on the client when this method is called on a background thread. See #257
    }


}