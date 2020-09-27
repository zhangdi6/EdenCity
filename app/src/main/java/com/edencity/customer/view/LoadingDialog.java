package com.edencity.customer.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edencity.customer.R;

public class LoadingDialog extends DialogFragment {
    private static LoadingDialog progressDialog;

    public LoadingDialog(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.mydialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        View v=inflater.inflate(R.layout.dialog_loading,container,false);
        return v;
    }

    public static void showLoading(FragmentManager manager){
        if (progressDialog!=null){
            if (!progressDialog.isVisible()) {
                progressDialog.show(manager, "progress");
            }

        }else {
            progressDialog=new LoadingDialog();
            progressDialog.show(manager,"progress");
        }
    }

    public static void hideLoading(){
        if (progressDialog!=null){
            progressDialog.dismissAllowingStateLoss();
            progressDialog=null;
        }
    }
}

