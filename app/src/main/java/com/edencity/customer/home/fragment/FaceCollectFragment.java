package com.edencity.customer.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment;
import com.edencity.customer.home.activity.AuthenticationActivity;
import com.edencity.customer.util.ButtonUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaceCollectFragment extends BaseFragment {


    private String name;
    private String card;

    public FaceCollectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(getLayoutId(), container, false);
        Bundle arguments = getArguments();
        if (arguments!=null){
            name = arguments.getString("name");
            card = arguments.getString("card");
        }
        initView(inflate);
        return inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_face_collect;
    }

    private void initView(View inflate) {

        TextView btn_go = inflate.findViewById(R.id.btn_go);
        TextView tv_name = inflate.findViewById(R.id.tv_name);
        TextView tv_card = inflate.findViewById(R.id.tv_card);
        String MyName = null;
        if (name.length()==2){
             MyName = name.replace(name.substring(0,name.length()-1),"*");
        }else if (name.length()==3){
             MyName  = name.replace(name.substring(0,name.length()-1),"**");
        }else if (name.length()==4){
             MyName  = name.replace(name.substring(0,name.length()-1),"***");
        }
        tv_name.setText(MyName);
        tv_card.setText(card.replace(card.substring(0,card.length()-4),"**************"));

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_go)){
                    ((AuthenticationActivity)getActivity()).changePage(3,
                            name,
                            card);
                }
            }
        });

    }

}
