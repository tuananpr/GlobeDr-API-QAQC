package com.apis.globedr.model.response.medicalTest;

import com.apis.globedr.model.general.Status;
import com.apis.globedr.model.general.Type;
import com.apis.globedr.model.response.org.OrgRS;
import com.apis.globedr.model.response.product.ProductItemRS;
import com.apis.globedr.model.response.user.UserInfoRS;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetailRS {
    private Status status;
    private Type type;
    private String receiverName;
    private String productServiceName;
    private UserInfoRS userInfo;
    private UserInfoRS patientInfo;
    private PersonOrgInfo personOrgInfo;
}
