package com.example.trending.Bean;

import java.util.List;

public class ItemsBean {

    /**
     * "author": "xingshaocheng",
     * "name": "architect-awesome",
     * "avatar": "https://github.com/xingshaocheng.png",
     * "url": "https://github.com/xingshaocheng/architect-awesome",
     * "description": "后端架构师技术图谱",
     * "language": "Go",
     * "languageColor": "#3572A5",
     * "stars": 7333,
     * "forks": 1546,
     * "currentPeriodStars": 1528,
     * "builtBy": [
     * {
     * "href": "https://github.com/viatsko",
     * "avatar": "https://avatars0.githubusercontent.com/u/376065",
     * "username": "viatsko"
     * }
     * ]ps://avatars.githubusercontent.com/u/21008209?s=40&v=4","https://avatars.githubusercontent.com/u/17448306?s=40&v=4","https://avatars.githubusercontent.com/u/22535595?s=40&v=4","https://avatars.githubusercontent.com/in/15368?s=40&v=4","https://avatars.githubusercontent.com/u/42513350?s=40&v=4"]
     */

    private String author;
    private String name;
    private String avatar;
    private String url;
    private String description;
    private String language;
    private String languageColor;
    private String stars;
    private String forks;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getForks() {
        return forks;
    }

    public void setForks(String forks) {
        this.forks = forks;
    }

    public String getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(String currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }

    public BuiltByBean getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(BuiltByBean builtBy) {
        this.builtBy = builtBy;
    }

    private String currentPeriodStars;
    private BuiltByBean builtBy;


    public static class BuiltByBean {
        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}


