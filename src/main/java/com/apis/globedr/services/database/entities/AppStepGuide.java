package com.apis.globedr.services.database.entities;

public class AppStepGuide {
    private Integer featureApp;
    private Integer action;
    private Integer featureAppForAction;
    private Integer appScreen;
    private Integer isFirst;

    public AppStepGuide(Integer featureApp, Integer action, Integer featureAppForAction, Integer appScreen, Integer isFirst) {
        this.featureApp = featureApp;
        this.action = action;
        this.featureAppForAction = featureAppForAction;
        this.appScreen = appScreen;
        this.isFirst = isFirst;
    }

    public Integer getFeatureApp() {
        return featureApp;
    }

    public void setFeatureApp(Integer featureApp) {
        this.featureApp = featureApp;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getFeatureAppForAction() {
        return featureAppForAction;
    }

    public void setFeatureAppForAction(Integer featureAppForAction) {
        this.featureAppForAction = featureAppForAction;
    }

    public Integer getAppScreen() {
        return appScreen;
    }

    public void setAppScreen(Integer appScreen) {
        this.appScreen = appScreen;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }
}
