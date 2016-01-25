package ru.diaproject.vkplus.imageloading;

import android.graphics.Bitmap;
import android.widget.ImageView;

import ru.diaproject.vkplus.core.executor.VKMainExecutor;

public interface ILoadManager {
     ILoadManager withPlaceholder(Integer placeholderId);

     ILoadManager withError(Integer errorId);

    ILoadManager withListener(VKMainExecutor.VKTask.ITaskListener<Bitmap> listener);

    ILoadManager load(String url);

    void into(ImageView view);
}
