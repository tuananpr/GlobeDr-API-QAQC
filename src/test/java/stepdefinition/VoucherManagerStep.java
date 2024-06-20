package stepdefinition;

import com.apis.globedr.business.voucher.VoucherApprover;
import com.apis.globedr.business.voucher.VoucherManagerBus;
import com.apis.globedr.business.voucher.VoucherOrg;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.general.VoucherSig;
import com.apis.globedr.model.step.voucher.CardMS;
import com.apis.globedr.model.step.voucher.VoucherCategoryMS;
import com.apis.globedr.model.step.voucher.VoucherMS;
import com.apis.globedr.constant.Text;
import com.apis.globedr.stepdefinition.SqlDatabaseStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class VoucherManagerStep extends Data {


    VoucherManagerBus voucherOrg = new VoucherOrg();
    VoucherManagerBus voucherApprover = new VoucherApprover();
    SqlDatabaseStep sqlStep = new SqlDatabaseStep();

    @And("^As approver, I re-create voucher category$")
    public void recreateVoucherCategory(List<VoucherCategoryMS> list) {
        list.forEach(info -> {
            sqlStep.deleteVoucherCategory(Text.NAME_EN, info.getNameEN());
            sqlStep.deleteVoucherCategory(Text.NAME_VN, info.getNameVN());
            voucherApprover.createCategory(info);
        });
    }


    @And("^As manager, I create voucher info category name \"([^\"]*)\"$")
    public void createVoucher(String categoryName, List<VoucherMS> list) {
        list.forEach(info -> {
            sqlStep.onSqlServerIDeleteVoucherByName(info.getName());
            info.setOrgSig(orgSig);
            info.setCategorySig(voucherOrg.loadCategories(categoryName).getCategorySig());
            VoucherSig rs = voucherOrg.createVoucher(info);
            if (response.isSuccess()) voucherSig = rs.getVoucherSig();
        });
    }

    @And("As approver, I load cards into voucher")
    public void asApproverILoadCardIntoVoucher(List<CardMS> list) {
        list.forEach(info -> {
            voucherOrg.loadCards(info);
        });
    }

    @And("As manager, I load vouchers")
    public void asManagerILoadVouchers(List<VoucherMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            voucherOrg.loadVouchersByManage(info);
        });
    }


    @When("^As manager, I load voucher categories$")
    public void loadVoucherCategory(List<VoucherCategoryMS> list) {
        list.forEach(voucherOrg::loadCategories);
    }


    @Then("^The voucher category should( not)? contain category that have \"([^\"]*)\" is \"([^\"]*)\"$")
    public void verifyVoucherCategory(String isContaint, String key, String value) {
        String path = String.format("$..list[*].%s", key);
        List<String> resultList = response.extract(path);
        if (isContaint == null) {
            Assert.assertTrue(resultList.contains(value));
        } else {
            Assert.assertFalse(resultList.contains(value));
        }
    }

    @When("^I select voucher category that have \"([^\"]*)\" is \"([^\"]*)\"$")
    public void selectCategory(String key, String value) {
//        voucherManagerBus.loadCategories();
//        String path = String.format("$..list[?(@.%s == '%s')].categorySig", key, value);
//        categorySig = response.extractAsList(path).get(0);
    }

    @And("^As manager, I update voucher category name \"([^\"]*)\" with below info$")
    public void editCategory(String categoryName, List<VoucherCategoryMS> list) {
        VoucherCategoryMS newData = list.get(0);
        newData.setOrgSig(orgSig);
        voucherOrg.editCategory(categoryName, newData);
    }


    @And("^As approver, I delete voucher categories$")
    public void iDeleteCategory(List<VoucherCategoryMS> list) {
        list.forEach(voucherApprover::deActiveCategory);
    }

    @And("^As manager, I add cards for voucher name \"([^\"]*)\"$")
    public void addCard(String voucherName, List<CardMS> list) {
        list.forEach(info->{
            info.setOrgSig(orgSig);
            voucherOrg.addCard(voucherName, info);
        });
    }

    @And("^As manager, I add auto cards for voucher name \"([^\"]*)\"$")
    public void addAutoCard(String voucherName, List<CardMS> list) {
        list.forEach(info->{
            info.setGlobedr(true);
            info.setOrgSig(orgSig);
            voucherOrg.addAutoCard(voucherName, info);
        });
    }

    @And("As approver, I approve vouchers")
    public void approveVoucher(List<VoucherMS> list) {
        list.forEach(info -> {
            voucherApprover.approveVoucher(info);
        });
    }

    @And("As approver, I deActive vouchers")
    public void deActiveVoucher(List<VoucherMS> list) {
        list.forEach(info -> {
            voucherApprover.deActiveVoucher(info);
        });
    }

    @And("I update avatar for voucher")
    public void iUpdateAvatarForVoucher(List<VoucherMS> list) {
        list.forEach(info->{
            info.setOrgSig(orgSig);
            voucherOrg.updateVoucherIcon(info);
        });
    }

    @And("I update hot avatar for voucher")
    public void iUpdateHotAvatarForVoucher(List<VoucherMS> list) {
        list.forEach(info->{
            info.setOrgSig(orgSig);
            voucherOrg.updateVoucherHotIcon(info);
        });
    }

    @When("^I update below info for voucher name \"([^\"]*)\" on category \"([^\"]*)\"$")
    public void updateVoucher(String voucherName, String categoryName, List<VoucherMS> list)  {
        list.forEach(info ->{
            info.setOrgSig(orgSig);
            VoucherMS old = VoucherMS.builder().orgSig(orgSig).name(voucherName).categoryName(categoryName).build();
            voucherOrg.updateVoucher(old,info);
        });

    }


    @And("As manager, I set voucher is hot")
    public void asManagerISetVoucherIsHot(List<VoucherMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            voucherOrg.setHotVoucher(info);
        });
    }

    @And("As manager, I set voucher is popular")
    public void asManagerISetVoucherIsPopular(List<VoucherMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            voucherOrg.setPopularVoucher(info);
        });
    }
}
