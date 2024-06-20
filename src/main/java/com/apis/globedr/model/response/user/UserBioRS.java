package com.apis.globedr.model.response.user;

import com.apis.globedr.model.request.user.AddUserBioRQ;
import com.apis.globedr.model.general.Doc;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserBioRS {
    private List<AddUserBioRQ> bioList;
    private List<AddUserBioRQ> educationList;
    private List<AddUserBioRQ> experienceList;
    private List<AddUserBioRQ> awardList;
    private List<AddUserBioRQ> associationList;
    private List<AddUserBioRQ> affiliationList;
    private List<AddUserBioRQ> publicationList;
    private List<Doc> certificateList;


}
