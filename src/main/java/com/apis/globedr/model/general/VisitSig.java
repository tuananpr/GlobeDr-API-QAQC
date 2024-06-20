package com.apis.globedr.model.general;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VisitSig {
    private String visitSig;

    public VisitSig(String visitSig){
        this.visitSig = visitSig;
    }
}
