package com.apis.globedr.model.request.appointment;

import com.apis.globedr.model.response.appointment.Doctor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListDoctor {
    private List<Doctor> list;

    public Doctor getDoctorForSchedule(Object name){
        if(name != null){
            for (Doctor doctor: list) {
                if(doctor.getName().equalsIgnoreCase(name.toString())){
                    return new Doctor(null, doctor.getName(), doctor.getSignature(), null, doctor.getTitle(), doctor.getUserId());
                }
            }
        }

        return null;
    }


}
