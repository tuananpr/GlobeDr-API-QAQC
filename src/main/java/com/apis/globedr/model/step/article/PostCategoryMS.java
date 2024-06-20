package com.apis.globedr.model.step.article;

import com.apis.globedr.enums.CategoryType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCategoryMS {
    private Integer categoryId;
    private String categorySignature;
    private String categoryName;
    private String description;
    private Integer weight;
    private Integer language;
    private Integer status;
    private Integer type;
    private Integer categoryStatus;
    private Integer categoryType;
    private String orgSignature;


    public void setType(String info){
        this.type = CategoryType.value(info);
    }
}
