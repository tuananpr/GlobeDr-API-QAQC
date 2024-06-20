package com.apis.globedr.apis;

import com.apis.globedr.helper.*;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.request.orgMember.*;
import com.apis.globedr.model.response.orgMember.LoadGroupsRS;
import com.apis.globedr.model.response.orgMember.MemberRS;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrgMemberApi extends BaseApi {

    private final int page = 1;
    private final int pageSize = 50;
    private OrgMemberApi(){}
    private static OrgMemberApi instant;
    public static OrgMemberApi getInstant(){
        if(instant == null) instant = new OrgMemberApi();
        return instant;
    }

    public List<LoadGroupsRS> loadGroups(Object body) {
        return RestCore.given().url(API.OrgMember.LOAD_GROUPS()).auth(token).body(body, LoadGroupsRQ.class)
                .post().send()
                .extractAsModels("data.list", LoadGroupsRS.class );
    }


    public Response loadOrgGroup(String orgSig) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ORG_SIG, orgSig);
        body.put(Text.PAGE, page);
        body.put(Text.PAGE_SIZE, pageSize);
        return RestCore.given().url(API.OrgMember.LOAD_GROUPS()).auth(token).body(body).post().send();
    }

    public String loadOrgGroup(String orgSig, String groupName) {
        loadOrgGroup(orgSig);

        List<String> groupSigList = Data.response.extract(String.format("data.list[?(@.name=='%s')].groupSig", groupName));
        return groupSigList.get(0);
    }

    public Response orgAddHealthDoc(Object body) {
        return RestCore.given().url(API.OrgMember.ORG_ADD_HEALTH_DOC()).auth(token)
                .multipart(body, OrgAddHealthDocRQ.class).post().send();
    }


    public Response sendSMS(String orgSig, Map<String, Object> dataTable) {
        Map<String, Object> body = new HashMap<>();
        body.putAll(dataTable);

        body.put(Text.ORG_SIG, orgSig);
//        body.put(Text.FILE, new File(dataTable.get()));
        return RestCore.given().url(API.OrgMember.SEND_SMS()).auth(token).multipart(body).post().send();

    }



    public List<MemberRS> loadMember(Object body) {
        return RestCore.given().url(API.OrgMember.LOAD_MEMBERS()).auth(token)
                .body(body, LoadMembersRQ.class).post().send()
                .extractAsModels("data.list", MemberRS.class );
    }

    public Response updatePatientId(Object body) {
        return RestCore.given().url(API.OrgMember.PATIENT_ID_MEMBER()).auth(token).body(body, PatientIdMemberRQ.class).put().send();

    }

    public Response statisticByGender(Object body) {
        return RestCore.given().url(API.OrgMember.STATISTIC_BY_GENDER()).auth(token).body(body, OrgSig.class).post().send();
    }

    public Response statisticByCountry(Object body) {
        return RestCore.given().url(API.OrgMember.STATISTIC_BY_COUNTRY()).auth(token).body(body, OrgSig.class).post().send();
    }

    public Response statisticByCity(Object body) {
        return RestCore.given().url(API.OrgMember.STATISTIC_BY_CITY()).auth(token).body(body, StatisticByCityRQ.class).post().send();
    }

    public Response statisticGrowthChart(Object body) {
        return RestCore.given().url(API.OrgMember.STATISTIC_GROWTH_CHART()).auth(token).body(body, StatisticGrowthChartRQ.class).post().send();
    }

    public Response addGroup(Object body) {
        return RestCore.given().url(API.OrgMember.ADD_GROUP()).auth(token).body(body, AddGroupRQ.class).post().send();
    }


    public Response deleteGroup(Object body) {
        return RestCore.given().url(API.OrgMember.DELETE_GROUP()).auth(token).body(body, AddGroupRQ.class).delete().send();
    }

    public Response addMember(Object body) {
        return RestCore.given().url(API.OrgMember.ADD_MEMBER()).auth(token).body(body, AddMemberRQ.class).post().send();
    }


    public Response removeMemberOutGroup(Object body) {
        return RestCore.given().url(API.OrgMember.REMOVE_MEMBER_OUT_GROUP()).auth(token).body(body, AddMemberToGroupRQ.class).delete().send();
    }


    public Response addMemberToGroup(Object body) {
        return RestCore.given().url(API.OrgMember.ADD_MEMBER_TO_GROUP()).auth(token).body(body, AddMemberToGroupRQ.class).post().send();
    }

    public Response loadMembersByGroup(Object body) {
        return RestCore.given().url(API.OrgMember.LOAD_MEMBERS_BY_GROUP()).auth(token).body(body, LoadMembersByGroupRQ.class).post().send();
    }

    public Response updateGroup(Object body) {
        return  RestCore.given().url(API.OrgMember.UPDATE_GROUP()).auth(token).body(body, AddGroupRQ.class).post().send();
    }






}
