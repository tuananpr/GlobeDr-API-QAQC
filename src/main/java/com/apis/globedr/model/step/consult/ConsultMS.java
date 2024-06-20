package com.apis.globedr.model.step.consult;

import com.apis.globedr.enums.PostStatus;
import com.apis.globedr.enums.PostType;
import com.apis.globedr.enums.UserType;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ConsultMS {
    private Boolean isPatient;
    private String temperature;
    private String userSig;
    private String msgSig;
    private String postSig;
    private String msg;

    private Double height;
    private Double weight;

    @JsonUnwrapped
    FilterDate filterDate;
    private String emailOrphone;
    private String textSearch;
    private String createdByName;
    private Integer providerId;
    private Integer postId;
    private Integer postStatus;
    private List<Integer> postTypes;
    private Integer userMode;
    private Integer auditorId;
    private String doctorName;
    private String userName;
    private String auditorSig;
    private String auditorName;
    private String providerSig;
    private String reason;
    private Integer language;
    private boolean webPlatform;
    private boolean isPayment4Doctor;



    @JsonUnwrapped
    ImageFile file;
    @JsonUnwrapped
    Page page;


    public boolean isPayment4Doctor() {
        return isPayment4Doctor;
    }

    public void setIsPayment4Doctor(boolean payment4Doctor) {
        isPayment4Doctor = payment4Doctor;
    }

    public void setUserMode(Object userMode) {
        this.userMode = UserType.value(userMode);
    }

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



    public ConsultMS setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
        return this;
    }
}
