package com.apis.globedr.model.step.review;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewMS {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String orgName;
    private Integer score;
    private String review;
    private List<String> ratingSigs;
    private String userName;
    private Integer gender;
    private String fromDate;
    private String toDate;
    @JsonUnwrapped
    Page page;

}
