package com.apis.globedr.model.request.sponsor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DurationRQ {
    private String startDate;
    private String endDate;
    private String orgSig;
    private Integer id;

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
