package com.apis.globedr.model.response.health.healthHistory;

import com.apis.globedr.model.request.health.healthHistory.QuestionItemsRQ;
import lombok.*;

@Getter
@Setter
public class QuestionItemsRS extends QuestionItemsRQ {
    private String itemName;
}
