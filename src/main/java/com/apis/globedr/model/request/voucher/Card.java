package com.apis.globedr.model.request.voucher;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String code;
    private String expiredDate;
}
