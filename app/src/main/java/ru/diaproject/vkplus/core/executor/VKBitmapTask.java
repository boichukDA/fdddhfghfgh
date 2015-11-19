package ru.diaproject.vkplus.core.executor;

import android.content.Context;
import android.util.Log;

import java.io.File;

import ru.diaproject.vkplus.core.utils.Utils;


public class VKBitmapTask extends VKMainExecutor.VKTask implements Runnable {
    private static int configWidth = 0;
    private static void initConfigWidth(Context context){
        configWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    private final ITaskListener listener;
    private Context context;
    private String url;
    private String md5;
    private String filePath;

    public VKBitmapTask(Context context, String url, ITaskListener listener){
        this.context = context;
        this.url = url;
        this.listener = listener;
        this.md5 = Utils.md5(url);
        this.filePath = Utils.getCachePath(context) + md5;

        if (configWidth == 0)
            initConfigWidth(this.context);
    }
    @Override
    public void run() {
        try {
            isStarted = true;
            String filePath = download();
        /*    T result = process(filePath);
            status = VKDownloadStatus.COMPLETED;

            if( result!= null )
                listener.onDone(result);
*/
        } catch (Throwable e) {
            status = VKDownloadStatus.FAILED;

            e.printStackTrace();
            listener.onError(e);
        } finally {
            VKMainExecutor.INSTANCE.executingTasks.remove(id);
            isStarted = false;
        }
    }

    private String download() {
        File file = new File(filePath);

        if(!file.exists())
           filePath =Utils.downloadFile(context, url, md5);

        return filePath;
    }
}
