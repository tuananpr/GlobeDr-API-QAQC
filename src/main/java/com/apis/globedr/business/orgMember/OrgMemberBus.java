package com.apis.globedr.business.orgMember;

import com.apis.globedr.apis.OrgMemberApi;
import com.apis.globedr.business.AbsBus;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.request.orgMember.StatisticGrowthChartRQ;
import com.apis.globedr.model.response.orgMember.LoadGroupsRS;
import com.apis.globedr.model.response.orgMember.MemberRS;
import com.apis.globedr.model.step.orgMember.GroupMemberMS;
import com.apis.globedr.model.step.orgMember.HealthDocMemberMS;
import com.apis.globedr.model.step.orgMember.MemberMS;
import com.rest.core.RestCore;
import com.rest.core.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrgMemberBus extends AbsBus {

    OrgMemberApi memberApi =  OrgMemberApi.getInstant();

    public Response addGroup(GroupMemberMS body){
        return memberApi.addGroup(body);
    }

    public Response updateGroup(GroupMemberMS oldGroup, GroupMemberMS newInfo){
        String groupSig = loadGroups(oldGroup).get(0).getGroupSig();
        newInfo.setGroupSig(groupSig);
        return memberApi.updateGroup(newInfo);
    }


    public Response deleteGroup(GroupMemberMS body){
        String groupSig = loadGroups(body).get(0).getGroupSig();
        body.setGroupSig(groupSig);
        return memberApi.deleteGroup(body);
    }

    public List<LoadGroupsRS> loadGroups(GroupMemberMS body){
        return memberApi.loadGroups(body);
    }

    public List<LoadGroupsRS> loadGroups(MemberMS body){
        return memberApi.loadGroups(body);
    }

    public Response loadMemberInGroups(MemberMS body){
        String groupSig = loadGroups(body).get(0).getGroupSig();
        body.setGroupSig(groupSig);
        return memberApi.loadMembersByGroup(body);
    }


    public List<MemberRS> loadMember(String displayName, String orgSig) {
        return memberApi.loadMember(MemberMS.builder().displayName(displayName).orgSig(orgSig).build());
    }

    public List<MemberRS> loadMember(Object body) {
        return memberApi.loadMember(body);
    }

    public Response addMemberToGroup(MemberMS body) {
        return memberApi.addMemberToGroup(body);
    }

    public Response updatePatientId(MemberMS body) {
        return memberApi.updatePatientId(body);
    }


    public Response removeMemberOutGroup(MemberMS body) {
        return memberApi.removeMemberOutGroup(body);
    }
    public Response addMember(MemberMS body) {
        return memberApi.addMember(body);
    }

    public Response statisticByGender(OrgSig body) {
        return memberApi.statisticByGender(body);
    }

    public Response statisticByCountry(OrgSig body) {
        return memberApi.statisticByCountry(body);
    }

    public Response statisticByCity(MemberMS body) {
        return memberApi.statisticByCity(body);
    }

    public Response statisticGrowthChart(StatisticGrowthChartRQ body) {
        return memberApi.statisticGrowthChart(body);
    }

    public void orgAddHealthDoc(HealthDocMemberMS info) {
        loadMember(info).forEach(member -> {
            info.setUserSig(member.getUserSig());
            memberApi.orgAddHealthDoc(info);
        });

    }




}
