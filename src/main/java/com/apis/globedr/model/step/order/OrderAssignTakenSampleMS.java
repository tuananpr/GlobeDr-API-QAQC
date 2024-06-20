package com.apis.globedr.model.step.order;

import com.apis.globedr.enums.OrgFeatureAttributes;
import com.apis.globedr.model.step.org.StaffMS;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;

@Getter
@Setter
public class OrderAssignTakenSampleMS {
    private String patientName;
    private String orgSig;
}