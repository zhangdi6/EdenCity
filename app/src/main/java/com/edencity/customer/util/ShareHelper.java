package com.edencity.customer.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import com.edencity.customer.R;

import java.io.File;

/**
 * @anthor: Ardy
 * @time: 2017/8/4
 * @tel: 17630360083
 * @desc:
 */

public class ShareHelper {
    public static final String KEY_FROM_ARTICLE = "key_from_article";//从文章发起的分享
    public static final String KEY_FROM_QUESTION = "key_from_question";//从问题发起的分享
    public static final String KEY_FROM_NEWS = "key_from_news";//从咨询发起的分享
    public static final String KEY_FROM_PREFECTURE = "key_from_prefecture";//从专区发起的分享


    public static void shareUserPlatform(Activity ctx, String title, boolean isCutTitle, String content, String fromUser,
                                         String imageUrl, String url, SHARE_MEDIA platform, UMShareListener umShareListener) {
        if (platform.equals(SHARE_MEDIA.QQ)) {//QQ
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                shareQQPlatform(ctx, title, isCutTitle, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装QQ客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.QZONE)) {//QZONE
            if (UMShareAPI.get(ctx).isInstall(ctx, SHARE_MEDIA.QQ)) {
                shareQzonePlatform(ctx, title, isCutTitle, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装QQ客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.WEIXIN)) {//WEIXIN
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                shareWeiXinPlatform(ctx, title, isCutTitle, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装微信客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.WEIXIN_CIRCLE)) {//WEIXIN_CIRCLE
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                shareWeiXinCirclePlatform(ctx, title, isCutTitle, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装微信客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.SINA)) {//SINA
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                shareUserSinaPlatform(ctx, title, content, fromUser, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装微博客户端呢！");
            }
        }
    }

    public static void shareUserSinaPlatform(Activity ctx, String title, String content, String fromUser, String imageUrl,
                                             String url, SHARE_MEDIA platform, UMShareListener umShareListener) {
        String shareText = "";
        shareText = fromUser + url;

        if (!TextUtils.isEmpty(imageUrl)) {
            UMImage umImage = new UMImage(ctx, imageUrl);
            new ShareAction(ctx)
                    .withText(shareText)
                    .withMedia(umImage)
                    .setPlatform(platform)
                    .setCallback(umShareListener).share();
        } else {
            UMImage umImage = new UMImage(ctx, R.mipmap.ic_launcher);
            new ShareAction(ctx)
                    .withText(shareText)
                    .withMedia(umImage)
                    .setPlatform(platform)
                    .setCallback(umShareListener).share();
        }
    }

    /**
     * @authorc: Ardy
     * @time: 2017/8/4
     * @desc: 分享工具类
     * isCutTitle 是否截取标题，问题进行截取，文章和资料不进行截取
     */
    public static void sharePlatform(Activity ctx, String title, boolean isCutTitle, String content, String fromUser, boolean isOwner, String type, String imageUrl, String url, boolean hasQuestionRed, SHARE_MEDIA platform, UMShareListener umShareListener) {
        if (platform.equals(SHARE_MEDIA.QQ)) {//QQ
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                content = content.replace("，", ",");
                shareQQPlatform(ctx, title, isCutTitle, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装QQ客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.QZONE)) {//QZONE
            if (UMShareAPI.get(ctx).isInstall(ctx, SHARE_MEDIA.QQ)) {
                shareQzonePlatform(ctx, title, isCutTitle, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装QQ客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.WEIXIN)) {//WEIXIN
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                shareWeiXinPlatform(ctx, title, isCutTitle, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装微信客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.WEIXIN_CIRCLE)) {//WEIXIN_CIRCLE
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                shareWeiXinCirclePlatform(ctx, title, isCutTitle, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装微信客户端呢！");
            }
        }
    }

    /**
     * @authorc: Ardy
     * @time: 2017/8/4
     * @desc: 分享工具类
     */
    public static void sharePlatformFromWeb(Activity ctx, String title, String content, String imageUrl, String url, SHARE_MEDIA platform, UMShareListener umShareListener) {
        if (platform.equals(SHARE_MEDIA.QQ)) {//QQ
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                shareQQPlatform(ctx, title, false, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装QQ客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.QZONE)) {//QZONE
            if (UMShareAPI.get(ctx).isInstall(ctx, SHARE_MEDIA.QQ)) {
                shareQzonePlatform(ctx, title, false, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装QQ客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.WEIXIN)) {//WEIXIN
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                shareWeiXinPlatform(ctx, title, false, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装微信客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.WEIXIN_CIRCLE)) {//WEIXIN_CIRCLE
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                shareWeiXinCirclePlatform(ctx, title, false, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装微信客户端呢！");
            }
        } else if (platform.equals(SHARE_MEDIA.SINA)) {//SINA
            if (UMShareAPI.get(ctx).isInstall(ctx, platform)) {
                shareSinaPlatformFromWeb(ctx, title, content, imageUrl, url, platform, umShareListener);
            } else {
                AdiUtils.showToast("您还没安装微博客户端呢！");
            }
        }
    }


    /**
     * @authorc: Ardy
     * @time: 2017/8/4
     * @desc: 分享QQ
     */
    public static void shareQQPlatform(Activity ctx, String title, boolean isCutTitle, String content, String imageUrl, String url, SHARE_MEDIA platform, UMShareListener umShareListener) {
        UMImage umImage = new UMImage(ctx, R.mipmap.ic_launcher);
        if (!TextUtils.isEmpty(imageUrl)) {
            umImage = new UMImage(ctx, imageUrl);
        }
        if (title.length() > 30 && isCutTitle) {
            title = title.substring(0, 30) + "...";
        }
        UMWeb web = new UMWeb(url);
        web.setThumb(umImage);
        web.setTitle(title);
        web.setDescription(content);
        new ShareAction(ctx)
                .withMedia(web)
                .setPlatform(platform)
                .setCallback(umShareListener).share();
    }

    /**
     * @authorc: Ardy
     * @time: 2017/8/4
     * @desc: 分享Qzone
     */
    public static void shareQzonePlatform(Activity ctx, String title, boolean isCutTitle, String content, String imageUrl, String url, SHARE_MEDIA platform, UMShareListener umShareListener) {
        UMImage umImage = new UMImage(ctx, R.mipmap.ic_launcher);
        if (!TextUtils.isEmpty(imageUrl)) {
            umImage = new UMImage(ctx, imageUrl);
        }
        if (title.length() > 30 && isCutTitle) {
            title = title.substring(0, 30) + "...";
        }
        UMWeb web = new UMWeb(url);
        web.setThumb(umImage);
        web.setTitle(title);
        web.setDescription(content);
        new ShareAction(ctx)
                .withMedia(web)
                .setPlatform(platform)
                .setCallback(umShareListener).share();
    }

    /**
     * @authorc: Ardy
     * @time: 2017/8/4
     * @desc: 分享WeiXin
     */
    public static void shareWeiXinPlatform(Activity ctx, String title, boolean isCutTitle, String content, String imageUrl, String url, SHARE_MEDIA platform, UMShareListener umShareListener) {
        UMImage umImage = new UMImage(ctx, R.mipmap.ic_launcher);
        if (!TextUtils.isEmpty(imageUrl)) {
            umImage = new UMImage(ctx, imageUrl);
        }
        if (title.length() > 30 && isCutTitle) {
            title = title.substring(0, 30) + "...";
        }
        UMWeb web = new UMWeb(url);
        web.setThumb(umImage);
        web.setTitle(title);
        web.setDescription(content);
        new ShareAction(ctx)
                .withMedia(web)
                .setPlatform(platform)
                .setCallback(umShareListener).share();
    }

    public static void shareWxPicture(Activity ctx, String title, File file,SHARE_MEDIA platform, UMShareListener umShareListener) {
        UMImage umImage = new UMImage(ctx, file);
        umImage.compressFormat = Bitmap.CompressFormat.JPEG;
        new ShareAction(ctx)
                .withText(title)
                .withMedia(umImage)
                .setPlatform(platform)
                .setCallback(umShareListener)
                .share();

    }

    /**
     * @authorc: Ardy
     * @time: 2017/8/4
     * @desc: 分享WeiXinCircle
     */
    public static void shareWeiXinCirclePlatform(Activity ctx, String title, boolean isCutTitle, String content, String imageUrl, String url, SHARE_MEDIA platform, UMShareListener umShareListener) {
        UMImage umImage = new UMImage(ctx, R.mipmap.ic_launcher);
        if (!TextUtils.isEmpty(imageUrl)) {
            umImage = new UMImage(ctx, imageUrl);
        }
        if (title.length() > 30 && isCutTitle) {
            title = title.substring(0, 30) + "...";
        }
        UMWeb web = new UMWeb(url);
        web.setThumb(umImage);
        web.setTitle(title);
        web.setDescription(content);
        new ShareAction(ctx)
                .withMedia(web)
                .setPlatform(platform)
                .setCallback(umShareListener).share();
    }


    /**
     * @authorc: Ardy
     * @time: 2017/8/4
     * @desc: 分享sina
     */
    public static void shareSinaPlatformFromWeb(Activity ctx, String title, String content, String imageUrl, String url, SHARE_MEDIA platform, UMShareListener umShareListener) {
        if (!TextUtils.isEmpty(imageUrl)) {
            UMImage umImage = new UMImage(ctx, imageUrl);
            new ShareAction(ctx)
                    .withText(title)
                    .withMedia(umImage)
                    .setPlatform(platform)
                    .setCallback(umShareListener).share();
        } else {
            UMImage umImage = new UMImage(ctx, R.mipmap.ic_launcher);
            new ShareAction(ctx)
                    .withText(title)
                    .withMedia(umImage)
                    .setPlatform(platform)
                    .setCallback(umShareListener).share();
        }
    }


}
