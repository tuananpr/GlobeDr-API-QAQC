package com.apis.globedr.model.response.health;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class AddHealthDocRS {
    private HealthDoc healthDoc;
    private HealthDoc apptDoc;



}
