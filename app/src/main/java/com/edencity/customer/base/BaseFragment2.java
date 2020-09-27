package com.edencity.customer.base;

import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.View;

import me.yokeyword.fragmentation.SupportFragment;


public class BaseFragment2 extends SupportFragment {
    /**
     * 返回上一步
     */
    public void backToParent(){
        FragmentManager fm=getFragmentManager();
        if (fm!=null && !fm.isStateSaved()){
            pop();
        }
    }

    /**
     * 接收视图中控件的点击事件。子类应根据视图的id进行判断和处理
     * @param view 被点击的视图
     * @return true:if has handled  false: not handle
     */
    public void onViewItemClicked(View view){

    }

    /**
     * 处理异步请求，如果需要处理的话，覆盖该函数
     * @param msg
     */
    public void handleMessage(Message msg){

    }
}
