package com.apis.globedr.model.request.voucher;

import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class UploadVoucherHotIconRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String voucherSig;
    @JsonUnwrapped
    ImageFile image;


}
