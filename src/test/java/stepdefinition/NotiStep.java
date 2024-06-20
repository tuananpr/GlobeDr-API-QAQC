package stepdefinition;



import com.apis.globedr.business.UserBus;
import com.apis.globedr.business.consult.ConsultUser;
import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.business.medicalTest.OrderBus;
import com.apis.globedr.business.noti.NotiBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.request.noti.ConfigAzurePusherRQ;
import com.apis.globedr.model.request.noti.RegisterDeviceRQ;
import com.apis.globedr.model.step.noti.NotiMS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;


public class NotiStep extends Data {

    NotiBus notiBus = new NotiBus();
    ConsultUser consultUser = new ConsultUser();
    UserBus userBus = new UserBus();
    OrderBus orderBus = new OrderBus();
    HealthBus healthBus = new HealthBus();

    @When("^I register device$")
    public void iRegisterDevice(RegisterDeviceRQ info) {
        notiBus.registerDevice(info);
    }

    @When("^I register device by GenInstallation$")
    public void iRegisterDeviceByGenInstallation(RegisterDeviceRQ info) {
        notiBus.genInstallation(info);
    }


    @And("^I get config$")
    public void getConfigOfWeb(ConfigAzurePusherRQ info) {
        notiBus.getConfig(info);
    }


    @And("I count unread")
    public void iCountUnread() {
        notiBus.countUnread();
    }


    @And("^I load notifications$")
    public void iGetAllNotificationOfAllGroup(NotiMS body) {
        userBus.getListNoti(body);
    }


    @And("I open noti from consult")
    public void iOpenNotiFromConsult(NotiMS body) {
        consultUser.loadComments(userBus.getObjectSig(body));
    }

    @And("I open noti from medical test")
    public void iOpenNotiFromMedicalTest(NotiMS body) {
        orderBus.orderDetail(userBus.getObjectSig(body));
    }

    @And("I open noti after visit")
    public void iOpenNotiAfterVisit(NotiMS body) {
        healthBus.loadVisitMedical(userBus.getNotification(body).getObj2().getSig());
    }

}
