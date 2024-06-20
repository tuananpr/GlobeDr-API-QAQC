package com.apis.globedr.model.step.health.healthHistory;

import com.google.gson.Gson;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionTextItem {
    private List<String> values;
    private Boolean isValue;

    public String toString(){
        return new Gson().toJson(this);
    }
}
