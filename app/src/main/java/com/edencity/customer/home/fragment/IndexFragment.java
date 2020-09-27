package com.edencity.customer.home.fragment;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.R;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.user.fragment.UserFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends BaseFragment2 {

    @BindView(R.id.tab_radiogroup)
    RadioGroup radio;
    @BindView(R.id.frag)
    FrameLayout mFrag;
    private FragmentManager manager;


    public IndexFragment() {
        // Required empty public constructor
    }

    public static IndexFragment newInstance() {
        return new IndexFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this, view);
        manager = getChildFragmentManager();
        initView();
        return view;
    }



   /* @Override
    protected int get(R.layout.fragment_index) {
        return ;
    }*/

    private void initView() {
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radiobutton_firstpage:
                        addFragment(manager, HomeFragment4.class, R.id.frag, null);
                        Window window =getActivity().getWindow();
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);;
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );
                        }else {
                            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                        }

                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.TRANSPARENT);
                        break;
                    case R.id.radiobutton_notification:
                        addFragment(manager, VipCenterFragment.class, R.id.frag, null);
                        StatusBarCompat.changeToLightStatusBar(getActivity());
                        break;
                    case R.id.radiobutton_shopcart:
                        addFragment(manager, UserFragment.class, R.id.frag, null);
                        StatusBarCompat.changeToLightStatusBar(getActivity());
                        break;

                }
            }
        });
        radio.check(R.id.radiobutton_firstpage);
    }

    private SupportFragment addFragment(FragmentManager manager, Class<? extends SupportFragment> aClass, int containerId, Bundle args) {
        String tag = aClass.getName();
        Fragment fragment = manager.findFragmentByTag(tag);
        FragmentTransaction transaction = manager.beginTransaction(); // 开启一个事务

        if (fragment == null) {// 没有添加
            try {
                fragment = aClass.newInstance(); // 通过反射 new 出一个 fragment 的实例

                transaction.add(containerId, fragment, tag);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isAdded()) {
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }

            } else {
                transaction.add(containerId, fragment, tag);
            }
        }

        if (fragment != null) {
            fragment.setArguments(args);
            hideBeforeFragment(manager, transaction, fragment);
            transaction.commit();
            return (SupportFragment) fragment;
        }
        return null;
    }

    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentFragment) {

        List<Fragment> fragments = manager.getFragments();

        for (Fragment fragment : fragments) {
            if (fragment != currentFragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
    }

    @Override
    public void onViewItemClicked(View view) {

    }
}
