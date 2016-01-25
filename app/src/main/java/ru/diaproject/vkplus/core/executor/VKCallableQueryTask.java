package ru.diaproject.vkplus.core.executor;


import android.content.Context;

import java.util.concurrent.Callable;

import ru.diaproject.vkplus.core.utils.Utils;
import ru.diaproject.vkplus.core.utils.json.VKJsonDataParser;
import ru.diaproject.vkplus.news.model.IDataResult;
import ru.diaproject.vkplus.news.model.users.IDataObject;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;

public class VKCallableQueryTask<T extends IDataObject> extends VKMainExecutor.VKTask implements Callable<T>{

    private VKQuery<T> query;
    private Context context;

    public VKCallableQueryTask(Context context, VKQuery<T> query){
        this.query = query;
        this.context = context;
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
    public T call() throws Exception {
        try {
            isStarted = true;
            String filePath = download();
            T result = process(filePath);
            status = VKDownloadStatus.COMPLETED;
            return result;
        } catch (Throwable e) {
            status = VKDownloadStatus.FAILED;
            throw e;
        } finally {
            VKMainExecutor.INSTANCE.executingTasks.remove(id);
            isStarted = false;
        }
    }

}
