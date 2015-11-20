package ru.diaproject.vkplus.news.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;


import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ILoggable;
import ru.diaproject.vkplus.core.animations.ResizeAnimation;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.executor.VKQueryTask;
import ru.diaproject.vkplus.news.NewsVariant;
import ru.diaproject.vkplus.news.adapters.NewsMapBindAdapter;
import ru.diaproject.vkplus.news.model.Response;
import ru.diaproject.vkplus.news.viewholders.FriendItemViewHolder;
import ru.diaproject.vkplus.news.viewholders.FriendSubItemViewHolder;
import ru.diaproject.vkplus.news.viewholders.PhotoItemViewHolder;
import ru.diaproject.vkplus.news.viewholders.PhotoTagItemViewHolder;
import ru.diaproject.vkplus.news.viewholders.PostItemViewHolder;
import ru.diaproject.vkplus.news.viewholders.WallPhotoItemViewHolder;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.VKQueryBuilder;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.VkQueryBuilderException;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public class NewsPagerCardFragment extends Fragment implements ILoggable{

    private static final String ARG_USER = "user";
    private static final String ARG_VARIANT = "variant";
    private VKUser user;
    private NewsVariant variant;
    private View rootView;
    private Response response;
    private NewsMapBindAdapter adapter;

    @Bind(R.id.list)
    RecyclerView listView;

    @Bind(R.id.swiperefreshlayout)
    SwipyRefreshLayout refreshLayout;


    private OnFabStateChangeListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getArguments().getParcelable(ARG_USER);
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
        initBackend(true);
        return rootView;
    }
    private void initUI(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.news_fragment_card,container,false);
        ButterKnife.bind(this, rootView);

        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                VKMainExecutor.INSTANCE.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null && !"".equals(response.getNextFrom()))
                            initBackend(false);
                        else
                            ((Activity) getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    refreshLayout.setRefreshing(false);
                                }
                            });
                    }
                });
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(linearLayoutManager);
        listView.addOnScrollListener(new HidingScrollListener());
    }
    protected void initBackend(final boolean isInit) {
        VKQuery query = isInit?createQuery():createQueryFrom(response == null ? "": response.getNextFrom());
        Log.e(getLogName(), query.getQuery());

        VKMainExecutor.INSTANCE.executeQuery(new VKQueryTask(getContext(), query,
                new VKMainExecutor.VKTask.ITaskListener<Response>() {
                    @Override
                    public void onDone(final Response result) {
                        if (isInit) {
                            Response totalResponse;
                            if (variant.getFilter() != null)
                                totalResponse = result.applyFilter(variant.getFilter());
                            else
                                totalResponse = result;

                            response = totalResponse;

                            if (totalResponse.getItems().size() == 0 && !response.getNextFrom().equals(response.getPreviousNextFrom()))
                                initBackend(false);

                            adapter = new NewsMapBindAdapter(response, NewsPagerCardFragment.this);
                            ((Activity) getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setAdapter(adapter);
                                }
                            });
                        } else {
                            if (response != null) {
                                Response totalResult;
                                if (variant.getFilter() != null)
                                    totalResult = result.applyFilter(variant.getFilter());
                                else totalResult = result;

                                if (totalResult.getItems().size() == 0 && !response.getNextFrom().equals(response.getPreviousNextFrom()))
                                    initBackend(false);

                                final int oldDataCount = response.getItems().size();
                                final int newDataCount = totalResult.getItems().size();
                                response.addNewPartData(totalResult);

                                adapter.setData(response);
                                ((Activity) getContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshLayout.setRefreshing(false);
                                        adapter.notifyItemRangeInserted(oldDataCount, newDataCount);
                                    }
                                });
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Error", "exception", e);
                    }
                }));

    }

    private VKQuery createQuery(){
        VKQuery query = null;
        VKQueryBuilder builder;
        try {
            builder = new VKQueryBuilder(user.getConfiguration())
                    .setVKQueryType(VKQueryType.NEWS)
                    .setVKMethod(variant.getSubMethod())
                    .setResultFormatType(VKQueryResponseTypes.JSON)
                    .setVKResultType(Response.class);
            builder.addCondition("filters", variant.getPostFilter());
            builder.addCondition("max_photos", "7");

            query = builder.build();
        } catch (VkQueryBuilderException e) {
            e.printStackTrace();
        }

        return query;
    }

    private VKQuery createQueryFrom(String from){
        VKQuery builder = null;
        try {
            builder = new VKQueryBuilder(user.getConfiguration())
                    .setVKQueryType(VKQueryType.NEWS)
                    .setVKMethod(VKQuerySubMethod.DEFAULT)
                    .setResultFormatType(VKQueryResponseTypes.JSON)
                    .setVKResultType(Response.class)
                    .addCondition("filters", "post")
                            //  .addCondition("filters", "photo")
                    .addCondition("start_from", from)
                    .build();
        } catch (VkQueryBuilderException e) {
            e.printStackTrace();
        }

        return builder;
    }

    @Override
    public String getLogName() {
        return getClass().getSimpleName();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    public void setOnFabStateChangeListener(OnFabStateChangeListener onFabStateChangeListener) {
        this.listener = onFabStateChangeListener;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragment", "resume");
        listView.addOnScrollListener(new HidingScrollListener());
    }

    public class HidingScrollListener extends RecyclerView.OnScrollListener {
        private static final int HIDE_THRESHOLD = 20;
        private int scrolledDistance = 0;
        private boolean controlsVisible = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            //show views if first item is first visible position and views are hidden
            if (firstVisibleItem == 0) {
                if(!controlsVisible) {
                    onShow();
                    controlsVisible = true;
                }
            } else {
                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                    onHide();
                    controlsVisible = false;
                    scrolledDistance = 0;
                } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                    onShow();
                    controlsVisible = true;
                    scrolledDistance = 0;
                }
            }

            if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
                scrolledDistance += dy;
            }
        }

        public void onHide(){
            listener.onHide();
        }
        public void onShow(){
            listener.onShow();
        }
    }

    public VKUser getUser(){
        return user;
    }
}