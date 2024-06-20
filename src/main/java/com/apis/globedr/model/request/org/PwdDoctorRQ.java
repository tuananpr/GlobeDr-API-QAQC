package com.apis.globedr.model.request.org;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PwdDoctorRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    @JsonAlias({"userSig", "userSignature"})
    private String userSig;
    private String password;

}
