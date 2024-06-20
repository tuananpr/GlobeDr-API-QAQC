package com.apis.globedr.model.response.sponsor;

import com.apis.globedr.model.response.voucher.Org;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class SponsorRS {
    private Integer id;
    private Integer weight;
    private Integer status;
    private String startDate;
    private String endDate;
    private Org org;

    public Date getStartDateAsDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return format.parse(getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getEndDateAsDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return format.parse(getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
