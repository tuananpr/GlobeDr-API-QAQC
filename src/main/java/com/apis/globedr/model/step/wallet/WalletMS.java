package com.apis.globedr.model.step.wallet;

import com.apis.globedr.enums.PointActivity;
import com.apis.globedr.enums.WalletStatus;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletMS {
    @JsonUnwrapped
    FilterDate filterDate;
    private Integer walletStatus;
    private Integer pointActivity;

    @JsonUnwrapped
    Page page;

    public void setWalletStatus(Object value){
        this.walletStatus = WalletStatus.value(value);
    }

    public void setPointActivity(Object value) {
        this.pointActivity = PointActivity.value(value);
    }
}

