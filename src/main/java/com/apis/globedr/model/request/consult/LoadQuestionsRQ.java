package com.apis.globedr.model.request.consult;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.ArrayList;
import java.util.List;

public class LoadQuestionsRQ {
    private String toDate;
    private String emailOrphone;
    private String textSearch;
    private String fromDate;
    private String createdByName;

    private Integer providerId;
    private Integer postId;
    private Integer postStatus;
    private String AuditorSig;
    private String ProviderSig;
    private Integer userMode;
    private Integer auditorId;
    private Integer language;
    private boolean webPlatform;
    private List<Integer> postTypes;
    @JsonUnwrapped
    Page page;


}

