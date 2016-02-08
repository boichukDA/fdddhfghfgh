package ru.diaproject.vkplus.core.executor;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import ru.diaproject.vkplus.VKPlusApplication;
import ru.diaproject.vkplus.model.users.IDataObject;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;

public enum VKMainExecutor {
    INSTANCE;

    public static <T extends IDataObject> void executeVKQuery( VKQuery<T> query,
                                                             VKTask.ITaskListener<T> listener){
        VKMainExecutor.INSTANCE.executeQuery(
                new VKQueryTask<>(VKPlusApplication.getStaticContext(), query,listener));
    }

    public static void executeVKQuery(VKBitmapTask task) {
        VKMainExecutor.INSTANCE.executeQuery(task);
    }

    public static <T extends IDataObject> Future<T> request(VKQuery<T> query){
       return  VKMainExecutor.INSTANCE.executeQuery(
               new VKCallableQueryTask<>(VKPlusApplication.getStaticContext(), query));
    }


    public static <T extends IDataObject> void executeVKQuery(VKQuery<T> query,
                                                             VKTask.ITaskListener<T> listener, CountDownLatch latch){
        VKMainExecutor.INSTANCE.executeQuery(
                new VKQueryTask<>(VKPlusApplication.getStaticContext(), query, listener, latch));
    }

    public static void executeRunnable(Runnable runnable){
        INSTANCE.execute(runnable);
    }
    private static final int MAX_TASK_COUNT = 100;

    public static ThreadPoolExecutor getExecutor() {
        return VKMainExecutor.INSTANCE.localThreadPool;
    }

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

    final int CACHE_BITMAP_SIZE = (int)(Runtime.getRuntime().maxMemory() / 8192f);

    public LruCache<String, Bitmap> cacheBitmap = new LruCache<String, Bitmap>(CACHE_BITMAP_SIZE) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return (int) ((bitmap.getRowBytes() * bitmap.getHeight()) / 1024f);
        }

       /* @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            oldValue.recycle();
        }*/
    };

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

    public void executeQuery(VKBitmapTask query){
        executingTasks.add(query.id);
        networkThreadPool.execute(query);
    }

    public <T extends IDataObject> Future<T> executeQuery(VKCallableQueryTask<T> query){
        executingTasks.add(query.id);
        Future<T> future = networkThreadPool.submit(query);
        return future;
    }

    public void execute (Runnable runnable){
        localThreadPool.submit(runnable);
    }
}
