package com.apis.globedr.model.request.chat;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCaresRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String deviceId;
    @JsonAlias({"name", "customerCareName"})
    private String name;
}
