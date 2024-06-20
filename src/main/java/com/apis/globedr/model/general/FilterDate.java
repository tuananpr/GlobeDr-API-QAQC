package com.apis.globedr.model.general;

import com.apis.globedr.helper.Common;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterDate {
    private String toDate;
    private String fromDate;


    public void setToDate(String toDate){
        this.toDate = toDate;
    }

    public void setFromDate(String fromDate){
        this.fromDate = fromDate;
    }
}
