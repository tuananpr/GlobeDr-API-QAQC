package com.apis.globedr.model.request.orgMember;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMemberRQ {
    private String orgSig;
    private String qrCode;

}
