package stepdefinition;

import com.apis.globedr.business.UserBus;
import com.apis.globedr.constant.Text;
import com.apis.globedr.enums.PageDashboard;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.request.user.QRCodeScanRQ;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class QRCodeStep extends Data {

    UserBus userBus = new UserBus();


    @And("^I get QRCode on my org$")
    public void getQRCodeForOrg() {
        Data.set(Text.QR_CODE, userBus.getQRCodeOrg(orgSig).getQrCode());
    }

    @And("^I get my QRCode$")
    public void getQRCodeForUser() {
        Data.set(Text.QR_CODE, userBus.getQRCode(userSig).getQrCode());
    }




    @Then("^I check number membe return should be '(\\d+)'$")
    public void iCheckNumberMembe(int expected) {
        int actual = response.extract("data.totalCount");
        Assert.assertEquals(actual, expected);
    }

    @And("^I scan QR Code is not on the system$")
    public void iScanQRCodeIsNotOnTheSystem() {
        QRCodeScanRQ body = QRCodeScanRQ.builder()
                .qrCode("GH12J")
                .orgSig(orgSig)
                .pageDashboard(PageDashboard.PageManageOrg.value())
                .build();
        userBus.qrCodeScan(body);
    }


    @And("^As user, I scan above QRCode$")
    public void asUserIScanAboveAppointmentQRCode() {
        QRCodeScanRQ body = QRCodeScanRQ
                .builder()
                .qrCode(Data.get(Text.QR_CODE))
                .pageDashboard(PageDashboard.PageProfile.value())
                .build();
        userBus.qrCodeScan(body);


    }

    @And("^As user, I load info from above QRCode$")
    public void asUserILoadInfoFromAboveQRCode() {
        QRCodeScanRQ body = QRCodeScanRQ
                .builder()
                .qrCode(Data.get(Text.QR_CODE))
                .pageDashboard(PageDashboard.PageProfile.value())
                .build();
        userBus.loadInfoQRCode(body);
    }


    @And("^As guest, I load info from above QRCode$")
    public void asGuestILoadInfoFromAboveQRCode() {
        QRCodeScanRQ body = QRCodeScanRQ
                .builder()
                .qrCode(Data.get(Text.QR_CODE))
                .build();
        userBus.loadInfoQRCode(body);
    }


    @And("^As manager, I scan above QRCode$")
    public void asManagerIScanAboveAppointmentQRCode() {
        QRCodeScanRQ body = QRCodeScanRQ.builder()
                .qrCode(Data.get(Text.QR_CODE))
                .orgSig(orgSig)
                .pageDashboard(PageDashboard.PageManageOrg.value())
                .build();
        userBus.qrCodeScan(body);
    }


    @And("^As manager, I load info from above QRCode$")
    public void asManagerILoadInfoFromAboveQRCode() {
        QRCodeScanRQ body = QRCodeScanRQ.builder()
                .qrCode(Data.get(Text.QR_CODE))
                .orgSig(orgSig)
                .pageDashboard(PageDashboard.PageManageOrg.value())
                .build();
        userBus.loadInfoQRCode(body);
    }


    @And("^As doctor, I scan above QRCode$")
    public void asDoctorIScanAboveAppointmentQRCode() {
        QRCodeScanRQ body = QRCodeScanRQ.builder()
                .qrCode(Data.get(Text.QR_CODE))
                .orgSig(orgSig)
                .pageDashboard(PageDashboard.PageMedicalBussiness.value())
                .build();
        userBus.qrCodeScan(body);
    }

    @And("^As doctor, I load info from above QRCode$")
    public void asDoctorILoadInfoFromAboveQRCode() {
        QRCodeScanRQ body = QRCodeScanRQ.builder()
                .qrCode(Data.get(Text.QR_CODE))
                .orgSig(orgSig)
                .pageDashboard(PageDashboard.PageMedicalBussiness.value())
                .build();
        userBus.loadInfoQRCode(body);
    }

}
