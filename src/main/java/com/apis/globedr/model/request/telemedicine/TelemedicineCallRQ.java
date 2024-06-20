package com.apis.globedr.model.request.telemedicine;

import com.apis.globedr.enums.VideoCallType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelemedicineCallRQ extends TeleMedicineInfo {
    private String userSigReceiver;
    private Integer senderType;
}