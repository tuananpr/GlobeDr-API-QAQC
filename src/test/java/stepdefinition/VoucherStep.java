package stepdefinition;


import com.apis.globedr.business.search.UserSearchBus;
import com.apis.globedr.business.voucher.*;
import com.apis.globedr.constant.Text;
import com.apis.globedr.model.request.voucher.ExportClickUsersRQ;
import com.apis.globedr.model.request.voucher.VoucherClickUsersRQ;
import com.apis.globedr.model.response.order.MedicalOrgRS;
import com.apis.globedr.model.step.review.ReviewMS;
import com.apis.globedr.model.step.voucher.UseVoucherMS;
import com.apis.globedr.model.step.voucher.VoucherMS;
import com.apis.globedr.helper.Data;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.*;


public class VoucherStep extends Data {


    UserSearchBus searchBus = new UserSearchBus();
    VoucherBus voucherUser = new VoucherUser();
    VoucherBus voucherGuest = new VoucherGuest();
    VoucherManagerBus voucherOrg = new VoucherOrg();

    private String base64Str;



    @And("^The list should( not)? contain voucher$")
    public void verifyVoucherList(String isContain, List<String> expectedList) {
        String path = "$..list[*].voucher.name";
        List<String> actualList = response.extract(path);
        Assert.assertEquals(actualList.containsAll(expectedList), isContain == null);
    }


    @And("^All vouchers should be (hot|popular) voucher$")
    public void verifyHotOrPopularVoucher(String voucherType) {
        List<Boolean> vouchers = null;
        switch (voucherType) {
            case "hot":
                vouchers = response.extract("$..voucher.isHot");
                break;
            case "popular":
                vouchers = response.extract("$..voucher.isPopular");
                break;
            default:
        }

        if (vouchers != null && vouchers.size() != 0) {
            for (Boolean voucher : vouchers) {
                Assert.assertTrue(voucher);
            }
        } else {
            Assert.fail(String.format("not found any %s voucher into list", voucherType));
        }


    }


    @And("^I save voucher qrcode$")
    public void iGetQRCodeVoucher() {
        Data.set(Text.QR_CODE, response.extract("data.qrCodeVoucher"));
    }


    @And("^I count my voucher$")
    public void iCountMyVoucher() {
        voucherUser.countMyVoucher();
    }

    @When("^I get review of org name \"([^\"]*)\"$")
    public void iGetReviewAboveOrg(String name) {
        String orgSig = searchBus.loadOrgsByName(name).stream().map(MedicalOrgRS::getSig).findFirst().orElse(null);
        voucherUser.getReview(VoucherMS.builder().orgSig(orgSig).build());
    }

    @When("^I review to org name \"([^\"]*)\"$")
    public void iReviewAboveOrg(String name, List<ReviewMS> list) {
        list.forEach(info -> {
            String orgSig = searchBus.loadOrgsByName(name).stream().map(MedicalOrgRS::getSig).findFirst().orElse(null);
            info.setOrgSig(orgSig);
            voucherUser.review(info);
        });
    }


    @And("^I view user that click on voucher$")
    public void iViewUserThatClickOnVoucher(VoucherClickUsersRQ body) {
        body.setOrgSig(orgSig);
        body.setVoucherSig(voucherSig);
        voucherOrg.voucherClickUsers(body);
    }

    @And("^I export user that click on voucher to file$")
    public void iExportUserThatClickOnVoucher(ExportClickUsersRQ body) {
        body.setOrgSig(orgSig);
        body.setVoucherSig(voucherSig);
        voucherOrg.exportClickUsers(body);
    }


    private String getBase64SToCsv(){
        base64Str = response.extract("data.base64Str");
        return new String(Base64.getDecoder().decode(base64Str.getBytes()));
    }
    @And("^CSV file should contains$")
    public void csvFileShouldBeContains(List<String> list) {
        String decodedString = getBase64SToCsv();
        for (String content : list) {
            Assert.assertTrue(decodedString.contains(content), String.format("decodedString : %s should contains : %s", decodedString, content));
        }
    }

    @And("^CSV file should not contains$")
    public void csvFileShouldNotContains(List<String> list) {
        String decodedString = getBase64SToCsv();
        for (String content : list) {
            Assert.assertFalse(decodedString.contains(content), String.format("decodedString : %s should not contains : %s", decodedString, content));
        }
    }

    @And("As user, I get voucher")
    public void asUserILoadDetailsVoucher(List<VoucherMS> list) {
        list.forEach(info->{
            voucherUser.getVoucher(info);
        });
    }

    @And("As user, I buy voucher")
    public void asUserIBuyVoucher(List<VoucherMS> list ) {
        list.forEach(info->{
            voucherUser.buyVoucher(info);
        });
    }


    @And("As user, I use voucher name {string} at country {string}")
    public void asUserIUseVoucher(String name, String country, List<UseVoucherMS> list ) {
        VoucherMS existedVoucher = VoucherMS.builder().name(name).country(country).build();
        list.forEach(info->{
            voucherUser.useVoucher(existedVoucher, info);
        });
    }


    @And("As user, I open link voucher")
    public void asUserIOpenLinkVoucher(List<VoucherMS> list ) {
        list.forEach(info->{
            voucherUser.openlinkVoucher(info);
        });
    }


    @And("As user, I load my wallet")
    public void asUserILoadMyWallet() {
        voucherUser.loadMyWallet();
    }

    @And("As user, I load vouchers")
    public void asUserILoadVouchers(List<VoucherMS> list) {
        list.forEach(info->{
            voucherUser.loadVouchers(info);
        });
    }

    @And("As guest, I load vouchers")
    public void asGuestILoadVouchers(List<VoucherMS> list) {
        list.forEach(info->{
            voucherGuest.loadVouchers(info);
        });
    }
}
