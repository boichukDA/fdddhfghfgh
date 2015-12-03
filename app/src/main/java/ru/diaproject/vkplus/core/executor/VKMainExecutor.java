package ru.diaproject.vkplus.core.executor;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import ru.diaproject.vkplus.core.VKDataCore;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;

public enum VKMainExecutor {
    INSTANCE;

    public static <T extends VKDataCore> void executeVKQuery(Context context,
                                                             VKQuery<T> query,
                                                             VKTask.ITaskListener<T> listener){
        VKMainExecutor.INSTANCE.executeQuery(new VKQueryTask(context, query,listener));
    }

    public static <T extends VKDataCore> void executeVKQuery(Context context,
                                                             VKQuery<T> query,
                                                             VKTask.ITaskListener<T> listener, CountDownLatch latch){
        VKMainExecutor.INSTANCE.executeQuery(new VKQueryTask(context, query,listener, latch));
    }

    public static void executeRunnable(Runnable runnable){
        INSTANCE.execute(runnable);
    }
    private static final int MAX_TASK_COUNT = 100;
    public static abstract class VKTask {

        public interface ITaskListener<T>{
            void onDone(T result);

            void onError(Throwable e);
        }

        private static Integer counter = 0;
        private static Integer idGenerator(){
            return counter++;
        }

        protected VKDownloadStatus status;
        protected volatile boolean isStarted;
        protected AtomicBoolean isCancel;
        protected  Integer id;

        protected VKTask() {
            id = idGenerator();

            isStarted = false;
            isCancel = new AtomicBoolean(false);
        }
        public boolean isCancelled() {
            return isCancel.get();
        }

        public boolean isDone() {
            return status == VKDownloadStatus.COMPLETED;
        }
        protected final void setStatus(VKDownloadStatus status) {
            this.status = status;
        }
    }

    private final BlockingQueue<Runnable> networkQueue;
    private final BlockingQueue<Runnable> localQueue;

    protected final ThreadPoolExecutor networkThreadPool;
    protected final ThreadPoolExecutor localThreadPool;

    protected final List<Integer> executingTasks;

    final int CORES = Runtime.getRuntime().availableProcessors();
    final int THREAD_POOL_SIZE_NETWORK = CORES + 1;
    final int THREAD_POOL_SIZE_NETWORK_MAX = CORES * 2 + 1;
    final long KEEP_ALIVE_VALUE = 1;
    final TimeUnit KEEP_ALIVE_VALUE_TIME_UNIT = TimeUnit.SECONDS;

    VKMainExecutor(){
        networkQueue = new LinkedBlockingQueue<>(MAX_TASK_COUNT);
        localQueue = new LinkedBlockingQueue<>(MAX_TASK_COUNT);

        networkThreadPool = new ThreadPoolExecutor(
                THREAD_POOL_SIZE_NETWORK,
                THREAD_POOL_SIZE_NETWORK_MAX,
                KEEP_ALIVE_VALUE,
                KEEP_ALIVE_VALUE_TIME_UNIT,
                networkQueue
        );
        localThreadPool = new ThreadPoolExecutor(
                CORES,
                CORES,
                KEEP_ALIVE_VALUE,
                KEEP_ALIVE_VALUE_TIME_UNIT,
                localQueue
        );

        executingTasks = new ArrayList<>();
    }

    public void executeQuery(VKQueryTask query){
        executingTasks.add(query.id);
        networkThreadPool.execute(query);
    }

    public void execute (Runnable runnable){
        localThreadPool.submit(runnable);
    }
}
