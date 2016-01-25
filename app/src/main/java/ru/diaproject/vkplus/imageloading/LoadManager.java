package ru.diaproject.vkplus.imageloading;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import ru.diaproject.vkplus.core.executor.VKBitmapTask;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;

public class LoadManager implements ILoadManager{
    private Context context;
    private String url;
    private Integer placeholder;
    private Integer error;
    private VKMainExecutor.VKTask.ITaskListener<Bitmap> listener;

    public LoadManager(Context context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public ILoadManager withPlaceholder(Integer placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    @Override
    public ILoadManager withError(Integer errorId) {
        this.error = errorId;
        return this;
    }

    @Override
    public ILoadManager withListener(VKMainExecutor.VKTask.ITaskListener<Bitmap> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public ILoadManager load(String url) {
        this.url = url;
        return this;
    }

    @Override
    public void into(ImageView view) {
        VKBitmapTask task = new VKBitmapTask(context);
        task.setUrl(url);
        task.setImageView(view);
        task.setListener(listener);
        VKMainExecutor.executeVKQuery(task);

    }
}
