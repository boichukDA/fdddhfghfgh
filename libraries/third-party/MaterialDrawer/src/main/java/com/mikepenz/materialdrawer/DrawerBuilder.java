package com.mikepenz.materialdrawer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.internal.view.SupportMenuInflater;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mikepenz.iconics.utils.Utils;
import com.mikepenz.materialdrawer.adapter.BaseDrawerAdapter;
import com.mikepenz.materialdrawer.adapter.DrawerAdapter;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Selectable;
import com.mikepenz.materialize.Materialize;
import com.mikepenz.materialize.MaterializeBuilder;
import com.mikepenz.materialize.util.UIUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mikepenz on 23.05.15.
 */
public class DrawerBuilder {

    // some internal vars
    // variable to check if a builder is only used once
    protected boolean mUsed = false;
    protected int mCurrentSelection = -1;
    protected int mCurrentFooterSelection = -1;
    protected boolean mAppended = false;

    // the activity to use
    protected Activity mActivity;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ViewGroup mRootView;
    protected Materialize mMaterialize;

    /**
     * default constructor
     */
    public DrawerBuilder() {

    }

    /**
     * Construct a Drawer by passing the activity to use for the generation
     *
     * @param activity current activity which will contain the drawer
     */
    public DrawerBuilder(@NonNull Activity activity) {
        this.mRootView = (ViewGroup) activity.findViewById(android.R.id.content);
        this.mActivity = activity;
        this.mLayoutManager = new LinearLayoutManager(mActivity);
    }

    /**
     * Sets the activity which will be generated for the generation
     * The activity is required and will be used to inflate the content in.
     * After generation it is set to null to prevent a memory leak.
     *
     * @param activity current activity which will contain the drawer
     */
    public DrawerBuilder withActivity(@NonNull Activity activity) {
        this.mRootView = (ViewGroup) activity.findViewById(android.R.id.content);
        this.mActivity = activity;
        this.mLayoutManager = new LinearLayoutManager(mActivity);
        return this;
    }

    /**
     * Sets the rootView which will host the DrawerLayout
     * The content of this view will be extracted and added as the new content inside the drawerLayout
     *
     * @param rootView a view which will get switched out by the DrawerLayout and added as its child
     */
    public DrawerBuilder withRootView(@NonNull ViewGroup rootView) {
        this.mRootView = rootView;

        //disable the translucent statusBar we don't need it
        withTranslucentStatusBar(false);

        return this;
    }

    /**
     * Sets the rootView which will host the DrawerLayout
     * The content of this view will be extracted and added as the new content inside the drawerLayout
     *
     * @param rootViewRes the id of a view which will get switched out by the DrawerLayout and added as its child
     */
    public DrawerBuilder withRootView(@IdRes int rootViewRes) {
        if (mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }

        return withRootView((ViewGroup) mActivity.findViewById(rootViewRes));
    }

    // set non translucent statusBar mode
    protected boolean mTranslucentStatusBar = true;

    /**
     * Sets that the view which hosts the DrawerLayout should have a translucent statusBar
     * This is true by default, so it's possible to display the drawer under the statusBar
     *
     * @param translucentStatusBar sets whether the statusBar is transparent (and the drawer is displayed under it) or not
     */
    public DrawerBuilder withTranslucentStatusBar(boolean translucentStatusBar) {
        this.mTranslucentStatusBar = translucentStatusBar;

        //if we disable the translucentStatusBar it should be disabled at all
        if (!translucentStatusBar) {
            this.mTranslucentStatusBarProgrammatically = false;
        }
        return this;
    }

    // set if we want to display the specific Drawer below the statusBar
    protected Boolean mDisplayBelowStatusBar;

    /**
     * Sets that the slider of this Drawer should be displayed below the statusBar even with a translucentStatusBar
     *
     * @param displayBelowStatusBar sets wheter the slider of the drawer is displayed below the statusBar or not
     */
    public DrawerBuilder withDisplayBelowStatusBar(boolean displayBelowStatusBar) {
        this.mDisplayBelowStatusBar = displayBelowStatusBar;
        return this;
    }


    // set to disable the translucent statusBar Programmatically
    protected boolean mTranslucentStatusBarProgrammatically = true;

    /**
     * Sets if the drawer should handle and make the statusBar translucent
     * This is true by default, so it's possible to display the drawer under the statusBar
     *
     * @param translucentStatusBarProgrammatically sets whether the statusBar should be transparent (and the drawer is displayed under it) or not
     */
    public DrawerBuilder withTranslucentStatusBarProgrammatically(boolean translucentStatusBarProgrammatically) {
        this.mTranslucentStatusBarProgrammatically = translucentStatusBarProgrammatically;
        //if we enable the programmatically translucent statusBar we want also the normal statusBar behavior
        if (translucentStatusBarProgrammatically) {
            this.mTranslucentStatusBar = true;
        }
        return this;
    }

    // defines if we want the statusBarShadow to be used in the Drawer
    protected Boolean mTranslucentStatusBarShadow = null;

    /**
     * Sets if the MaterialDrawer should add the translucent shadow overlay under the statusBar to get the same effect as the toolbar with a colored statusBar
     *
     * @param translucentStatusBarShadow sets wheter the drawer should handle a shadow under the translucent statusBar or not
     */
    public DrawerBuilder withTranslucentStatusBarShadow(Boolean translucentStatusBarShadow) {
        this.mTranslucentStatusBarShadow = translucentStatusBarShadow;
        return this;
    }


    // the toolbar of the activity
    protected Toolbar mToolbar;

    /**
     * Sets the toolbar which should be used in combination with the drawer
     * This will handle the ActionBarDrawerToggle for you.
     * Do not set this if you are in a sub activity and want to handle the back arrow on your own
     *
     * @param toolbar the toolbar which is used in combination with the drawer
     */
    public DrawerBuilder withToolbar(@NonNull Toolbar toolbar) {
        this.mToolbar = toolbar;
        return this;
    }

    // set non translucent NavigationBar mode
    protected boolean mTranslucentNavigationBar = false;

    /**
     * Set to true if you use a translucent NavigationBar
     *
     * @param translucentNavigationBar
     * @return
     */
    public DrawerBuilder withTranslucentNavigationBar(boolean translucentNavigationBar) {
        this.mTranslucentNavigationBar = translucentNavigationBar;

        //if we disable the translucentNavigationBar it should be disabled at all
        if (!translucentNavigationBar) {
            this.mTranslucentNavigationBarProgrammatically = false;
        }

        return this;
    }

    // set to disable the translucent statusBar Programmatically
    protected boolean mTranslucentNavigationBarProgrammatically = false;

    /**
     * set this to true if you want a translucent navigation bar.
     *
     * @param translucentNavigationBarProgrammatically
     * @return
     */
    public DrawerBuilder withTranslucentNavigationBarProgrammatically(boolean translucentNavigationBarProgrammatically) {
        this.mTranslucentNavigationBarProgrammatically = translucentNavigationBarProgrammatically;
        //if we enable the programmatically translucent navigationBar we want also the normal navigationBar behavior
        if (translucentNavigationBarProgrammatically) {
            this.mTranslucentNavigationBar = true;
        }
        return this;
    }


    // set non translucent NavigationBar mode
    protected boolean mFullscreen = false;

    /**
     * Set to true if the used theme has a translucent statusBar
     * and navigationBar and you want to manage the padding on your own.
     *
     * @param fullscreen
     * @return
     */
    public DrawerBuilder withFullscreen(boolean fullscreen) {
        this.mFullscreen = fullscreen;

        if (fullscreen) {
            withTranslucentStatusBar(false);
            withTranslucentNavigationBar(false);
        }

        return this;
    }

    // a custom view to be used instead of everything else
    protected View mCustomView;

    /**
     * Pass a custom view if you need a completely custom drawer
     * content
     *
     * @param customView
     * @return
     */
    public DrawerBuilder withCustomView(@NonNull View customView) {
        this.mCustomView = customView;
        return this;
    }

    // the drawerLayout to use
    protected DrawerLayout mDrawerLayout;
    protected RelativeLayout mSliderLayout;

    /**
     * Pass a custom DrawerLayout which will be used.
     * NOTE: This requires the same structure as the drawer.xml
     *
     * @param drawerLayout
     * @return
     */
    public DrawerBuilder withDrawerLayout(@NonNull DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
        return this;
    }

    /**
     * Pass a custom DrawerLayout Resource which will be used.
     * NOTE: This requires the same structure as the drawer.xml
     *
     * @param resLayout
     * @return
     */
    public DrawerBuilder withDrawerLayout(@LayoutRes int resLayout) {
        if (mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }

        if (resLayout != -1) {
            this.mDrawerLayout = (DrawerLayout) mActivity.getLayoutInflater().inflate(resLayout, mRootView, false);
        } else {
            this.mDrawerLayout = (DrawerLayout) mActivity.getLayoutInflater().inflate(R.layout.material_drawer, mRootView, false);
        }

        return this;
    }

    //the statusBar color
    protected int mStatusBarColor = 0;
    protected int mStatusBarColorRes = -1;

    /**
     * Set the statusBarColor color for this activity
     *
     * @param statusBarColor
     * @return
     */
    public DrawerBuilder withStatusBarColor(@ColorInt int statusBarColor) {
        this.mStatusBarColor = statusBarColor;
        return this;
    }

    /**
     * Set the statusBarColor color for this activity from a resource
     *
     * @param statusBarColorRes
     * @return
     */
    public DrawerBuilder withStatusBarColorRes(@ColorRes int statusBarColorRes) {
        this.mStatusBarColorRes = statusBarColorRes;
        return this;
    }

    //the background color for the slider
    protected int mSliderBackgroundColor = 0;
    protected int mSliderBackgroundColorRes = -1;
    protected Drawable mSliderBackgroundDrawable = null;
    protected int mSliderBackgroundDrawableRes = -1;

    /**
     * Set the background color for the Slider.
     * This is the view containing the list.
     *
     * @param sliderBackgroundColor
     * @return
     */
    public DrawerBuilder withSliderBackgroundColor(@ColorInt int sliderBackgroundColor) {
        this.mSliderBackgroundColor = sliderBackgroundColor;
        return this;
    }

    /**
     * Set the background color for the Slider from a Resource.
     * This is the view containing the list.
     *
     * @param sliderBackgroundColorRes
     * @return
     */
    public DrawerBuilder withSliderBackgroundColorRes(@ColorRes int sliderBackgroundColorRes) {
        this.mSliderBackgroundColorRes = sliderBackgroundColorRes;
        return this;
    }


    /**
     * Set the background drawable for the Slider.
     * This is the view containing the list.
     *
     * @param sliderBackgroundDrawable
     * @return
     */
    public DrawerBuilder withSliderBackgroundDrawable(@NonNull Drawable sliderBackgroundDrawable) {
        this.mSliderBackgroundDrawable = sliderBackgroundDrawable;
        return this;
    }


    /**
     * Set the background drawable for the Slider from a Resource.
     * This is the view containing the list.
     *
     * @param sliderBackgroundDrawableRes
     * @return
     */
    public DrawerBuilder withSliderBackgroundDrawableRes(@DrawableRes int sliderBackgroundDrawableRes) {
        this.mSliderBackgroundDrawableRes = sliderBackgroundDrawableRes;
        return this;
    }

    //the width of the drawer
    protected int mDrawerWidth = -1;

    /**
     * Set the DrawerBuilder width with a pixel value
     *
     * @param drawerWidthPx
     * @return
     */
    public DrawerBuilder withDrawerWidthPx(int drawerWidthPx) {
        this.mDrawerWidth = drawerWidthPx;
        return this;
    }

    /**
     * Set the DrawerBuilder width with a dp value
     *
     * @param drawerWidthDp
     * @return
     */
    public DrawerBuilder withDrawerWidthDp(int drawerWidthDp) {
        if (mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }

        this.mDrawerWidth = Utils.convertDpToPx(mActivity, drawerWidthDp);
        return this;
    }

    /**
     * Set the DrawerBuilder width with a dimension resource
     *
     * @param drawerWidthRes
     * @return
     */
    public DrawerBuilder withDrawerWidthRes(@DimenRes int drawerWidthRes) {
        if (mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }

        this.mDrawerWidth = mActivity.getResources().getDimensionPixelSize(drawerWidthRes);
        return this;
    }

    //the gravity of the drawer
    protected Integer mDrawerGravity = GravityCompat.START;

    /**
     * Set the gravity for the drawer. START, LEFT | RIGHT, END
     *
     * @param gravity
     * @return
     */
    public DrawerBuilder withDrawerGravity(int gravity) {
        this.mDrawerGravity = gravity;
        return this;
    }

    //the account selection header to use
    protected AccountHeader mAccountHeader;
    protected boolean mAccountHeaderSticky = false;

    /**
     * Add a AccountSwitcherHeader which will be used in this drawer instance.
     * NOTE: This will overwrite any set headerView.
     *
     * @param accountHeader
     * @return
     */
    public DrawerBuilder withAccountHeader(@NonNull AccountHeader accountHeader) {
        return withAccountHeader(accountHeader, false);
    }

    /**
     * Add a AccountSwitcherHeader which will be used in this drawer instance. Pass true if it should be sticky
     * NOTE: This will overwrite any set headerView or stickyHeaderView (depends on the boolean).
     *
     * @param accountHeader
     * @param accountHeaderSticky
     * @return
     */
    public DrawerBuilder withAccountHeader(@NonNull AccountHeader accountHeader, boolean accountHeaderSticky) {
        this.mAccountHeader = accountHeader;
        this.mAccountHeaderSticky = accountHeaderSticky;
        return this;
    }

    // enable/disable the actionBarDrawerToggle animation
    protected boolean mAnimateActionBarDrawerToggle = false;

    /**
     * Set this to true if you want the ActionBarDrawerToggle to be animated.
     * NOTE: This will only work if the built in ActionBarDrawerToggle is used.
     * Enable it by setting withActionBarDrawerToggle to true
     *
     * @param actionBarDrawerToggleAnimated
     * @return
     */
    public DrawerBuilder withActionBarDrawerToggleAnimated(boolean actionBarDrawerToggleAnimated) {
        this.mAnimateActionBarDrawerToggle = actionBarDrawerToggleAnimated;
        return this;
    }


    // enable the drawer toggle / if withActionBarDrawerToggle we will autoGenerate it
    protected boolean mActionBarDrawerToggleEnabled = true;

    /**
     * Set this to false if you don't need the included ActionBarDrawerToggle
     *
     * @param actionBarDrawerToggleEnabled
     * @return
     */
    public DrawerBuilder withActionBarDrawerToggle(boolean actionBarDrawerToggleEnabled) {
        this.mActionBarDrawerToggleEnabled = actionBarDrawerToggleEnabled;
        return this;
    }

    // drawer toggle
    protected ActionBarDrawerToggle mActionBarDrawerToggle;

    /**
     * Add a custom ActionBarDrawerToggle which will be used in combination with this drawer.
     *
     * @param actionBarDrawerToggle
     * @return
     */
    public DrawerBuilder withActionBarDrawerToggle(@NonNull ActionBarDrawerToggle actionBarDrawerToggle) {
        this.mActionBarDrawerToggleEnabled = true;
        this.mActionBarDrawerToggle = actionBarDrawerToggle;
        return this;
    }

    // defines if the drawer should scroll to top after click
    protected boolean mScrollToTopAfterClick = false;

    /**
     * defines if the drawer should scroll to top after click
     *
     * @param scrollToTopAfterClick
     * @return
     */
    public DrawerBuilder withScrollToTopAfterClick(boolean scrollToTopAfterClick) {
        this.mScrollToTopAfterClick = scrollToTopAfterClick;
        return this;
    }


    // header view
    protected View mHeaderView;
    protected boolean mHeaderDivider = true;
    protected boolean mHeaderClickable = false;

    /**
     * Add a header to the DrawerBuilder ListView. This can be any view
     *
     * @param headerView
     * @return
     */
    public DrawerBuilder withHeader(@NonNull View headerView) {
        this.mHeaderView = headerView;
        return this;
    }

    /**
     * Add a header to the DrawerBuilder ListView defined by a resource.
     *
     * @param headerViewRes
     * @return
     */
    public DrawerBuilder withHeader(@LayoutRes int headerViewRes) {
        if (mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }

        if (headerViewRes != -1) {
            //i know there should be a root, bit i got none here
            this.mHeaderView = mActivity.getLayoutInflater().inflate(headerViewRes, null, false);
        }

        return this;
    }

    /**
     * Set this to true if you want the header to be clickable
     *
     * @param headerClickable
     * @return
     */
    public DrawerBuilder withHeaderClickable(boolean headerClickable) {
        this.mHeaderClickable = headerClickable;
        return this;
    }

    /**
     * Set this to false if you don't need the divider below the header
     *
     * @param headerDivider
     * @return
     */
    public DrawerBuilder withHeaderDivider(boolean headerDivider) {
        this.mHeaderDivider = headerDivider;
        return this;
    }

    // sticky view
    protected View mStickyHeaderView;

    /**
     * Add a sticky header below the DrawerBuilder ListView. This can be any view
     *
     * @param stickyHeader
     * @return
     */
    public DrawerBuilder withStickyHeader(@NonNull View stickyHeader) {
        this.mStickyHeaderView = stickyHeader;
        return this;
    }

    /**
     * Add a sticky header below the DrawerBuilder ListView defined by a resource.
     *
     * @param stickyHeaderRes
     * @return
     */
    public DrawerBuilder withStickyHeader(@LayoutRes int stickyHeaderRes) {
        if (mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }

        if (stickyHeaderRes != -1) {
            //i know there should be a root, bit i got none here
            this.mStickyHeaderView = mActivity.getLayoutInflater().inflate(stickyHeaderRes, null, false);
        }

        return this;
    }

    // footer view
    protected View mFooterView;
    protected boolean mFooterDivider = true;
    protected boolean mFooterClickable = false;

    /**
     * Add a footer to the DrawerBuilder ListView. This can be any view
     *
     * @param footerView
     * @return
     */
    public DrawerBuilder withFooter(@NonNull View footerView) {
        this.mFooterView = footerView;
        return this;
    }

    /**
     * Add a footer to the DrawerBuilder ListView defined by a resource.
     *
     * @param footerViewRes
     * @return
     */
    public DrawerBuilder withFooter(@LayoutRes int footerViewRes) {
        if (mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }

        if (footerViewRes != -1) {
            //i know there should be a root, bit i got none here
            this.mFooterView = mActivity.getLayoutInflater().inflate(footerViewRes, null, false);
        }

        return this;
    }

    /**
     * Set this to true if you want the footer to be clickable
     *
     * @param footerClickable
     * @return
     */
    public DrawerBuilder withFooterClickable(boolean footerClickable) {
        this.mFooterClickable = footerClickable;
        return this;
    }

    /**
     * Set this to false if you don't need the divider above the footer
     *
     * @param footerDivider
     * @return
     */
    public DrawerBuilder withFooterDivider(boolean footerDivider) {
        this.mFooterDivider = footerDivider;
        return this;
    }

    // sticky view
    protected ViewGroup mStickyFooterView;
    protected Boolean mStickyFooterDivider = null;

    /**
     * Add a sticky footer below the DrawerBuilder ListView. This can be any view
     *
     * @param stickyFooter
     * @return
     */
    public DrawerBuilder withStickyFooter(@NonNull ViewGroup stickyFooter) {
        this.mStickyFooterView = stickyFooter;
        return this;
    }

    /**
     * Add a sticky footer below the DrawerBuilder ListView defined by a resource.
     *
     * @param stickyFooterRes
     * @return
     */
    public DrawerBuilder withStickyFooter(@LayoutRes int stickyFooterRes) {
        if (mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }

        if (stickyFooterRes != -1) {
            //i know there should be a root, bit i got none here
            this.mStickyFooterView = (ViewGroup) mActivity.getLayoutInflater().inflate(stickyFooterRes, null, false);
        }

        return this;
    }

    /**
     * Set this to false if you don't need the divider above the sticky footer
     *
     * @param stickyFooterDivider
     * @return
     */
    public DrawerBuilder withStickyFooterDivider(Boolean stickyFooterDivider) {
        this.mStickyFooterDivider = stickyFooterDivider;
        return this;
    }

    // fire onClick after build
    protected boolean mFireInitialOnClick = false;

    /**
     * Set this to true if you love to get an initial onClick event after the build method is called
     *
     * @param fireOnInitialOnClick
     * @return
     */
    public DrawerBuilder withFireOnInitialOnClick(boolean fireOnInitialOnClick) {
        this.mFireInitialOnClick = fireOnInitialOnClick;
        return this;
    }

    // item to select
    protected int mSelectedItemPosition = 0;

    /**
     * Set this to the index of the item, you would love to select upon start
     *
     * @param selectedItemPosition
     * @return
     */
    public DrawerBuilder withSelectedItemByPosition(int selectedItemPosition) {
        this.mSelectedItemPosition = selectedItemPosition;
        return this;
    }

    // an RecyclerView to use within the drawer :D
    protected RecyclerView mRecyclerView;

    /**
     * Define a custom RecyclerView which will be used in the drawer
     * NOTE: this is not recommended
     *
     * @param recyclerView
     * @return
     */
    public DrawerBuilder withRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        return this;
    }

    // an adapter to use for the list
    protected BaseDrawerAdapter mAdapter;

    /**
     * Define a custom Adapter which will be used in the drawer
     * NOTE: this is not recommended
     *
     * @param adapter
     * @return
     */
    public DrawerBuilder withAdapter(@NonNull BaseDrawerAdapter adapter) {
        if (mAdapter != null) {
            throw new RuntimeException("the adapter was already set or items were added to it. A header is also a RecyclerItem");
        }
        this.mAdapter = adapter;
        return this;
    }

    /**
     * get the adapter (null safe)
     *
     * @return
     */
    protected BaseDrawerAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new DrawerAdapter();
        }
        return mAdapter;
    }

    // Defines a Adapter which wraps the main Adapter used in the RecyclerView to allow extended navigation and other stuff
    protected RecyclerView.Adapter mAdapterWrapper;

    /**
     * Defines a Adapter which wraps the main Adapter used in the RecyclerView to allow extended navigation and other stuff
     *
     * @param adapterWrapper
     * @return
     */
    public DrawerBuilder withAdapterWrapper(@NonNull RecyclerView.Adapter adapterWrapper) {
        if (mAdapter == null) {
            throw new RuntimeException("this adapter has to be set in conjunction to a normal adapter which is used inside this wrapper adapter");
        }
        this.mAdapterWrapper = adapterWrapper;
        return this;
    }


    //defines the itemAnimator to be used in conjunction with the RecyclerView
    protected RecyclerView.ItemAnimator mItemAnimator = null;

    /**
     * defines the itemAnimator to be used in conjunction with the RecyclerView
     *
     * @param itemAnimator
     * @return
     */
    public DrawerBuilder withItemAnimator(@NonNull RecyclerView.ItemAnimator itemAnimator) {
        mItemAnimator = itemAnimator;
        return this;
    }

    /**
     * Set the initial List of IDrawerItems for the Drawer
     *
     * @param drawerItems
     * @return
     */
    public DrawerBuilder withDrawerItems(@NonNull ArrayList<IDrawerItem> drawerItems) {
        this.getAdapter().setDrawerItems(drawerItems);
        return this;
    }

    /**
     * Add a initial DrawerItem or a DrawerItem Array  for the Drawer
     *
     * @param drawerItems
     * @return
     */
    public DrawerBuilder addDrawerItems(@NonNull IDrawerItem... drawerItems) {
        this.getAdapter().addDrawerItems(drawerItems);
        return this;
    }

    // always visible list in drawer
    protected ArrayList<IDrawerItem> mStickyDrawerItems = new ArrayList<>();

    /**
     * Set the initial List of IDrawerItems for the StickyDrawerFooter
     *
     * @param stickyDrawerItems
     * @return
     */
    public DrawerBuilder withStickyDrawerItems(@NonNull ArrayList<IDrawerItem> stickyDrawerItems) {
        this.mStickyDrawerItems = stickyDrawerItems;
        return this;
    }

    /**
     * Add a initial DrawerItem or a DrawerItem Array for the StickyDrawerFooter
     *
     * @param stickyDrawerItems
     * @return
     */
    public DrawerBuilder addStickyDrawerItems(@NonNull IDrawerItem... stickyDrawerItems) {
        if (this.mStickyDrawerItems == null) {
            this.mStickyDrawerItems = new ArrayList<>();
        }

        if (stickyDrawerItems != null) {
            Collections.addAll(this.mStickyDrawerItems, stickyDrawerItems);
        }
        return this;
    }

    /**
     * Inflates the DrawerItems from a menu.xml
     *
     * @param menuRes
     * @return
     */
    public DrawerBuilder inflateMenu(@MenuRes int menuRes) {
        MenuInflater menuInflater = new SupportMenuInflater(mActivity);
        MenuBuilder mMenu = new MenuBuilder(mActivity);

        menuInflater.inflate(menuRes, mMenu);

        addMenuItems(mMenu, false);

        return this;
    }

    /**
     * helper method to init the drawerItems from a menu
     *
     * @param mMenu
     * @param subMenu
     */
    private void addMenuItems(Menu mMenu, boolean subMenu) {
        int groupId = R.id.material_drawer_menu_default_group;
        for (int i = 0; i < mMenu.size(); i++) {
            MenuItem mMenuItem = mMenu.getItem(i);
            IDrawerItem iDrawerItem;
            if (!subMenu && mMenuItem.getGroupId() != groupId && mMenuItem.getGroupId() != 0) {
                groupId = mMenuItem.getGroupId();
                iDrawerItem = new DividerDrawerItem();
                getAdapter().addDrawerItems(iDrawerItem);
            }
            if (mMenuItem.hasSubMenu()) {
                iDrawerItem = new PrimaryDrawerItem()
                        .withName(mMenuItem.getTitle().toString())
                        .withIcon(mMenuItem.getIcon())
                        .withIdentifier(mMenuItem.getItemId())
                        .withEnabled(mMenuItem.isEnabled())
                        .withSelectable(false);
                getAdapter().addDrawerItems(iDrawerItem);
                addMenuItems(mMenuItem.getSubMenu(), true);
            } else if (mMenuItem.getGroupId() != 0 || subMenu) {
                iDrawerItem = new SecondaryDrawerItem()
                        .withName(mMenuItem.getTitle().toString())
                        .withIcon(mMenuItem.getIcon())
                        .withIdentifier(mMenuItem.getItemId())
                        .withEnabled(mMenuItem.isEnabled());
                getAdapter().addDrawerItems(iDrawerItem);
            } else {
                iDrawerItem = new PrimaryDrawerItem()
                        .withName(mMenuItem.getTitle().toString())
                        .withIcon(mMenuItem.getIcon())
                        .withIdentifier(mMenuItem.getItemId())
                        .withEnabled(mMenuItem.isEnabled());
                getAdapter().addDrawerItems(iDrawerItem);
            }
        }
    }

    // close drawer on click
    protected boolean mCloseOnClick = true;

    /**
     * Set this to false if the drawer should stay opened after an item was clicked
     *
     * @param closeOnClick
     * @return this
     */
    public DrawerBuilder withCloseOnClick(boolean closeOnClick) {
        this.mCloseOnClick = closeOnClick;
        return this;
    }

    // delay drawer close to prevent lag
    protected int mDelayOnDrawerClose = 50;

    /**
     * Define the delay for the drawer close operation after a click.
     * This is a small trick to improve the speed (and remove lag) if you open a new activity after a DrawerItem
     * was selected.
     * NOTE: Disable this by passing -1
     *
     * @param delayOnDrawerClose -1 to disable
     * @return this
     */
    public DrawerBuilder withDelayOnDrawerClose(int delayOnDrawerClose) {
        this.mDelayOnDrawerClose = delayOnDrawerClose;
        return this;
    }


    // onDrawerListener
    protected Drawer.OnDrawerListener mOnDrawerListener;

    /**
     * Define a OnDrawerListener for this Drawer
     *
     * @param onDrawerListener
     * @return this
     */
    public DrawerBuilder withOnDrawerListener(@NonNull Drawer.OnDrawerListener onDrawerListener) {
        this.mOnDrawerListener = onDrawerListener;
        return this;
    }

    // onDrawerItemClickListeners
    protected Drawer.OnDrawerItemClickListener mOnDrawerItemClickListener;

    /**
     * Define a OnDrawerItemClickListener for this Drawer
     *
     * @param onDrawerItemClickListener
     * @return
     */
    public DrawerBuilder withOnDrawerItemClickListener(@NonNull Drawer.OnDrawerItemClickListener onDrawerItemClickListener) {
        this.mOnDrawerItemClickListener = onDrawerItemClickListener;
        return this;
    }

    // onDrawerItemClickListeners
    protected Drawer.OnDrawerItemLongClickListener mOnDrawerItemLongClickListener;

    /**
     * Define a OnDrawerItemLongClickListener for this Drawer
     *
     * @param onDrawerItemLongClickListener
     * @return
     */
    public DrawerBuilder withOnDrawerItemLongClickListener(@NonNull Drawer.OnDrawerItemLongClickListener onDrawerItemLongClickListener) {
        this.mOnDrawerItemLongClickListener = onDrawerItemLongClickListener;
        return this;
    }

    // onDrawerItemClickListeners
    protected Drawer.OnDrawerItemSelectedListener mOnDrawerItemSelectedListener;

    /**
     * Define a OnDrawerItemSelectedListener for this Drawer
     *
     * @param onDrawerItemSelectedListener
     * @return
     */
    public DrawerBuilder withOnDrawerItemSelectedListener(@NonNull Drawer.OnDrawerItemSelectedListener onDrawerItemSelectedListener) {
        this.mOnDrawerItemSelectedListener = onDrawerItemSelectedListener;
        return this;
    }

    // onDrawerListener
    protected Drawer.OnDrawerNavigationListener mOnDrawerNavigationListener;

    /**
     * Define a OnDrawerNavigationListener for this Drawer
     *
     * @param onDrawerNavigationListener
     * @return this
     */
    public DrawerBuilder withOnDrawerNavigationListener(@NonNull Drawer.OnDrawerNavigationListener onDrawerNavigationListener) {
        this.mOnDrawerNavigationListener = onDrawerNavigationListener;
        return this;
    }

    //show the drawer on the first launch to show the user its there
    protected boolean mShowDrawerOnFirstLaunch = false;

    /**
     * define if the DrawerBuilder is shown on the first launch
     *
     * @param showDrawerOnFirstLaunch
     * @return
     */
    public DrawerBuilder withShowDrawerOnFirstLaunch(boolean showDrawerOnFirstLaunch) {
        this.mShowDrawerOnFirstLaunch = showDrawerOnFirstLaunch;
        return this;
    }

    // savedInstance to restore state
    protected Bundle mSavedInstance;

    /**
     * Set the Bundle (savedInstance) which is passed by the activity.
     * No need to null-check everything is handled automatically
     *
     * @param savedInstance
     * @return
     */
    public DrawerBuilder withSavedInstance(Bundle savedInstance) {
        this.mSavedInstance = savedInstance;
        return this;
    }

    /**
     * helper method to handle when the drawer should be shown on the first launch
     */
    private void handleShowOnFirstLaunch() {
        //check if it should be shown on first launch (and we have a drawerLayout)
        if (mActivity != null && mDrawerLayout != null && mShowDrawerOnFirstLaunch) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
            //if it was not shown yet
            if (!preferences.getBoolean(Drawer.PREF_USER_LEARNED_DRAWER, false)) {
                //open the drawer
                mDrawerLayout.openDrawer(mSliderLayout);

                //save that it showed up once ;)
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Drawer.PREF_USER_LEARNED_DRAWER, true);
                editor.apply();
            }
        }
    }

    /**
     * Build and add the DrawerBuilder to your activity
     *
     * @return
     */
    public Drawer build() {
        if (mUsed) {
            throw new RuntimeException("you must not reuse a DrawerBuilder builder");
        }
        if (mActivity == null) {
            throw new RuntimeException("please pass an activity");
        }

        //set that this builder was used. now you have to create a new one
        mUsed = true;

        // if the user has not set a drawerLayout use the default one :D
        if (mDrawerLayout == null) {
            withDrawerLayout(-1);
        }

        //some new Materialize magic ;)
        mMaterialize = new MaterializeBuilder()
                .withActivity(mActivity)
                .withRootView(mRootView)
                .withFullscreen(mFullscreen)
                .withTranslucentStatusBar(mTranslucentStatusBar)
                .withTranslucentStatusBarProgrammatically(mTranslucentStatusBarProgrammatically)
                .withTranslucentNavigationBar(mTranslucentNavigationBar)
                .withTranslucentNavigationBarProgrammatically(mTranslucentNavigationBarProgrammatically)
                .withContainer(mDrawerLayout)
                .withStatusBarColor(mStatusBarColor)
                .withStatusBarColorRes(mStatusBarColorRes)
                .build();

        //handle the navigation stuff of the ActionBarDrawerToggle and the drawer in general
        handleDrawerNavigation(mActivity);

        //build the view which will be set to the drawer
        Drawer result = buildView();

        // add the slider to the drawer
        mDrawerLayout.addView(mSliderLayout, 1);

        return result;
    }

    /**
     * handles the different logics for the Drawer Navigation Listeners / Indications (ActionBarDrawertoggle)
     */
    protected void handleDrawerNavigation(Activity activity) {
        //set the navigationOnClickListener
        final View.OnClickListener toolbarNavigationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean handled = false;

                if (mOnDrawerNavigationListener != null && (mActionBarDrawerToggle != null && !mActionBarDrawerToggle.isDrawerIndicatorEnabled())) {
                    handled = mOnDrawerNavigationListener.onNavigationClickListener(v);
                }
                if (!handled) {
                    if (mDrawerLayout.isDrawerOpen(mDrawerGravity)) {
                        mDrawerLayout.closeDrawer(mDrawerGravity);
                    } else {
                        mDrawerLayout.openDrawer(mDrawerGravity);
                    }
                }
            }
        };

        // create the ActionBarDrawerToggle if not set and enabled and if we have a toolbar
        if (mActionBarDrawerToggleEnabled && mActionBarDrawerToggle == null && mToolbar != null) {
            this.mActionBarDrawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout, mToolbar, R.string.material_drawer_open, R.string.material_drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    if (mOnDrawerListener != null) {
                        mOnDrawerListener.onDrawerOpened(drawerView);
                    }
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    if (mOnDrawerListener != null) {
                        mOnDrawerListener.onDrawerClosed(drawerView);
                    }
                    super.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    if (mOnDrawerListener != null) {
                        mOnDrawerListener.onDrawerSlide(drawerView, slideOffset);
                    }

                    if (!mAnimateActionBarDrawerToggle) {
                        super.onDrawerSlide(drawerView, 0);
                    } else {
                        super.onDrawerSlide(drawerView, slideOffset);
                    }
                }
            };
            this.mActionBarDrawerToggle.syncState();
        }

        //if we got a toolbar set a toolbarNavigationListener
        //we also have to do this after setting the ActionBarDrawerToggle as this will overwrite this
        if (mToolbar != null) {
            this.mToolbar.setNavigationOnClickListener(toolbarNavigationListener);
        }

        //handle the ActionBarDrawerToggle
        if (mActionBarDrawerToggle != null) {
            mActionBarDrawerToggle.setToolbarNavigationClickListener(toolbarNavigationListener);
            mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        } else {
            mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    if (mOnDrawerListener != null) {
                        mOnDrawerListener.onDrawerSlide(drawerView, slideOffset);
                    }
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    if (mOnDrawerListener != null) {
                        mOnDrawerListener.onDrawerOpened(drawerView);
                    }
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    if (mOnDrawerListener != null) {
                        mOnDrawerListener.onDrawerClosed(drawerView);
                    }
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        }
    }

    /**
     * build the drawers content only. This will still return a Result object, but only with the content set. No inflating of a DrawerLayout.
     *
     * @return Result object with only the content set
     */
    public Drawer buildView() {
        // get the slider view
        mSliderLayout = (RelativeLayout) mActivity.getLayoutInflater().inflate(R.layout.material_drawer_slider, mDrawerLayout, false);
        mSliderLayout.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(mActivity, R.attr.material_drawer_background, R.color.material_drawer_background));
        // get the layout params
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mSliderLayout.getLayoutParams();
        if (params != null) {
            // if we've set a custom gravity set it
            params.gravity = mDrawerGravity;
            // if this is a drawer from the right, change the margins :D
            params = DrawerUtils.processDrawerLayoutParams(this, params);
            // set the new layout params
            mSliderLayout.setLayoutParams(params);
        }

        //create the content
        createContent();

        //create the result object
        Drawer result = new Drawer(this);
        //set the drawer for the accountHeader if set
        if (mAccountHeader != null) {
            mAccountHeader.setDrawer(result);
        }

        //handle if the drawer should be shown on first launch
        handleShowOnFirstLaunch();

        //forget the reference to the activity
        mActivity = null;

        return result;
    }

    /**
     * Call this method to append a new DrawerBuilder to a existing Drawer.
     *
     * @param result the Drawer.Result of an existing Drawer
     * @return
     */
    public Drawer append(@NonNull Drawer result) {
        if (mUsed) {
            throw new RuntimeException("you must not reuse a DrawerBuilder builder");
        }
        if (mDrawerGravity == null) {
            throw new RuntimeException("please set the gravity for the drawer");
        }

        //set that this builder was used. now you have to create a new one
        mUsed = true;
        mAppended = true;

        //get the drawer layout from the previous drawer
        mDrawerLayout = result.getDrawerLayout();

        // get the slider view
        mSliderLayout = (RelativeLayout) mActivity.getLayoutInflater().inflate(R.layout.material_drawer_slider, mDrawerLayout, false);
        mSliderLayout.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(mActivity, R.attr.material_drawer_background, R.color.material_drawer_background));
        // get the layout params
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mSliderLayout.getLayoutParams();
        // set the gravity of this drawerGravity
        params.gravity = mDrawerGravity;
        // if this is a drawer from the right, change the margins :D
        params = DrawerUtils.processDrawerLayoutParams(this, params);
        // set the new params
        mSliderLayout.setLayoutParams(params);
        // add the slider to the drawer
        mDrawerLayout.addView(mSliderLayout, 1);

        //create the content
        createContent();

        //forget the reference to the activity
        mActivity = null;

        return new Drawer(this);
    }

    /**
     * the helper method to create the content for the drawer
     */
    private void createContent() {
        //if we have a customView use this
        if (mCustomView != null) {
            LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            contentParams.weight = 1f;
            mSliderLayout.addView(mCustomView, contentParams);
            return;
        }

        //set the shadow for the drawer
        if (Build.VERSION.SDK_INT < 21 && mDrawerLayout != null) {
            if (mDrawerGravity == GravityCompat.START) {
                mDrawerLayout.setDrawerShadow(R.drawable.material_drawer_shadow_right, mDrawerGravity);
            } else {
                mDrawerLayout.setDrawerShadow(R.drawable.material_drawer_shadow_left, mDrawerGravity);
            }
        }

        // if we have an adapter (either by defining a custom one or the included one add a list :D
        if (mRecyclerView == null) {
            mRecyclerView = (RecyclerView) LayoutInflater.from(mActivity).inflate(R.layout.material_drawer_recycler_view, mSliderLayout, false);
            //set the itemAnimator
            if (mItemAnimator == null) {
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            } else {
                mRecyclerView.setItemAnimator(mItemAnimator);
            }
            //some style improvements on older devices
            mRecyclerView.setFadingEdgeLength(0);

            //set the drawing cache background to the same color as the slider to improve performance
            //mRecyclerView.setDrawingCacheBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(mActivity, R.attr.material_drawer_background, R.color.material_drawer_background));
            mRecyclerView.setClipToPadding(false);
            //additional stuff
            mRecyclerView.setLayoutManager(mLayoutManager);

            int paddingTop = 0;
            if ((mTranslucentStatusBar || mFullscreen) && (mDisplayBelowStatusBar == null || !mDisplayBelowStatusBar)) {
                paddingTop = UIUtils.getStatusBarHeight(mActivity);
            }
            int paddingBottom = 0;
            if ((mTranslucentNavigationBar || mFullscreen) && Build.VERSION.SDK_INT >= 19) {
                paddingBottom = UIUtils.getNavigationBarHeight(mActivity);
            }

            mRecyclerView.setPadding(0, paddingTop, 0, paddingBottom);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.weight = 1f;
        mSliderLayout.addView(mRecyclerView, params);

        //find the shadow view
        View statusBarShadow = mSliderLayout.findViewById(R.id.material_drawer_shadow_top);
        RelativeLayout.LayoutParams shadowLayoutParams = (RelativeLayout.LayoutParams) statusBarShadow.getLayoutParams();
        shadowLayoutParams.height = UIUtils.getStatusBarHeight(mActivity, true);
        statusBarShadow.setLayoutParams(shadowLayoutParams);

        // set the background
        if (mSliderBackgroundColor != 0) {
            mSliderLayout.setBackgroundColor(mSliderBackgroundColor);
        } else if (mSliderBackgroundColorRes != -1) {
            mSliderLayout.setBackgroundColor(mActivity.getResources().getColor(mSliderBackgroundColorRes));
        } else if (mSliderBackgroundDrawable != null) {
            UIUtils.setBackground(mSliderLayout, mSliderBackgroundDrawable);
        } else if (mSliderBackgroundDrawableRes != -1) {
            UIUtils.setBackground(mSliderLayout, mSliderBackgroundColorRes);
        }

        //some extra stuff to beautify the whole thing ;)
        if ((mTranslucentStatusBar || (mTranslucentStatusBarShadow != null && mTranslucentStatusBarShadow))) {
            if (mTranslucentStatusBarShadow == null) {
                //if we use the default behavior show it only if we are >= API Level 21
                if (Build.VERSION.SDK_INT >= 21) {
                    //bring shadow bar to front again
                    statusBarShadow.bringToFront();
                } else {
                    //disable the shadow if  we are on a lower sdk
                    statusBarShadow.setVisibility(View.GONE);
                }
            } else {
                //bring shadow bar to front again
                statusBarShadow.bringToFront();
            }
        } else {
            //disable the shadow if we don't use a translucent activity
            statusBarShadow.setVisibility(View.GONE);
        }

        if (mDisplayBelowStatusBar != null && mDisplayBelowStatusBar) {
            //disable the shadow if we are below the statusBar
            statusBarShadow.setVisibility(View.GONE);
        }

        //handle the header
        DrawerUtils.handleHeaderView(this);

        //handle the footer
        DrawerUtils.handleFooterView(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDrawerItem drawerItem = (IDrawerItem) v.getTag();
                DrawerUtils.onFooterDrawerItemClick(DrawerBuilder.this, drawerItem, v, true);
            }
        });

        //after adding the header do the setAdapter and set the selection

        //set the adapter on the listView
        if (mAdapterWrapper == null) {
            mRecyclerView.setAdapter(getAdapter());
        } else {
            mRecyclerView.setAdapter(mAdapterWrapper);
        }

        //predefine selection (should be the first element
        if (mHeaderView != null && mSelectedItemPosition == 0) {
            mSelectedItemPosition = 1;
        }
        DrawerUtils.setRecyclerViewSelection(this, mSelectedItemPosition, false);

        // add the onDrawerItemClickListener if set
        mAdapter.setOnClickListener(new BaseDrawerAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, IDrawerItem item) {
                if (!(item != null && item instanceof Selectable && !((Selectable) item).isSelectable())) {
                    resetStickyFooterSelection();
                    mCurrentSelection = position;
                    mCurrentFooterSelection = -1;
                }

                boolean consumed = false;
                if (mOnDrawerItemClickListener != null) {
                    consumed = mOnDrawerItemClickListener.onItemClick(view, position, item);
                }

                if (!consumed) {
                    //close the drawer after click
                    closeDrawerDelayed();
                }
            }
        });

        // add the onDrawerItemLongClickListener if set
        mAdapter.setOnLongClickListener(new BaseDrawerAdapter.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view, int position, IDrawerItem item) {
                if (mOnDrawerItemLongClickListener != null) {
                    return mOnDrawerItemLongClickListener.onItemLongClick(view, position, getDrawerItem(position));
                }
                return false;
            }
        });

        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(0);
        }

        // try to restore all saved values again
        if (mSavedInstance != null) {
            if (!mAppended) {
                DrawerUtils.setRecyclerViewSelection(this, mSavedInstance.getInt(Drawer.BUNDLE_SELECTION, -1), false);
                DrawerUtils.setFooterSelection(this, mSavedInstance.getInt(Drawer.BUNDLE_FOOTER_SELECTION, -1), null);
            } else {
                DrawerUtils.setRecyclerViewSelection(this, mSavedInstance.getInt(Drawer.BUNDLE_SELECTION_APPENDED, -1), false);
                DrawerUtils.setFooterSelection(this, mSavedInstance.getInt(Drawer.BUNDLE_FOOTER_SELECTION_APPENDED, -1), null);
            }
        }

        // call initial onClick event to allow the dev to init the first view
        if (mFireInitialOnClick && mOnDrawerItemClickListener != null) {
            mOnDrawerItemClickListener.onItemClick(null, mCurrentSelection, getDrawerItem(mCurrentSelection));
        }
    }

    /**
     * helper method to close the drawer delayed
     */
    protected void closeDrawerDelayed() {
        if (mCloseOnClick && mDrawerLayout != null) {
            if (mDelayOnDrawerClose > -1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawerLayout.closeDrawers();

                        if (mScrollToTopAfterClick) {
                            mRecyclerView.smoothScrollToPosition(0);
                        }
                    }
                }, mDelayOnDrawerClose);
            } else {
                mDrawerLayout.closeDrawers();
            }
        }
    }

    /**
     * get the drawerItem at a specific position
     *
     * @param position
     * @return
     */
    protected IDrawerItem getDrawerItem(int position) {
        return getAdapter().getItem(position);
    }

    /**
     * check if the item is within the bounds of the list
     *
     * @param position
     * @param includeOffset
     * @return
     */
    protected boolean checkDrawerItem(int position, boolean includeOffset) {
        return getAdapter().getItem(position) != null;
    }

    /**
     * simple helper method to reset the selection of the sticky footer
     */
    protected void resetStickyFooterSelection() {
        if (mStickyFooterView instanceof LinearLayout) {
            for (int i = 0; i < (mStickyFooterView).getChildCount(); i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    (mStickyFooterView).getChildAt(i).setActivated(false);
                }
                (mStickyFooterView).getChildAt(i).setSelected(false);
            }
        }
    }
}