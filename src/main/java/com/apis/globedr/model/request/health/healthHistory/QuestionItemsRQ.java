package com.apis.globedr.model.request.health.healthHistory;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionItemsRQ {
    private String questionItemSig;
    private Integer itemType;
    private Object answerData;

}