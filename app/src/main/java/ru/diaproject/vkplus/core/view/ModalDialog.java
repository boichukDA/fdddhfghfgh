package ru.diaproject.vkplus.core.view;


import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

public class ModalDialog extends Dialog {
    public ModalDialog(Context context) {
        super(context);
        WindowManager.LayoutParams windowAttributes = this.getWindow().getAttributes();
        windowAttributes.gravity = 1;
        this.getWindow().setAttributes(windowAttributes);
        this.setTitle(null);
        this.setCancelable(false);
        this.setOnCancelListener( null);
    }

}
