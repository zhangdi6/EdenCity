package com.edencity.customer.entity;

/* Created by AdScar
    on 2020/8/26.
      */

public class CheckUpdateEntity {

    /**
     * version : 2.2
     * isInstall : 1
     * versionDetail : 1，更新了啥啥啥2，新增了啥啥啥3.删除了啥啥啥
     */

    private String version;
    private String isInstall;
    private String versionDetail;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIsInstall() {
        return isInstall;
    }

    public void setIsInstall(String isInstall) {
        this.isInstall = isInstall;
    }

    public String getVersionDetail() {
        return versionDetail;
    }

    public void setVersionDetail(String versionDetail) {
        this.versionDetail = versionDetail;
    }
}
