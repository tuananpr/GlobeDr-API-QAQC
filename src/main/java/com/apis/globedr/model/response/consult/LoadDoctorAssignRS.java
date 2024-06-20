package com.apis.globedr.model.response.consult;

import com.apis.globedr.model.response.other.Specialty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoadDoctorAssignRS {
    private String avatar;
    private String name;
    private String signature;
    private List<Specialty> specialties;
    private List<Integer> specialtyCodes;
    private String title;
}
