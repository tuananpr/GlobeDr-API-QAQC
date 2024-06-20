package com.apis.globedr.model.response.health.healthHistory;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthHistoryRS {
    private List<Questions> questions;
    private String groupName;
}
