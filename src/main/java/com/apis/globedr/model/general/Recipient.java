package com.apis.globedr.model.general;

import com.apis.globedr.enums.RecipientType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipient {
    private Integer type; // RecipientType
    private String recipSig;

    public void setType(Object value) {
        this.type = RecipientType.value(value);
    }
}
