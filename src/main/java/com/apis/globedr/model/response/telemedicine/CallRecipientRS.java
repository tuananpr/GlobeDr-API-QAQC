package com.apis.globedr.model.response.telemedicine;

import com.apis.globedr.model.response.other.Specialty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CallRecipientRS {
    private List<Specialty> specialties;
    private Integer id;
    private String sig;
    private String name;
    private String title;
    private String avatar;
}
