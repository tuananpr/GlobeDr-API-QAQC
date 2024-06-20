package com.apis.globedr.model.request.appointment;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRQ {
    private Integer idCardDocIdBack;
    private Integer idCardDocIdFront;
    private String idCardDocSigBack;
    private String idCardDocSigFront;
    private String insuDocSigFront;
    private String insuDocSigBack;
    private Integer insuDocIdFront;
    private Integer insuDocIdBack;
    private String idInsurance;
    private String idCardNumber;
    private String apptSig;

}
