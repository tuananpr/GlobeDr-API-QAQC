package com.apis.globedr.services.database.entities;

import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
public class UserVerification {
    private Integer userId;
    private Integer type;
    private String verifyCode;
    private Integer verified;
    private Date onDate;
    private Integer id;
    public UserVerification(){}

    public UserVerification(Integer userId, Integer type, String verifyCode, Integer verified, Date onDate, Integer id) {
        this.userId = userId;
        this.type = type;
        this.verifyCode = verifyCode;
        this.verified = verified;
        this.onDate = onDate;
        this.id = id;
    }





}
