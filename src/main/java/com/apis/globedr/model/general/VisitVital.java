package com.apis.globedr.model.general;

import com.apis.globedr.model.response.appointment.Doctor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class VisitVital {
    private List<Provider> providers = new ArrayList<>();
    private ChiefComplaint chiefComplaint = new ChiefComplaint();
    private Vital vital = new Vital();

    public void addProvider(Doctor doctor){
        Provider provider = new Provider();
        provider.setId(doctor.getUserId());
        provider.setSig(doctor.getSignature());
        provider.setName(doctor.getName());
        provider.setVisitAccessType(2);
        provider.setAvatar(doctor.getAvatar());
        provider.setTitle(doctor.getTitle());
        providers.add(provider);
    }
}

