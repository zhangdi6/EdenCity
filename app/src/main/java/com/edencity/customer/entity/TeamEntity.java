package com.edencity.customer.entity;

/* Created by AdScar
    on 2020/5/28.
      */

import java.util.List;

public class TeamEntity {

    private List<EntryTeamsBean> entryTeams;

    public List<EntryTeamsBean> getEntryTeams() {
        return entryTeams;
    }

    public void setEntryTeams(List<EntryTeamsBean> entryTeams) {
        this.entryTeams = entryTeams;
    }

    public static class EntryTeamsBean {
        /**
         * teamName : 德克士
         * entryTime : 2020-05-30
         * teamDetailUrl : http://www.baidu.com
         * teamDesc : null
         * recommend : 1
         * entryTeamId : 55b657206532452cb9ce8379cb5c61d3
         * teamHeadPicture : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/entryTeam/teamHeadPicture/55b657206532452cb9ce8379cb5c61d3/teamHeadPicture/1590629945987_1586744216057_德克士-门店门面.jpg
         */

        private String teamName;
        private String entryTime;
        private String teamDetailUrl;
        private String teamDesc;
        private String recommend;
        private String entryTeamId;
        private String teamHeadPicture;

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getEntryTime() {
            return entryTime;
        }

        public void setEntryTime(String entryTime) {
            this.entryTime = entryTime;
        }

        public String getTeamDetailUrl() {
            return teamDetailUrl;
        }

        public void setTeamDetailUrl(String teamDetailUrl) {
            this.teamDetailUrl = teamDetailUrl;
        }

        public String getTeamDesc() {
            return teamDesc;
        }

        public void setTeamDesc(String teamDesc) {
            this.teamDesc = teamDesc;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public String getEntryTeamId() {
            return entryTeamId;
        }

        public void setEntryTeamId(String entryTeamId) {
            this.entryTeamId = entryTeamId;
        }

        public String getTeamHeadPicture() {
            return teamHeadPicture;
        }

        public void setTeamHeadPicture(String teamHeadPicture) {
            this.teamHeadPicture = teamHeadPicture;
        }
    }
}
