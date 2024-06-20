package stepdefinition;

import com.apis.globedr.business.orgManager.OrgManagerBus;
import com.apis.globedr.business.payment.PaymentBus;
import com.apis.globedr.business.search.SysAdminSearchBus;
import com.apis.globedr.business.system.SystemBus;
import com.apis.globedr.business.userManager.UserManagerBus;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.apis.globedr.enums.*;
import com.apis.globedr.helper.Common;
import com.apis.globedr.helper.Wait;
import com.apis.globedr.model.request.payment.PayooOrderRQ;
import com.apis.globedr.model.request.system.*;
import com.apis.globedr.model.response.org.LoadOrgsRS;
import com.apis.globedr.model.step.org.OrgMS;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.step.sysAdmin.AppUsageTimeMS;
import com.apis.globedr.services.es.ElasticSearchApi;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import java.util.*;

public class SystemAdminStep extends Data {

    OrgManagerBus orgManagerBus = new OrgManagerBus();
    SystemBus systemBus = new SystemBus();
    SysAdminSearchBus sysAdminSearchBus = new SysAdminSearchBus();
    PaymentBus paymentBus = new PaymentBus();
    UserManagerBus userManagerBus = new UserManagerBus();

    @When("^As sysAdmin, I set attributes for org name \"(.*?)\"$")
    public void setOrgAttribute(String orgName, List<String> attributeList) {
        Wait.seconds(1);
        String orgSig = sysAdminSearchBus.loadOrgsByName(orgName).get(0).getOrgSig();
        OrgMS orgMS = OrgMS.builder().orgSig(orgSig).orgAttribute(OrgAttributes.convert(attributeList)).build();
        orgManagerBus.changeOrgAttribute(orgMS);
    }


    @When("^As sysAdmin, I view users growth chart$")
    public void iViewUsersGrowthChart(UserGrowthChartRQ body) {
        systemBus.viewUsersGrowthChart(body);
    }

    @When("^As sysAdmin, I view users by country$")
    public void iViewUsersByCountry(UsersByCountryRQ body) {
        systemBus.getUsersByCountry(body);
    }

    @When("^As sysAdmin, I view users by gender$")
    public void iViewUsersByGender(UsersByGenderRQ body) {
        systemBus.getUsersByGender(body);
    }

    @When("^As sysAdmin, I view orgs growth chart$")
    public void iViewOrgsGrowthChart(OrgsGrowthChartRQ body) {
        systemBus.viewOrgsGrowthChart(body);
    }

    @When("^As sysAdmin, I view orgs by country$")
    public void iViewOrgsByCountry(OrgsByCountryRQ body) {
        systemBus.getOrgsByCountry(body);
    }

    @When("^As sysAdmin, I view orgs by type$")
    public void iViewOrgsByType(OrgsByTypeRQ body) {
        systemBus.getOrgsByType(body);
    }


    @And("^As sysAdmin, I load feedbacks$")
    public void sysAdminLoadFeedbacks(FeedbacksRQ body) {
        systemBus.loadFeedbacks(body);
    }

    @And("^I want to export data$")
    public void iWantToExportData(FeedbacksExcelRQ body) {
        systemBus.exportFeedbacks(body);
    }


    @And("As sysAdmin, I setting customer care")
    public void asSysAdminISettingCustomerCare(List<OrgMS> list) {
        list.forEach(info->{
            String orgSig = orgManagerBus.loadOrgs(info).get(0).getOrgSig();
            info.setOrgSig(orgSig);
            orgManagerBus.customerCareSetting(info);
        });
    }

    @And("As sysAdmin, I get total customer care into org")
    public void onOrgIGetTotalCustomerCare(OrgMS info) {
        LoadOrgsRS rs = sysAdminSearchBus.loadOrgs(info).get(0);
        orgManagerBus.getTotalCustomerCare(rs);
    }

    @And("As sysAdmin, I load above payoo order")
    public void asSysAdminILoadAbovePayooOrder() {
        PayooOrderRQ info = new PayooOrderRQ();
        //info.setOrderId(Data.get(Text.ORDER_ID));
        info.setOrderId(Data.get(Text.ORDER_ID));
        paymentBus.payooOrder(info);
    }

    @And("As sysAdmin, I load payoo order that isn't existed")
    public void asSysAdminILoadPayooOrderNotFound() {
        PayooOrderRQ info = new PayooOrderRQ();
        //info.setOrderId(Data.get(Text.ORDER_ID));
        info.setOrderId("123");
        paymentBus.payooOrder(info);
    }

    @When("As sysAdmin, I set time use app for user")
    public void asSysAdminISetTimeUseAppForUser(List<AppUsageTimeMS> list) {
        list.forEach(info->{
            userManagerBus.loadUsersAndGetSigs(info.getName()).forEach(
                    u-> {
                        info.setUserSig(u);
                        userManagerBus.appUsageTime(info);
                    }
            );
        });
    }


}
