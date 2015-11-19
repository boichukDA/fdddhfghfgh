package ru.diaproject.vkplus.core.executor;


import android.content.Context;

import java.util.concurrent.CountDownLatch;

import ru.diaproject.vkplus.core.VKDataCore;
import ru.diaproject.vkplus.core.utils.Utils;
import ru.diaproject.vkplus.core.utils.VKDataParser;
import ru.diaproject.vkplus.core.utils.json.VKJsonDataParser;
import ru.diaproject.vkplus.core.utils.xml.VKXmlDataParser;
import ru.diaproject.vkplus.news.model.Response;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;

public class VKQueryTask<T extends VKDataCore> extends VKMainExecutor.VKTask implements Runnable {

    private VKQuery query;
    private Context context;
    private final ITaskListener listener;
    private CountDownLatch latch;

    public VKQueryTask(Context context, VKQuery query, ITaskListener listener){
        this.query = query;
        this.context = context;
        this.listener = listener;
    }

    public VKQueryTask(Context context, VKQuery query, ITaskListener listener, CountDownLatch latch){
        this (context, query, listener);
        this.latch = latch;
    }

    protected String download() {
        String res = Utils.downloadFile(context, query.getQuery(), Utils.md5(query.getQuery()));
        return res;
    }

    protected T process(String filePath) throws Exception {
        VKDataParser<Response> parser;
         if (query.getResponseType().equals(VKQueryResponseTypes.XML))
             parser = new VKXmlDataParser<>(filePath, query.getResultType());
        else
         parser = new VKJsonDataParser<>(filePath, query.getResultType());
        T result = (T) parser.parse();
        return result;
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

        e.printStackTrace();
    } finally {
        VKMainExecutor.INSTANCE.executingTasks.remove(id);
        isStarted = false;
            if (latch!=null)
                latch.countDown();
    }
    }
}
