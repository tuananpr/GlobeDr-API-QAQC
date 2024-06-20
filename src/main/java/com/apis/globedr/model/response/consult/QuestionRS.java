package com.apis.globedr.model.response.consult;

import com.apis.globedr.enums.UserType;
import com.apis.globedr.model.general.Actions;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionRS {
    private List<Provider> providers;
    private Status status;
    private PostType postType;
    private Comment rootMsg;
    private String actions;
    private boolean unread;
    private String patientSig;
    private Integer postId;
    private String postSig;
    private String createdDate;


    public boolean hasUserName(String userName) {
        return getProviders().stream().anyMatch(p -> p.getUserName().equalsIgnoreCase(userName));
    }
    public boolean hasMsg(String msg) {
        return getRootMsg().getMsg().equalsIgnoreCase(msg);
    }

    public String getPatientSig() {
        return getProviders().stream()
                .filter(p -> p.getUserModeInQuestion() == UserType.Patient.value())
                .map(p->p.getUserSig())
                .findFirst().orElse(null);
    }

    public String getSig(String name) {
        return getProviders().stream()
                .filter(p -> p.getUserName().equalsIgnoreCase(name))
                .map(p->p.getUserSig())
                .findFirst().orElse(null);
    }
}

