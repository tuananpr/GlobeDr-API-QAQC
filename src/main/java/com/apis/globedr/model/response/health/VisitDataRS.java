package com.apis.globedr.model.response.health;

import com.apis.globedr.model.general.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitDataRS {
    private String visitSig;
    private String userSig;
    private String orgSig;
    private String visitDate;
    private String linkSig;
    private int linkId;
    private int createdByType;
    private int createdById;
    private VisitData visitData;
    private IssuesObservation issuesObservation;
    private VisitSubclinical visitSubclinical;
    private Medication medication;
    private Instruction instruction;
    private FollowUp followUp;
    private Link link;


}
