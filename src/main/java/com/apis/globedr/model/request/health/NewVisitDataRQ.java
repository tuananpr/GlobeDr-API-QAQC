package com.apis.globedr.model.request.health;


import com.apis.globedr.model.general.VisitData;

public class NewVisitDataRQ {
    private String visitSig;
    private String userSig;
    private String orgSig;
    private String visitDate;
    private String linkSig;
    private int linkId;
    private int createdByType;
    private int createdById;
    private VisitData visitData;
}
