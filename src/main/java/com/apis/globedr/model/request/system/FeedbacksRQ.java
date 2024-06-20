package com.apis.globedr.model.request.system;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbacksRQ {
    private Integer fromScore;
    private Integer toScore;
    private String userName;
    @JsonUnwrapped
    private Page page;
}

