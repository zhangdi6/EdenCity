package com.edencity.customer.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.login.activity.LoginActivity;


public class AdiUtils {

    //输入框根据长度显示和添加清除按钮，
    // mode分为输入账号等不需要替换为星号的形式和密码等需要变换字符的形式
    //view是指定的点击切换显示隐藏的控件
    public static void showDeleteButton(final EditText editText, int mode, CheckBox view) {


        //禁止回车键
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });

        //输入账号
        if (mode == 1) {
            //最大长度为11
            editText.setMaxEms(11);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER );
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    //当输入框内容长度改变显示按钮,按钮图片自己添加
                    editText.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, App.defaultApp().getResources().getDrawable(R.mipmap.closee), null);

                    //输入框字符删除完，按钮消失
                    if (charSequence.length() == 0) {
                        editText.setCompoundDrawablesWithIntrinsicBounds(null,
                                null, null, null);
                    }

                }

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void afterTextChanged(Editable editable) {
                    //删除按钮的点击事件
                    editText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            //获取到下标为2，也就是drawableRight的图片
                            Drawable drawables = editText.getCompoundDrawables()[2];
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
                            if (motionEvent.getX() > editText.getWidth()
                                    - editText.getPaddingRight()
                                    - drawables.getIntrinsicWidth()) {
                                editText.setText("");
                                editText.setCompoundDrawablesWithIntrinsicBounds(null,
                                        null, null, null);
                            }
                            return false;

                        }
                    });
                }
            });
        } else if (mode == 2) {

           //最大长度为16
         /*   editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);*/
            editText.setTransformationMethod(new PasswordChange());
            // 点击指定的view去控制密码显示隐藏
            if (view!=null){
                view.setChecked(false);

                view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        if (b){
                            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            editText.setSelection(editText.getText().toString().length());
                        }else{
                            editText.setTransformationMethod(new PasswordChange());
                            editText.setSelection(editText.getText().toString().length());
                        }
                    }
                });
            }


            editText.addTextChangedListener(new TextWatcher() {
                String tmp = "";
                String digits = ResUtils.getString(R.string.register_name_);
                @Override
                public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                    tmp = s.toString();
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    //当输入框内容长度改变显示按钮,按钮图片自己添加
                    editText.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, App.defaultApp().getResources().getDrawable(R.mipmap.closee), null);

                    //输入框字符删除完，按钮消失
                    if (charSequence.length() == 0) {
                        editText.setCompoundDrawablesWithIntrinsicBounds(null,
                                null, null, null);
                    }

                }

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void afterTextChanged(Editable editable) {
                    String str = editable.toString();
                    if(str.equals(tmp)){
                        return;
                    }

                    StringBuffer sb = new StringBuffer();
                    for(int i = 0; i < str.length(); i++){
                        if(digits.indexOf(str.charAt(i)) >= 0){
                            sb.append(str.charAt(i));
                        }
                    }
                    tmp = sb.toString();
                    editText.setText(tmp);
                    editText.setSelection(editable.length());


                    //删除按钮的点击事件
                    editText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            //获取到下标为2，也就是drawableRight的图片
                            Drawable drawables = editText.getCompoundDrawables()[2];
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
                            if (motionEvent.getX() > editText.getWidth()
                                    - editText.getPaddingRight()
                                    - drawables.getIntrinsicWidth()) {
                                editText.setText("");
                                editText.setCompoundDrawablesWithIntrinsicBounds(null,
                                        null, null, null);
                            }
                            return false;

                        }
                    });
                }
            });
        } else if (mode==3){
            //最大长度为4  验证码
            editText.setMaxEms(4);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER );
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
           /* editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    //当输入框内容长度改变显示按钮,按钮图片自己添加
                    editText.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, App.defaultApp().getResources().getDrawable(R.mipmap.close), null);

                    //输入框字符删除完，按钮消失
                    if (charSequence.length() == 0) {
                        editText.setCompoundDrawablesWithIntrinsicBounds(null,
                                null, null, null);
                    }

                }

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void afterTextChanged(Editable editable) {
                    //删除按钮的点击事件
                    editText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            //获取到下标为2，也就是drawableRight的图片
                            Drawable drawables = editText.getCompoundDrawables()[2];
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
                            if (motionEvent.getX() > editText.getWidth()
                                    - editText.getPaddingRight()
                                    - drawables.getIntrinsicWidth()) {
                                editText.setText("");
                                editText.setCompoundDrawablesWithIntrinsicBounds(null,
                                        null, null, null);
                            }
                            return false;

                        }
                    });
                }
            });*/
        }


    }



    //输入框提示语点击即消失
    public static void HintListener2(final EditText editText, final String msg, final String phone) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    editText.setHint("");
                    editText.setText(phone);
                } else {
                    editText.setHint(msg);
                }
            }
        });
    } //输入框提示语点击即消失
    public static void HintListener(final EditText editText, final String msg) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    editText.setHint("");
                } else {
                    editText.setHint(msg);
                }
            }
        });
    }

    //验证手机号码格式
    public static boolean isPhone(String number) {

        // "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，
        // "[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"
        // 代表后面是可以是0～9的数字，有9位。

        String num = "^((13[0-9])|(14[5,7,9])|(16[6])|(19[1,5,8,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";

        if (TextUtils.isEmpty(number)) {
            showToast("手机号不能为空");
            return false;
        } else if (!number.matches(num)) {
            showToast("请输入正确手机号");
            return false;
        }
        return true;
    }

    //验证手机号码格式无吐司
    public static boolean isPhoneNoToast(String number) {

        // "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，
        // "[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"
        // 代表后面是可以是0～9的数字，有9位。

        String num = "^((13[0-9])|(14[5,7,9])|(16[6])|(19[1,5,8,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";

        if (TextUtils.isEmpty(number)) {
            return false;
        } else if (!number.matches(num)) {
            return false;
        }
        return true;
    }

    public static void showToast(String msg){
        Toast.makeText(App.defaultApp(), msg, Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(String msg){
        Toast.makeText(App.defaultApp(), msg, Toast.LENGTH_LONG).show();
    }

    //判断是否属于字母加数字格式，true代表不属于
    public static boolean isNotTrue (String str){
        int a = 0;
        for(int i=0 ; i < str.length();i++){
            if(!Character.isDigit(str.charAt(i)) && !Character.isLetter(str.charAt(i))){   //用char包装类中的判断数字的方法判断每一个字符
                a++;
            }
        }
        if (a>0){
            return true;
        }else{
            return false;
        }
    }


    //验证密码格式
    public static boolean isPassword(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            showToast("密码不能为空");
            return false;
        }
        if (pwd.length() < 6 || pwd.length() > 12) {
            showToast("请输入6~12位密码");
            return false;
        }
        if (isNotTrue(pwd)){
            showToast("密码只支持数字和字母,不可含有其他符号");
            return false;
        }
        if (isLetterDigit(pwd)) {
            showToast("密码不支持纯数字或纯字母");
            return false;
        }
        return true;
    }

    //判断是否是纯数字或纯字母，true代表是纯数字或纯字母
    public static boolean isLetterDigit(String str){
        int isDigit = 0;
        int isLetter = 0;
        for(int i=0 ; i < str.length();i++){
            if(!Character.isDigit(str.charAt(i))){   //用char包装类中的判断数字的方法判断每一个字符
                isDigit ++;
            }
            if (!Character.isLetter(str.charAt(i))){
                isLetter ++;
            }
        }
        if (isDigit ==0 || isLetter==0 ){
            return true;
        }
        return false;
    }
    //验证码格式
    public static boolean isVerify(String verify) {
        if (TextUtils.isEmpty(verify)) {
            showToast("验证码不能为空");
            return false;
        } else if (verify.length() < 4 ) {
            showToast("请输入正确格式的验证码");
            return false;
        }
        return true;
    }

    //验证码格式无吐司
    public static boolean isVerifyNoToast(String verify) {
        if (TextUtils.isEmpty(verify)) {
            return false;
        } else if (verify.length() < 4 ) {
            return false;
        }
        return true;
    }
    String cityJsonStr = "";

    //读取方法
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void setBackgroundAlpha(Context mContext, float bgAlpha) {
//        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
//        lp.alpha = bgAlpha;
//        ((Activity) mContext).getWindow().setAttributes(lp);

        if (bgAlpha == 1f) {
            clearDim((Activity) mContext);
        } else {
            applyDim((Activity) mContext, bgAlpha);
        }
    }

    //处理验证码输入金钱格式(小数点只有两位)
    public static void setEditTextTwoPoint(EditText editText){


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                } else {
                    if (s.length() > 8) {
                        editText.setText(s.subSequence(0, 8));
                        editText.setSelection(s.length() - 1);
                    }
                }

                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        editText.addTextChangedListener(textWatcher);
    }

    @SuppressLint("ObsoleteSdkInt")
    private static void applyDim(Activity activity, float bgAlpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            //activity跟布局
//        ViewGroup parent = (ViewGroup) parent1.getChildAt(0);
            Drawable dim = new ColorDrawable(Color.BLACK);
            dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
            dim.setAlpha((int) (255 * bgAlpha));
            ViewGroupOverlay overlay = parent.getOverlay();
            overlay.add(dim);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private static void clearDim(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            //activity跟布局
//        ViewGroup parent = (ViewGroup) parent1.getChildAt(0);
            ViewGroupOverlay overlay = parent.getOverlay();
            overlay.clear();
        }
    }
    //抓取相册图片
    public static String getPicturePathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) {
            return getPicturePathFromUriAboveApi19(context, uri);
        } else {
            return getPicturePathFromUriBelowAPI19(context, uri);
        }
    }

    private static String getPicturePathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    private static String getPicturePathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // 如果是document类型的 uri, 则通过document id来进行处理
                String documentId = DocumentsContract.getDocumentId(uri);
                if (isMediaDocument(uri)) { // MediaProvider
                    // 使用':'分割
                    String id = documentId.split(":")[1];

                    String selection = MediaStore.Images.Media._ID + "=?";
                    String[] selectionArgs = {id};
                    filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
                } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                    filePath = getDataColumn(context, contentUri, null, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果是 content 类型的 Uri
                filePath = getDataColumn(context, uri, null, null);
            } else if ("file".equals(uri.getScheme())) {
                // 如果是 file 类型的 Uri,直接获取图片对应的路径
                filePath = uri.getPath();
            }
        }
        return filePath;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    //图片压缩
    public static Bitmap compressPicture(String imgPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);
        Logger.e( "onActivityResult: 未压缩之前图片的宽：" + options.outWidth + "--未压缩之前图片的高："
                + options.outHeight + "--未压缩之前图片大小:" + options.outWidth * options.outHeight * 4 / 1024 / 1024 + "M");

        options.inSampleSize = calculateInSampleSize(options, 100, 100);
        Logger.e("onActivityResult: inSampleSize:" + options.inSampleSize);
        options.inJustDecodeBounds = false;
        Bitmap afterCompressBm = BitmapFactory.decodeFile(imgPath, options);
//      //默认的图片格式是Bitmap.Config.ARGB_8888
        Logger.e( "onActivityResult: 图片的宽：" + afterCompressBm.getWidth() + "--图片的高："
                + afterCompressBm.getHeight() + "--图片大小:" + afterCompressBm.getWidth() * afterCompressBm.getHeight() * 4 / 1024 / 1024 + "M");
        return afterCompressBm;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    //退出登录
    public static void loginOut() {
        AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
        Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getSp().remove("userId");
        App.getSp().remove("ticket");
        App.defaultApp().startActivity(intent);
    }

    /**
     * 隐藏输入法键盘
     */
    public static void hideInput(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<Activity>(activity);
        hideInput(weakReference);
    }

    /**
     * 隐藏键盘
     */
    public static void hideInput(WeakReference<Activity> activity) {
        InputMethodManager imm = (InputMethodManager) activity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.get().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.get().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 显示输入法键盘
     */
    public static void showInput(WeakReference<Activity> activity, EditText editText) {
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    public static long getAppVersionCode(Context context) {
        long appVersionCode = 0;
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                appVersionCode = packageInfo.getLongVersionCode();
            } else {
                appVersionCode = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return appVersionCode;
    }

    /**
     * 获取当前app version name
     */
    public static String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return appVersionName;
    }
}
