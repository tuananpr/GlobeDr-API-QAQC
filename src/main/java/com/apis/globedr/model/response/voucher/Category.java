package com.apis.globedr.model.response.voucher;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer id;
    private String signature;
    private String name;
}
