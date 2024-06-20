package com.apis.globedr.model.response.voucher;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {
    private Integer id;
    private Integer cardId;
    private Integer type;
    private String signature;
    private String name;
    private String description;
    private String imgHot;
    private String imgNormal;
    private String expiredDate;
    private String refundDate;
    private String cardSig;
    private String link;
    private boolean isSelect;
    private boolean isHot;
    private boolean isShip;
}