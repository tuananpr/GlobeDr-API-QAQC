package com.apis.globedr.model.request.order;


import com.apis.globedr.model.general.file.ImageFile;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class OrderAttachFileRQ {

    private String orderSig;
    private String orgSig;
    private boolean isResult;

    @JsonUnwrapped
    ImageFile file;

}