package ru.diaproject.vkplus.core.executor;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.utils.Utils;


public class VKBitmapTask extends VKMainExecutor.VKTask implements Runnable {
    private static int configWidth = 0;
    private static void initConfigWidth(Context context){
        configWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    private ITaskListener listener;
    private Context context;
    private String url;
    private String md5;
    private String filePath;
    private WeakReference<ImageView> imageViewWeakReference;

    public VKBitmapTask(Context context){
        this.context = context;
    }

    public VKBitmapTask(Context context, final ImageView view, String url, ITaskListener listener){
        this(context);
        setUrl(url);
        this.listener = listener;
        this.imageViewWeakReference = new WeakReference<>(view);
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setImageResource(R.drawable.picture_placeholder);
            }
        });

        if (configWidth == 0)
            initConfigWidth(this.context);
    }
    @Override
    public void run() {
        Bitmap bitmap = null;
        try {
            isStarted = true;
            bitmap = checkBitmapInCache();

            if (bitmap == null){
                File file = new File(filePath);

                if(!file.exists()) {
                    filePath = Utils.downloadFile(context, url, md5);
                    status = VKDownloadStatus.COMPLETED;
                }else
                    status = VKDownloadStatus.NO_NEED;

                bitmap = processFile(file);
            }
            else status = VKDownloadStatus.NO_NEED;

        } catch (Throwable e) {
            status = VKDownloadStatus.FAILED;

            e.printStackTrace();
            listener.onError(e);
        } finally {
            VKMainExecutor.INSTANCE.executingTasks.remove(id);
            isStarted = false;
            if (bitmap!=null){
                VKMainExecutor.INSTANCE.cacheBitmap.put(md5, bitmap);
                final ImageView view = imageViewWeakReference.get();
                if (view!=null) {
                    final Bitmap finalBitmap = bitmap;
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(finalBitmap);
                        }
                    });
                }
            }
        }
    }

    public Bitmap checkBitmapInCache(){
        return VKMainExecutor.INSTANCE.cacheBitmap.get(md5);
    }

    protected Bitmap processFile(File file) {

        Bitmap bitmap = Utils.processBitmap(
                file.getAbsolutePath(),
                Bitmap.Config.RGB_565);

            FileOutputStream fileOutputStream = null;

            try {
                fileOutputStream = new FileOutputStream(file, false);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                try {
                    if(fileOutputStream != null)
                        fileOutputStream.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        return bitmap;
    }

    public void setUrl(String url){
        this.url = url;
        this.md5 = Utils.md5(url);
        this.filePath = Utils.getCachePath(context) + md5;
    }

    public void setListener(ITaskListener listener){
        this.listener = listener;
    }

    public void setImageView(ImageView view){
        this.imageViewWeakReference = new WeakReference<>(view);
    }
}
