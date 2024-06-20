package com.apis.globedr.model.step.org;

import com.apis.globedr.model.general.VisitData;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AfterVisitMS {
    private String visitSig;
    private String userSig;
    private String orgSig;
    private String visitDate;
    private String linkSig;
    private String doctorName;
    private int linkId;
    private int createdByType;
    private int createdById;

    @Builder.Default
    private VisitData visitData = new VisitData();

    public void setChiefComplaintVisit(String value){
        getVisitData().getVisitVital().getChiefComplaint().setChiefComplaintVisit(value);
    }

    public void setHeight(Integer value){
        getVisitData().getVisitVital().getVital().setHeight(value);
    }

    public void setWeight(Integer value){
        getVisitData().getVisitVital().getVital().setWeight(value);
    }

    public void setBloodPressure(Integer value){
        getVisitData().getVisitVital().getVital().setBloodPressure(value);
    }

    public void setRespiration(Integer value){
        getVisitData().getVisitVital().getVital().setRespiration(value);
    }

    public void setPhysicalType(Integer value){
        getVisitData().getVisitVital().getVital().setPhysicalType(value);
    }

    public void setPulse(Integer value){
        getVisitData().getVisitVital().getVital().setPulse(value);
    }

    public void setBmi(Double value){
        getVisitData().getVisitVital().getVital().setBmi(value);
    }

    public void setHead(Double value){
        getVisitData().getVisitVital().getVital().setHead(value);
    }

    public void setIssuesObservationDescription(String value){
        getVisitData().setIssuesObservationDescription(value);
    }

    public void setVisitSubclinicalDescription(String value){
        getVisitData().setVisitSubclinicalDescription(value);
    }

    public void setMedicationDescription(String value){
        getVisitData().setMedicationDescription(value);
    }

    public void setInstructionDescription(String value){
        getVisitData().setInstructionDescription(value);
    }

    public void setFollowUpDescription(String value){
        getVisitData().setFollowUpDescription(value);
    }


}

