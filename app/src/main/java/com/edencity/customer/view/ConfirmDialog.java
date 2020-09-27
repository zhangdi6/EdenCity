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

public class ConfirmDialog extends DialogFragment {

//    private String title;
    private String message;

    private DialogInterface.OnClickListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.dialog_confirm, container);
//        if (title!=null)
//            ((TextView)view.findViewById(R.id.dialog_alert_title)).setText(title);
        ((TextView)view.findViewById(R.id.dialog_alert_msg)).setText(message);

        View.OnClickListener vcl= view1 -> {
            dismissAllowingStateLoss();
            if (listener!=null){
                listener.onClick(getDialog(), view1.getId()==R.id.btn_ok ? DialogInterface.BUTTON_POSITIVE : DialogInterface.BUTTON_NEGATIVE);
            }
        };
        view.findViewById(R.id.btn_cancel).setOnClickListener(vcl);
        view.findViewById(R.id.btn_ok).setOnClickListener(vcl);

        return view;
    }

    public void setDialogTitleAndMessage(String message){
//        this.title=title;
        this.message=message;
    }

    public static void showConfirm(FragmentManager fm,String msg, DialogInterface.OnClickListener listener){
        ConfirmDialog ld=new ConfirmDialog();
        ld.setDialogTitleAndMessage(msg);
        ld.listener=listener;
        ld.show(fm,"confirm");
    }
}