package com.apis.globedr.model.request.userManager;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PwdResetRQ {
    private List<String> userSigList;
}
