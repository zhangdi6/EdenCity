package com.edencity.customer.entity;

public class BaseDebug {

    private String title;
    private String url;
    private int resource;
    private String subtitle;
    private boolean checked ;
    private int img_def ;
    private int img_check ;
    private int icon_def;
    private int icon_check ;

    public BaseDebug(String title, String url, String subtitle, boolean checked) {
        this.title = title;
        this.url = url;
        this.checked = checked;
        this.subtitle = subtitle;
    }

    public BaseDebug(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public BaseDebug(String title, String url, int resource, String subtitle) {
        this.title = title;
        this.url = url;
        this.resource = resource;
        this.subtitle = subtitle;
    }
    public BaseDebug( int resource,String title, String subtitle) {
        this.title = title;
        this.resource = resource;
        this.subtitle = subtitle;
    }

    public BaseDebug(String title, int img_check, int img_def, boolean checked , int icon_check ,int icon_def) {
        this.title = title;
        this.img_check = img_check;
        this.img_def = img_def;
        this.checked = checked;
        this.icon_check = icon_check;
        this.icon_def = icon_def;
    }

    public BaseDebug(String title, String subtitle, String url) {
        this.title = title;
        this.url = url;
        this.subtitle = subtitle;
    }

    public BaseDebug(String title, int resource) {
        this.title = title;
        this.resource = resource;
    }

    public boolean isChecked() {
        return checked;
    }

    public int getImg_check() {
        return img_check;
    }

    public int getIcon_check() {
        return icon_check;
    }

    public int getIcon_def() {
        return icon_def;
    }

    public void setIcon_check(int icon_check) {
        this.icon_check = icon_check;
    }

    public void setIcon_def(int icon_def) {
        this.icon_def = icon_def;
    }

    public int getImg_def() {
        return img_def;
    }

    public void setImg_check(int img_check) {
        this.img_check = img_check;
    }

    public void setImg_def(int img_def) {
        this.img_def = img_def;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
