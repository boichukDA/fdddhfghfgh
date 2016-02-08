package ru.diaproject.vkplus.core.executor;


import android.content.Context;

import java.util.concurrent.CountDownLatch;

import ru.diaproject.vkplus.core.utils.Utils;
import ru.diaproject.vkplus.core.utils.json.VKJsonDataParser;
import ru.diaproject.vkplus.model.IDataResult;
import ru.diaproject.vkplus.model.users.IDataObject;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;

public class VKQueryTask<T extends IDataObject> extends VKMainExecutor.VKTask implements Runnable {

    private VKQuery<T> query;
    private Context context;
    private final ITaskListener<T> listener;
    private CountDownLatch latch;

    public VKQueryTask(Context context, VKQuery<T> query, ITaskListener<T> listener){
        this.query = query;
        this.context = context;
        this.listener = listener;
    }

    public VKQueryTask(Context context, VKQuery<T>  query, ITaskListener<T> listener, CountDownLatch latch){
        this (context, query, listener);
        this.latch = latch;
    }

    protected String download() {
        String res = Utils.downloadFile(context, query.getQuery(), Utils.md5(query.getQuery()));
        return res;
    }

    protected T process(String filePath) throws Exception {
        VKJsonDataParser<T> parser = new VKJsonDataParser<>(filePath, query.getResultType());
        T dataResult = parser.parse();

        if (dataResult instanceof IDataResult)
            ((IDataResult)dataResult).prepareItems();

        return dataResult;
    }

    @Override
    public void run()  {
        try {
            isStarted = true;
            String filePath = download();
            T result = process(filePath);
            status = VKDownloadStatus.COMPLETED;

            if( result!= null )
                listener.onDone(result);

        } catch (Throwable e) {
            status = VKDownloadStatus.FAILED;
        } finally {
            VKMainExecutor.INSTANCE.executingTasks.remove(id);
            isStarted = false;
                if (latch!=null)
                    latch.countDown();
        }
    }
}
