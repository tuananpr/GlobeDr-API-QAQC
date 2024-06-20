package com.apis.globedr.model.step.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointHistoryMS {
    private String userSig;
    private Integer page;
    private String name;
}
