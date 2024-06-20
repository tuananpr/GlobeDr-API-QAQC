package com.apis.globedr.model.request.subAccount;



import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareInfoRQ {
    private String userSig;
    private String orgSig;
    private Integer userManageType;

}
