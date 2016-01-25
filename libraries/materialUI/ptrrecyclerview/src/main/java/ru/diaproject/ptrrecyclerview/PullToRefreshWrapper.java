package ru.diaproject.ptrrecyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class PullToRefreshWrapper {
    private static final int EMPTY_LIST_ITEMS_COUNT = 0;
    private static final int DEFAULT_LIMIT = 50;
    private static final int MAX_ATTEMPTS_TO_RETRY_LOADING = 3;

    public static <T> Observable<T> paging(RecyclerView recyclerView, PagingListener<T> pagingListener) {
        return paging(recyclerView, pagingListener, DEFAULT_LIMIT, EMPTY_LIST_ITEMS_COUNT, MAX_ATTEMPTS_TO_RETRY_LOADING, BackgroundExecutor.getSafeBackgroundExecutor());
    }

    public static <T> Observable<T> paging(RecyclerView recyclerView, PagingListener<T> pagingListener, int limit) {
        return paging(recyclerView, pagingListener, limit, EMPTY_LIST_ITEMS_COUNT, MAX_ATTEMPTS_TO_RETRY_LOADING,BackgroundExecutor.getSafeBackgroundExecutor());
    }

    public static <T> Observable<T> paging(RecyclerView recyclerView, PagingListener<T> pagingListener, int limit, int emptyListCount) {
        return paging(recyclerView, pagingListener, limit, emptyListCount, MAX_ATTEMPTS_TO_RETRY_LOADING, BackgroundExecutor.getSafeBackgroundExecutor());
    }

    public static <T> Observable<T> paging(RecyclerView recyclerView, final PagingListener<T> pagingListener, int limit, int emptyListCount, final int retryCount, Executor executor) {
        if (recyclerView == null) {
            throw new PagingException("null recyclerView");
        }
        if (recyclerView.getAdapter() == null) {
            throw new PagingException("null recyclerView adapter");
        }
        if (limit <= 0) {
            throw new PagingException("limit must be greater then 0");
        }
        if (emptyListCount < 0) {
            throw new PagingException("emptyListCount must be not less then 0");
        }
        if (retryCount < 0) {
            throw new PagingException("retryCount must be not less then 0");
        }

        final int startNumberOfRetryAttempt = 0;
        return getScrollObservable(recyclerView, limit, emptyListCount)
                .subscribeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .observeOn(Schedulers.from(executor))
                .switchMap(new Func1<Integer, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(Integer integer) {
                        return getPagingObservable(pagingListener, pagingListener.onNextPage(integer), startNumberOfRetryAttempt, integer, retryCount);
                    }
                });
    }

    private static Observable<Integer> getScrollObservable(final RecyclerView recyclerView, final int limit, final int emptyListCount) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                final RecyclerView.OnScrollListener sl = new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        if (!subscriber.isUnsubscribed()) {
                            int position = getLastVisibleItemPosition(recyclerView);
                            int updatePosition = recyclerView.getAdapter().getItemCount() - 1 - (limit / 2);
                            if (position >= updatePosition) {
                                subscriber.onNext(recyclerView.getAdapter().getItemCount());
                            }
                        }
                    }
                };
                recyclerView.addOnScrollListener(sl);
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        recyclerView.removeOnScrollListener(sl);
                    }
                }));
                if (recyclerView.getAdapter().getItemCount() == emptyListCount) {
                    subscriber.onNext(recyclerView.getAdapter().getItemCount());
                }
            }
        });
    }

    private static int getLastVisibleItemPosition(RecyclerView recyclerView) {
        Class recyclerViewLMClass = recyclerView.getLayoutManager().getClass();
        if (recyclerViewLMClass == LinearLayoutManager.class || LinearLayoutManager.class.isAssignableFrom(recyclerViewLMClass)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            return linearLayoutManager.findLastVisibleItemPosition();
        } else if (recyclerViewLMClass == StaggeredGridLayoutManager.class || StaggeredGridLayoutManager.class.isAssignableFrom(recyclerViewLMClass)) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager)recyclerView.getLayoutManager();
            int[] into = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
            List<Integer> intoList = new ArrayList<>();
            for (int i : into) {
                intoList.add(i);
            }
            return Collections.max(intoList);
        }
        throw new PagingException("Unknown LayoutManager class: " + recyclerViewLMClass.toString());
    }

    private static <T> Observable<T> getPagingObservable(final PagingListener<T> listener, Observable<T> observable, final int numberOfAttemptToRetry, final int offset, final int retryCount) {
        return observable.onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                if (numberOfAttemptToRetry < retryCount) {
                    int attemptToRetryInc = numberOfAttemptToRetry + 1;
                    return getPagingObservable(listener, listener.onNextPage(offset), attemptToRetryInc, offset, retryCount);
                } else {
                    return Observable.empty();
                }
            }
        });
    }

}
