package com.edencity.customer.user.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.pojo.FuncResult;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ResUtils;
import com.edencity.customer.util.ToastUtil;
import com.edencity.customer.view.LoadingDialog;
import com.umeng.message.PushAgent;

import me.yokeyword.fragmentation.SupportActivity;

public class UpdateUserNameActivity extends SupportActivity {

    /**
     * 保存
     */
    private TextView mSave;
    private EditText mEtName;
    /**
     * 格式有误，请重新填写
     */
    private MyNormalTextView mTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_update_user_name);
        initView();
        initLogic();
    }

    private void initLogic() {
        mSave.setOnClickListener(v -> update());

        mEtName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //当输入框内容长度改变显示按钮,按钮图片自己添加
                mEtName.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, App.defaultApp().getResources().getDrawable(R.mipmap.closee), null);

                //输入框字符删除完，按钮消失
                if (charSequence.length() == 0) {
                    mEtName.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, null);
                    mTag.setVisibility(View.GONE);
                }

            }

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                String string = ResUtils.getString(R.string.rule_password);
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    String s = c + "";
                    if (string.contains(s)){
                        String replace = str.replace(s, "");
                        mEtName.setText(replace);
                        mEtName.setSelection(replace.length());
                    }
                }


                //删除按钮的点击事件
                mEtName.setOnTouchListener((view, motionEvent) -> {
                    //获取到下标为2，也就是drawableRight的图片
                    Drawable drawables = mEtName.getCompoundDrawables()[2];
                    //如果为空说明未设置drawableRight的图片,直接返回
                    if (drawables == null) {
                        return false;
                    }
                    //如果用户不是点击的图片，也返回
                    if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                        return false;
                    }
                    //当发生点击事件的X轴坐标在 超过了输入框字符长度加上与到图片的距离的长度
                    //即点击事件发生在图片上，删除文字，随后删除按钮
                    if (motionEvent.getX() > mEtName.getWidth()
                            - mEtName.getPaddingRight()
                            - drawables.getIntrinsicWidth()) {
                        mEtName.setText("");
                        mEtName.setCompoundDrawablesWithIntrinsicBounds(null,
                                null, null, null);
                    }
                    return false;

                });
            }
        });
    }

    private void update() {
        String name = mEtName.getText().toString().trim();

        if (name.length()<1 || name.length()>12){
            mTag.setVisibility(View.VISIBLE);
        }else{
            if (name.length()==1){
                if (ResUtils.getString(R.string.register_name_).contains(name)){
                    mTag.setVisibility(View.VISIBLE);
                    return;
                }
            }
            int a = 0;
            for (int i = 0; i < name.length(); i++) {
                String c = name.charAt(i)+"";
                if (ResUtils.getString(R.string.register_name_).contains(c)){
                    a++;
                }else{
                    a= a+2;
                }
            }
            if (a>12){
                mTag.setVisibility(View.VISIBLE);
                return;
            }
            mTag.setVisibility(View.GONE);
            LoadingDialog.showLoading(getSupportFragmentManager());
            App.execute(()->{
                final FuncResult fr=App.webService().updateUserMsg(null,null,
                        mEtName.getText().toString().trim());
                runOnUiThread(()->{
                    LoadingDialog.hideLoading();
                    if (fr.code==0){
                        Log.e("user",fr.toString());
                        AdiUtils.showToast("修改成功");
                        Intent intent = new Intent();
                        intent.putExtra("namee",mEtName.getText().toString().trim());
                        setResult(400,intent);
                        finish();
                    }else if (fr.code==1){
                        AdiUtils.showToast(fr.msg);

                        App.defaultApp().saveUserMsg(null);
                        Intent intent = new Intent(UpdateUserNameActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else {
                        ToastUtil.showToast(UpdateUserNameActivity.this,fr.msg==null?"更新用户信息出错，请重试":fr.msg);
                    }
                });
            }

            );
        }
    }

    private void initView() {
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(v -> finish());
        mSave = (TextView) findViewById(R.id.save);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtName.setText(getIntent().getStringExtra("name"));
        mEtName.requestFocus();
        mTag = (MyNormalTextView) findViewById(R.id.tag_text);


    }
}
