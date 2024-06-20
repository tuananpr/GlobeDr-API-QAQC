package com.apis.globedr.model.step.connection;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionMS {
    private String name;
    private String toUserSig;
    private String userSig;

    private Integer orgType;
    private Boolean getScore;
    private Boolean allowRequestAppt;
    private Boolean allowOrder;
    private Boolean notLoadShared;
    private String subSig;

    @JsonUnwrapped
    Page page;
}
