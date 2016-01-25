package ru.diaproject.vkplus.core;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.utils.ApplicationConfiguration;
import ru.diaproject.vkplus.core.view.ModalDialog;

public abstract class ParentActivity extends Activity {

    private LinearLayout rootLayout;
    private RelativeLayout headerLayout;
    private LinearLayout userLayout;
    private Dialog progressDialog;
    private LayoutInflater inflater;
    private ApplicationConfiguration config = ApplicationConfiguration.INSTANCE;

    private final int SHOW_TOP_BAR = 1;
    private final int HIDE_TOP_BAR = 2;


    Handler eventsHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SHOW_TOP_BAR:
                    showTopBarView();
                    break;
                case HIDE_TOP_BAR:
                    hideTopBarView();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void hideTopBarView() {
        if (headerLayout!= null)
            headerLayout.setVisibility(View.GONE);

    }

    private void showTopBarView() {

    }

    protected void hideTopbar(){
        eventsHandler.sendEmptyMessage(HIDE_TOP_BAR);
    }
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        try {

            if (inflater == null)
                inflater = LayoutInflater.from(this);

            super.onCreate(savedInstanceState);

            rootLayout = (LinearLayout) inflater.inflate(R.layout.vkplus_header_layout, null);

            headerLayout = (RelativeLayout) rootLayout.findViewById(R.id.header_layout);

            userLayout = (LinearLayout) rootLayout.findViewById(R.id.user_view_container);

            eventsHandler.sendEmptyMessage(SHOW_TOP_BAR);

            super.setContentView(rootLayout);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showProgressDialog(true);
                            }
                        });

                        initContext(savedInstanceState);
                        initBackend(savedInstanceState);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressDialog();
                                initUI(savedInstanceState);

                            }
                        });

                    }
                    catch(final Throwable e){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressDialog();
                                Log.e(getLogName(), "exception", e);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.fatal_error), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                }
            }).start();

        }
        catch(final Throwable e){
            Log.e(getLogName(), "exception", e);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fatal_error), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public final void setContentView(int layoutResID) {
        View childView = inflater.inflate(layoutResID, null);
       setContentView(childView);
    }

    @Override
    public final void setContentView(View view) {
        if (userLayout.getChildCount()!= 0)
            userLayout.removeAllViews();

        userLayout.addView(view);

        super.setContentView(rootLayout);
    }

    @Override
    public final void setContentView(View view, ViewGroup.LayoutParams params) {
        view.setLayoutParams(params);
        setContentView(view);
    }

    protected abstract void initContext(Bundle savedInstanceState);

    protected abstract void initBackend(Bundle savedInstanceState);

    protected abstract void initUI(Bundle savedInstanceState);

    protected final void showProgressDialog(boolean cancelable) {
        if(this.progressDialog != null && (!(this.progressDialog instanceof ProgressDialog) || cancelable)) {
            if(!this.progressDialog.isShowing()) {
                this.progressDialog.show();
            }
        } else {
            if(cancelable) {
                this.progressDialog = ProgressDialog.show(this, null, this.getString(R.string.progress_text), true);
                this.progressDialog.setCancelable(true);
                this.progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialogInterface) {
                        ParentActivity.this.onBackPressed();
                    }
                });
            } else {
                this.progressDialog = new ModalDialog(this);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }
        }
    }

    protected final void hideProgressDialog() {
        if(this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }
    protected ApplicationConfiguration getConfig(){
        return config;
    }
    protected abstract String getLogName();
}
