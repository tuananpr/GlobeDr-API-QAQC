package com.apis.globedr.model.request.telemedicine;

public class TelemedicineBusyRQ extends  TeleMedicineInfo {
    private String userSigCaller;
    private Integer receiverType;
    private String channelName;
    private boolean anotherCall;
}