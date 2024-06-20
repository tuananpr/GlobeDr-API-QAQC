package com.apis.globedr.model.step.userManager;

import com.apis.globedr.enums.PointActivity;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Name;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointRuleMS {
    private String descriptionHtml;
    private Integer amount;
    private Integer id;
    private Integer pointActivity;
    private Name ruleName;
    private String nameVi;
    private String nameEn;

    @JsonUnwrapped
    private FilterDate filterDate;

    @JsonUnwrapped
    private Page page;

    public void setPointActivity(Object pointActivity) {
        this.pointActivity = PointActivity.value(pointActivity);
    }



    public void setNameVi(String value){
        this.nameVi = value;
        if(ruleName == null ) ruleName = new Name();

        ruleName.setNameVi(value);
    }

    public void setNameEn(String value){
        this.nameEn = value;
        if(ruleName == null ) ruleName = new Name();

        ruleName.setNameEn(value);
    }
}
