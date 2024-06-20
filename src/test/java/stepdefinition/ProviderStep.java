package stepdefinition;

import com.apis.globedr.business.consult.ConsultFactory;
import com.apis.globedr.business.provider.ProviderBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.request.provider.LogReceiverFeesRQ;
import com.apis.globedr.model.request.provider.ReportFeesRQ;
import com.apis.globedr.model.response.consult.QuestionRS;
import com.apis.globedr.model.step.provider.GiftPointMS;
import io.cucumber.java.en.And;

import java.util.List;

public class ProviderStep extends Data {
    ProviderBus providerBus = new ProviderBus();
    @And("As doctor, I search user to gift point")
    public void doctorSearchUseToGiftPointr(GiftPointMS info) {
        providerBus.searchUser(info);
    }

    @And("As doctor, I gift point to user")
    public void doctorGiftPointToUser(List<GiftPointMS> list) {
        list.forEach(info ->{
            info.setUserSig(providerBus.searchAndGetUserSig(info));
            providerBus.giftPoint(info);
        });
    }

    @And("^As (doctor), I load question content \"([^\"]*)\" and gift point to user$")
    public void iLoadQuestionsAndGiftPoint(String role, String content, List<GiftPointMS> list) {

        list.forEach(info ->{
            QuestionRS question = ConsultFactory.init(role).loadQuestionsByContent(content).get(0);
            info.setUserSig(question.getSig(info.getName()));
            providerBus.giftPoint(info);
        });
    }

    @And("As doctor, I load report by org")
    public void asDoctorILoadReportByOrg() {
        providerBus.reportOrgs();
    }

    @And("As doctor, I load report fees")
    public void asDoctorILoadReportFees(ReportFeesRQ info) {
        providerBus.reportFees(info);
    }

    @And("As doctor, I load log fees")
    public void asDoctorILoadLogFees(LogReceiverFeesRQ info) {
        providerBus.logReceiverFees(info);
    }
}
