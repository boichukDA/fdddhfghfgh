package ru.diaproject.vkplus.glide;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.squareup.okhttp.OkHttpClient;

import java.io.InputStream;

public  class OkHttpClientFactory implements ModelLoaderFactory<GlideUrl, InputStream> {
    private static volatile OkHttpClient internalClient;
    private OkHttpClient client;

    private static OkHttpClient getInternalClient() {
        if (internalClient == null) {
            synchronized (OkHttpClientFactory.class) {
                if (internalClient == null) {
                    internalClient = new OkHttpClient();
                }
            }
        }
        return internalClient;
    }

    /**
     * Constructor for a new Factory that runs requests using a static singleton client.
     */
    public OkHttpClientFactory() {
        this(getInternalClient());
    }

    /**
     * Constructor for a new Factory that runs requests using given client.
     */
    public OkHttpClientFactory(OkHttpClient client) {
        this.client = client;
    }


    @Override
    public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
        return new OkHttpUrlLoader(client);
    }

    @Override
    public void teardown() {
        // Do nothing, this instance doesn't own the client.
    }
}

