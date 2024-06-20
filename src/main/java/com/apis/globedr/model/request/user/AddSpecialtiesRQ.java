package com.apis.globedr.model.request.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddSpecialtiesRQ {
    private List<String> specialtyCodes;
}
