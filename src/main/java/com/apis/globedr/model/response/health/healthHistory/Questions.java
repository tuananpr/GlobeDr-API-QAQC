package com.apis.globedr.model.response.health.healthHistory;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Questions {
    private List<QuestionItemsRS> questionItems;
    private String name;
    private String groupName;
    private Integer type;
}
