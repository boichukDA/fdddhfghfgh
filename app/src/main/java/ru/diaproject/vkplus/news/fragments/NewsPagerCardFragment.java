package ru.diaproject.vkplus.news.fragments;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.util.ViewPreloadSizeProvider;

import ru.diaproject.ptrrecyclerview.PagingListener;
import ru.diaproject.ptrrecyclerview.PullToRefreshWrapper;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ILoggable;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.news.NewsVariant;
import ru.diaproject.vkplus.news.adapters.NewsMapBindAdapter;
import ru.diaproject.vkplus.news.model.NewsResponse;
import ru.diaproject.vkplus.news.viewholders.FriendItemViewHolder;
import ru.diaproject.vkplus.news.viewholders.PhotoItemViewHolder;
import ru.diaproject.vkplus.news.viewholders.PhotoTagItemViewHolder;
import ru.diaproject.vkplus.news.viewholders.PostItemViewHolder;
import ru.diaproject.vkplus.news.viewholders.WallPhotoItemViewHolder;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.customs.VKApi;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class NewsPagerCardFragment extends Fragment implements ILoggable{

    private static final String ARG_USER = "user";
    private static final String ARG_VARIANT = "variant";
    private static final String STORE_IMAGES = "store_images";
    private static final String STORE_CUR_POSITION = "store_cur_pos";

    private static final int LIMIT = 50;

    private User user;
    private NewsVariant variant;
    private View rootView;
    private NewsResponse response;
    private NewsMapBindAdapter adapter;
    private Subscription pagingSubscription;

    private RecyclerView listView;
    private SwipeRefreshLayout refreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getArguments().getSerializable(ARG_USER);
        variant = getArguments().getParcelable(ARG_VARIANT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initUI(inflater, container);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof PhotoItemViewHolder)
                    ((PhotoItemViewHolder) holder).clear();
                if (holder instanceof PostItemViewHolder)
                    ((PostItemViewHolder) holder).clear();
                if (holder instanceof PhotoTagItemViewHolder)
                    ((PhotoTagItemViewHolder) holder).clear();
                if (holder instanceof WallPhotoItemViewHolder)
                    ((WallPhotoItemViewHolder) holder).clear();
                if (holder instanceof FriendItemViewHolder)
                    ((FriendItemViewHolder) holder).clear();

            }
        });

        initBackend(savedInstanceState);
        return rootView;
    }
    private void initUI(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.news_fragment_card,container,false);

         listView = (RecyclerView) rootView.findViewById(R.id.list);
         refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefreshlayout);

        initColorScheme(user.getColorScheme());
        LinearLayoutManager linearLayoutManager = new PreCachingLayoutManager(getContext(), getResources().getDisplayMetrics().heightPixels);
        listView.setLayoutManager(linearLayoutManager);
        listView.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                initBackend(null);
            }
        });
    }
    protected void initColorScheme(ColorScheme colorScheme){
        listView.setBackgroundColor(colorScheme.getBackgroundColor());
    }
    protected void initBackend(Bundle savedInstanceState) {
        if (savedInstanceState != null){

            response = (NewsResponse) savedInstanceState.getSerializable(STORE_IMAGES);
            int curPos = savedInstanceState.getInt(STORE_CUR_POSITION);
            adapter = new NewsMapBindAdapter(response, NewsPagerCardFragment.this);
            initRecyclerView();

            LinearLayoutManager manager = (LinearLayoutManager) listView.getLayoutManager();
            manager.scrollToPosition(curPos);
            return;
        }

        final VKQuery<NewsResponse> query = createQuery();
        Log.e(getLogName(), query.getQuery());

        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                Observable.from(VKMainExecutor.request(query))
                        .map(new Func1<NewsResponse, NewsResponse>() {
                            @Override
                            public NewsResponse call(NewsResponse response) {
                                if (variant.getFilter() != null)
                                    return response.applyFilter(variant.getFilter());

                                return response;
                            }
                        })
                        .subscribe(new Action1<NewsResponse>() {
                            @Override
                            public void call(NewsResponse totalResponse) {
                                response = totalResponse;

                                adapter = new NewsMapBindAdapter(response, NewsPagerCardFragment.this);

                                ((Activity) getContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initRecyclerView();
                                    }
                                });
                            }
                        });
            }
        });
    }

    private void initRecyclerView(){
    listView.setAdapter(adapter);
    pagingSubscription = PullToRefreshWrapper
        .paging(listView, new PagingListener<NewsResponse>() {
            @Override
            public Observable<NewsResponse> onNextPage(int offset) {
                VKQuery<NewsResponse>  query = createQueryFrom(response.getNextFrom());
                return Observable.from(VKMainExecutor.request( query));
            }
        }, LIMIT).map(new Func1<NewsResponse, NewsResponse>() {
                @Override
                public NewsResponse call(NewsResponse response) {
                    if (variant.getFilter() != null)
                        return response.applyFilter(variant.getFilter());

                    return response;
                }
            }).subscribeOn(Schedulers.from(VKMainExecutor.getExecutor()))
            .subscribe(new Action1<NewsResponse>() {
                @Override
                public void call(NewsResponse totalResult) {

                    final int oldDataCount = response.getListItems().size();
                    final int newDataCount = totalResult.getListItems().size();
                    response.addNewPartData(totalResult);

                    adapter.setData(response);
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemRangeInserted(oldDataCount, newDataCount);
                        }
                    });

                    if ("".equals(response.getNextFrom()))
                        pagingSubscription.unsubscribe();
                }
            });

        refreshLayout.setRefreshing(false);
    }

    private VKQuery<NewsResponse> createQuery(){
        VKPreparedItem<NewsResponse> result;
        if (variant.getSubMethod().equals(VKQuerySubMethod.DEFAULT))
            result = VKApi.news(user).get();
        else result = VKApi.news(user).recommended();

        return result.with(VKParameter.FILTERS, variant.getPostFilter())
                .and(VKParameter.MAX_PHOTOS, "7").build();
    }

    private VKQuery<NewsResponse> createQueryFrom(String from){
        VKPreparedItem<NewsResponse> result;
        if (variant.getSubMethod().equals(VKQuerySubMethod.DEFAULT))
            result = VKApi.news(user).get();
        else result = VKApi.news(user).recommended();

        return result.with(VKParameter.FILTERS, variant.getPostFilter())
                .and(VKParameter.MAX_PHOTOS, "7")
                .and(VKParameter.START_FROM, from).build();
    }

    @Override
    public String getLogName() {
        return getClass().getSimpleName();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragment", "resume");
    }

    public User getUser(){
        return user;
    }

    @Override
    public void onDestroyView() {
        if (pagingSubscription != null && !pagingSubscription.isUnsubscribed()) {
            pagingSubscription.unsubscribe();
        }
        if (listView != null) {
            listView.setAdapter(null);
        }
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STORE_IMAGES, response);
        LinearLayoutManager manager = (LinearLayoutManager) listView.getLayoutManager();

        if (manager!=null)
            outState.putSerializable(STORE_CUR_POSITION, manager.findFirstVisibleItemPosition());
    }
}