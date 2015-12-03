package ru.diaproject.vkplus.core.executor;

public class SimpleTaskListener<T> implements VKMainExecutor.VKTask.ITaskListener<T> {

    @Override
    public void onDone(T result) {
    }

    @Override
    public void onError(Throwable e) {
    }
}