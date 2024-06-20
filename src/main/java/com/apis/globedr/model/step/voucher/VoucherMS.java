package com.apis.globedr.model.step.voucher;

import com.apis.globedr.enums.VoucherStatus;
import com.apis.globedr.model.general.*;
import com.apis.globedr.model.general.file.File;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class VoucherMS {


    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String categorySig;
    private String voucherSig;
    private String categoryName;
    private String description;
    @JsonAlias({"name", "voucherName"})
    private String name;
    private String note;
    private String country;
    private String supportPhone;
    private String link;
    private String city;
    private String mostComment;
    private String mostReview;
    private String searchText;
    private Integer voucherStatus;
    private Integer language;
    private Integer type;


    private Integer weight;
    private Integer weightHot;
    private Integer weightPopular;
    @JsonAlias({"isLoadMyVoucher"})
    private boolean isLoadMyVoucher;
    @JsonAlias({"isSendNoti"})
    private boolean isSendNoti;
    @JsonAlias({"isHot"})
    private boolean isHot;
    @JsonAlias({"isNew"})
    private boolean isNew;
    @JsonAlias({"isPopular"})
    private boolean isPopular;
    @JsonAlias({"isAdminLoad"})
    private boolean isAdminLoad;
    @JsonAlias({"isOneUse"})
    private boolean isOneUse;
    @JsonAlias({"isShip"})
    private boolean isShip;
    private String cardSig;
    private String shipAddress;
    private Boolean isAddMember;

    private List<String> branchSigs;
    private List<String> categorySigs;
    private List<String> voucherSigs ;

    @JsonUnwrapped
    private Geo geo;

    @JsonUnwrapped(suffix = "Popular")
    FilterDate popular;

    @JsonUnwrapped
    FilterDate date;
    @JsonUnwrapped(suffix = "Hot")
    FilterDate hot;

    @JsonUnwrapped
    Page page;

    @JsonUnwrapped
    File image;

    public void setVoucherStatus(Object status) {
        this.voucherStatus = VoucherStatus.value(status);
    }

    public void setImage(String image) {
        this.image = FileFactory.getFile(image);
    }



}
