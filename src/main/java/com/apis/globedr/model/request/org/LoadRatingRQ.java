package com.apis.globedr.model.request.org;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoadRatingRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private List<String> ratingSigs;
    private String userName;
    private Integer gender;
    private Integer score;
    private String fromDate;
    private String toDate;
    @JsonUnwrapped
    Page page;



}
