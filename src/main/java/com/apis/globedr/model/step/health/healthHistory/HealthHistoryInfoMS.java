package com.apis.globedr.model.step.health.healthHistory;

import com.apis.globedr.enums.NotiGroup;
import com.apis.globedr.enums.QuestionGroupType;
import com.apis.globedr.enums.QuestionItemType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HealthHistoryInfoMS {
    private String groupName;
    private String questionName;
    private Integer groupType;
    private String questionItemSig;
    private String userSig;
    private String itemName;
    private Boolean isYes; // choose true = Yes or false = No
    private Boolean test; // choose true = Yes or false = No
    private String value;
    private Integer itemType;
    private Object answerData;

    public void setItemType(String value){
        this.itemType = QuestionItemType.value(value);
    }

    public void setValue(String value) {
        this.value = value;
        this.answerData = AnswerDataFactory.init(this);
    }

    public void setIsYes(Boolean isYes) {
        this.isYes = isYes;
        this.answerData = AnswerDataFactory.init(this);
    }

    public void setGroupType(Object info){
        groupType = QuestionGroupType.value(info);
    }

}
