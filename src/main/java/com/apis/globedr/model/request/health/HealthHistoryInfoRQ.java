package com.apis.globedr.model.request.health;

import com.apis.globedr.model.request.health.healthHistory.QuestionItemsRQ;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthHistoryInfoRQ {
    private String userSig;
    List<QuestionItemsRQ> questionItemInfos;
}
