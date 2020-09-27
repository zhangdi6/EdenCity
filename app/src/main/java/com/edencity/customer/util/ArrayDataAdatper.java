package com.edencity.customer.util;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 紫钢 on 2016/10/25.
 */

public class ArrayDataAdatper<T> extends BaseAdapter{
    private List<T> data;
    private ViewProvider provider;
    private ViewConfigure<T> configure;
    private LayoutInflater inflater;
    private int layoutID;

    /**
     * 单元格使用不同的layout
     * @param data
     * @param provider
     */
    public ArrayDataAdatper(List<T> data,ViewProvider provider){
        this.data=data;
        this.provider=provider;
    }

    /**
     * 单元格使用同样的layout
     * @param data
     * @param inflater
     * @param layoutID
     * @param configure
     */
    public ArrayDataAdatper(List<T> data, LayoutInflater inflater, @LayoutRes int layoutID, ViewConfigure<T> configure){
        this.data=data;
        this.configure=configure;
        this.inflater=inflater;
        this.layoutID=layoutID;
    }


    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater==null || layoutID==0){
            if (provider!=null)
                return provider.providerView(this,i,view,viewGroup);
        }else {
            if (view==null){
                view=inflater.inflate(layoutID,viewGroup,false);
            }
            if (configure!=null)
                configure.configView(view,data.get(i));
            return view;
        }
        return null;
    }
    public interface ViewConfigure<T>{
        public void configView(View convertView, T object);
    }

    public interface ViewProvider{
        public View providerView(ArrayDataAdatper adapter, int position, View convertView, ViewGroup viewGroup);
    }
}
