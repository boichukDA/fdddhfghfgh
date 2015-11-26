package ru.diaproject.vkplus.glide;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.squareup.okhttp.OkHttpClient;

import java.io.InputStream;

public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    private final OkHttpClient client;

    public OkHttpUrlLoader(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
        return  new OkHttpStreamFetcher(client, model);
    }
}
