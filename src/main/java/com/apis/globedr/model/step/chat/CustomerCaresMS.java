package com.apis.globedr.model.step.chat;

import com.apis.globedr.model.request.chat.CustomerCaresRQ;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCaresMS extends CustomerCaresRQ {
    private String orgName;
}
