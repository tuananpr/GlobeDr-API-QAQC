package com.apis.globedr.model.request.telemedicine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelemedicineMissRQ extends TeleMedicineInfo {
    private String userSigReceiver;
    private Integer senderType;
    private String channelName;
    private boolean anotherCall;
}
