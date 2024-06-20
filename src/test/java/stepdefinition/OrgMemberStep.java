package stepdefinition;

import com.apis.globedr.business.orgMember.OrgMemberBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.request.orgMember.StatisticGrowthChartRQ;
import com.apis.globedr.model.step.orgMember.GroupMemberMS;
import com.apis.globedr.model.step.orgMember.MemberMS;
import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.Common;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrgMemberStep extends Data {
    OrgMemberBus memberBus = new OrgMemberBus();

    @And("^I add member by scan QRcode$")
    public void iAddMemberByQCCode() {
        MemberMS body = MemberMS.builder().orgSig(orgSig).qrCode(Data.get(Text.QR_CODE)).build();
        memberBus.addMember(body);
    }


    @And("^I load org members$")
    public void iLoadMembersOfOrgHasBeenShared(List<MemberMS> list) {
        list.forEach(member ->{
            member.setOrgSig(orgSig);
            memberBus.loadMember(member);
        });
    }


    @And("^I load user dashboard by gender$")
    public void iLoadDashboardWithGender() {
        memberBus.statisticByGender(new OrgSig(orgSig));
    }

    @And("^I load user dashboard by country$")
    public void iLoadDashboardWithCountry() {
        memberBus.statisticByCountry(new OrgSig(orgSig));
    }

    @And("^I load user dashboard by growth chart$")
    public void inLastDaysFromTodayWithCountryEqual(StatisticGrowthChartRQ body) {
        body.setOrgSig(orgSig);
        memberBus.statisticGrowthChart(body);
    }

    @And("^I load user dashboard by city$")
    public void iLoadDashboardCity(MemberMS body) {
        body.setOrgSig(orgSig);
        memberBus.statisticByCity(body);
    }


    @Then("^The result return should be \"([^\"]*)\" and count '(\\d+)' user$")
    public void theResultReturnShouldBeAndCountUser(String today, int expect) {
        SimpleDateFormat formatter = new SimpleDateFormat(Text.FTIME_FULL);
        if (today.equalsIgnoreCase("to day")) {
            Date day = Common.today();
            String toDate = formatter.format(day);
            List<String> listTime = response.extract("data.list[*].time");
            String time = listTime.get(0);
            List<Integer> listCount = response.extract("data.list[*].count");
            int count = listCount.get(0);

            Assert.assertEquals(toDate, time, "Time should be :" + today);
            Assert.assertEquals(count, expect);
        }
    }


    @And("^I create group member$")
    public void createGroup(List<GroupMemberMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            memberBus.addGroup(info);
        });
    }

    @And("^I update below info for group member name \"([^\"]*)\"$")
    public void updateGroup(String name, GroupMemberMS newInfo) {
        newInfo.setOrgSig(orgSig);
        GroupMemberMS old = GroupMemberMS.builder().name(name).orgSig(orgSig).build();
        memberBus.updateGroup(old, newInfo);
    }

    @And("^I delete group member$")
    public void deleteGroup(List<GroupMemberMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            memberBus.deleteGroup(info);
        });
    }

    @And("^I load group member$")
    public void loadGroup(GroupMemberMS info) {
        info.setOrgSig(orgSig);
        memberBus.loadGroups(info);
    }

    @And("^I load members into group member$")
    public void loadMemberIntoGroup(MemberMS info) {
        info.setOrgSig(orgSig);
        memberBus.loadMemberInGroups(info);
    }

    @And("I add below members to group member name {string}")
    public void iAddBelowMembersToGroupMemberName(String newGroupName, List<MemberMS> list) {
        String groupSig = memberBus.loadGroups(GroupMemberMS.builder().name(newGroupName).orgSig(orgSig).build()).get(0).getGroupSig();
        list.forEach(info ->{
            info.setOrgSig(orgSig);
            info.setGroupSig(groupSig);
            info.setUserSig(memberBus.loadMember(info).get(0).getUserSig());
            memberBus.addMemberToGroup(info);
        });
    }

    @And("I delete below members from group member name {string}")
    public void iDeleteBelowMembersFromGroupMemberName(String newGroupName, List<MemberMS> list) {
        String groupSig = memberBus.loadGroups(GroupMemberMS.builder().name(newGroupName).orgSig(orgSig).build()).get(0).getGroupSig();
        list.forEach(info ->{
            info.setOrgSig(orgSig);
            info.setGroupSig(groupSig);
            info.setUserSig(memberBus.loadMember(info).get(0).getUserSig());
            memberBus.removeMemberOutGroup(info);
        });
    }

    @And("As org, I update patient id for member name {string}")
    public void asOrgIUpdatePatientIdForMember(String name, MemberMS info) {
        String authSig = memberBus.loadMember(name, orgSig).get(0).getAuthSig();
        info.setOrgSig(orgSig);
        info.setAuthSig(authSig);
        memberBus.updatePatientId(info);
    }






}
