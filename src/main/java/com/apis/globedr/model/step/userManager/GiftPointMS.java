package com.apis.globedr.model.step.userManager;

import com.apis.globedr.enums.PointActivity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiftPointMS {
    private String tokenCaptcha;
    private List<String> userSigs;
    private String user;
    private String description;
    private Integer pointActivity;
    private Integer amount;

    public void setPointActivity(Object pointActivity) {
        this.pointActivity = PointActivity.value(pointActivity);
    }
}
