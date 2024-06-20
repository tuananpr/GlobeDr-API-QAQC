package com.apis.globedr.model.step.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.cucumber.java.mk_latn.No;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMS {
    @JsonAlias({"userSig", "userSignature"})
    private String userSig;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String deviceId;
    private String qrCode;
    private Integer pageDashboard;
    private Integer language;
    private List<String> specialtiesName;

    public void setSpecialtiesName(Object info){
        if(info instanceof String) {
            specialtiesName = Arrays.asList(info.toString().split(","));
        }else{
            specialtiesName = (List<String>) info;
        }
    }
}
