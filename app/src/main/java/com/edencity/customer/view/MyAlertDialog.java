package com.edencity.customer.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edencity.customer.R;

public class MyAlertDialog extends DialogFragment {

//    private String title;
    private String message;

    private DialogInterface.OnDismissListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.dialog_alert, container);
//        if (title!=null)
//            ((TextView)view.findViewById(R.id.dialog_alert_title)).setText(title);
        ((TextView)view.findViewById(R.id.dialog_alert_msg)).setText(message);
        view.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAlertDialog.this.dismissAllowingStateLoss();
            }
        });
        return view;
    }

    public void setDialogTitleAndMessage(String message){
//        this.title=title;
        this.message=message;
    }

    public static void showAlert(FragmentManager fm, String msg, DialogInterface.OnDismissListener listener){
        MyAlertDialog ld=new MyAlertDialog();
        ld.setDialogTitleAndMessage(msg);
        ld.listener=listener;
        ld.show(fm,"alert");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener!=null)
            listener.onDismiss(dialog);
    }
}