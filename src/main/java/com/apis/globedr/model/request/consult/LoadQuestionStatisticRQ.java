package com.apis.globedr.model.request.consult;

import com.apis.globedr.enums.PostStatus;
import com.apis.globedr.enums.PostType;
import com.apis.globedr.enums.UserType;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadQuestionStatisticRQ {

    @JsonUnwrapped
    FilterDate filterDate;
    private String auditorSig;
    private String providerSig;
    private String coordinatorSig;
    private Integer postStatus;
    private Integer PostId;
    private Integer userMode;
    private boolean isPayment4Doctor;
    private List<Integer> postTypes;

    public void setPostTypes(Object postTypes) {
        if(postTypes instanceof String){
            List<Integer> result = new ArrayList<>();
            for (String type : ((String) postTypes).split(",")) {
                result.add(PostType.value(type));
            }
            this.postTypes = result;
        }else{
            this.postTypes = (List<Integer>) postTypes;
        }
    }
    public List<Integer> getPostTypes() {
        return postTypes == null ? new ArrayList<>() : this.postTypes;
    }

    public void setPostStatus(Object info){
        this.postStatus = PostStatus.value(info);
    }

    public void setUserMode(Object userMode) {
        this.userMode = UserType.value(userMode);
    }

    public boolean getIsPayment4Doctor() {
        return this.isPayment4Doctor;
    }

    public void setIsPayment4Doctor(boolean isPayment4Doctor) {
        this.isPayment4Doctor = isPayment4Doctor;
    }
    @JsonUnwrapped
    Page page;
}

