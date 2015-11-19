package ru.diaproject.vkplus.core.executor;


import android.annotation.SuppressLint;

public abstract class VKRunnableTask extends VKMainExecutor.VKTask implements Runnable {

    protected abstract  VKDownloadStatus download();

    protected void process(VKDownloadStatus downloadResult) {

    }

    @SuppressLint("LongLogTag")
    public void run() {
        try {
            isStarted = true;
            if( status == null) {
                status = download();
                VKMainExecutor.INSTANCE.localThreadPool.execute(this);
            } else {
                process(status);
                status = VKDownloadStatus.COMPLETED;
            }
        } catch (Exception e) {
            status = VKDownloadStatus.FAILED;
            e.printStackTrace();
            throw e;
        }
        finally{
            VKMainExecutor.INSTANCE.executingTasks.remove(id);
            isStarted = false;

        }
    }
}
