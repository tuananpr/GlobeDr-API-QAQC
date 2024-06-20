package com.apis.globedr.model.request.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsedPointHistoryDetailRQ {
    private String userSig;
    private Integer page;

}
