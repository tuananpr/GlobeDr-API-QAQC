package com.apis.globedr.services.database.entities;

public class AppScreenGuide {
    private Integer appScreen;
    private Integer action;
    private Integer appScreenForAction;
    private Integer isFirst;
    private Integer availabe;

    public AppScreenGuide(Integer appScreen, Integer action, Integer appScreenForAction, Integer isFirst, Integer availabe) {
        this.appScreen = appScreen;
        this.action = action;
        this.appScreenForAction = appScreenForAction;
        this.isFirst = isFirst;
        this.availabe = availabe;
    }

    public Integer getAppScreen() {
        return appScreen;
    }

    public void setAppScreen(Integer appScreen) {
        this.appScreen = appScreen;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getAppScreenForAction() {
        return appScreenForAction;
    }

    public void setAppScreenForAction(Integer appScreenForAction) {
        this.appScreenForAction = appScreenForAction;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getAvailabe() {
        return availabe;
    }

    public void setAvailabe(Integer availabe) {
        this.availabe = availabe;
    }
}
