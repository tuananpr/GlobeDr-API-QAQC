package com.apis.globedr.model.step.health.healthHistory;

import com.apis.globedr.enums.QuestionGroupType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthHistoryMS {
    private String userSig;
    private Integer groupType;

    public HealthHistoryMS setGroupType(Object value) {
        this.groupType = QuestionGroupType.value(value);
        return this;
    }

}
