package stepdefinition;

import com.apis.globedr.business.consult.*;
import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.business.other.OtherBus;
import com.apis.globedr.business.provider.ProviderBus;
import com.apis.globedr.business.subAccount.AbsSubAccountBus;
import com.apis.globedr.business.subAccount.FamilyBus;
import com.apis.globedr.model.request.consult.LoadQuestionStatisticRQ;
import com.apis.globedr.model.response.consult.AddCommentRS;
import com.apis.globedr.model.response.consult.QuestionRS;
import com.apis.globedr.model.step.chat.ChatMS;
import com.apis.globedr.model.step.consult.ConsultMS;
import com.apis.globedr.model.step.health.HealthDocMS;
import com.apis.globedr.model.step.health.HealthMS;
import com.apis.globedr.model.step.provider.GiftPointMS;
import com.apis.globedr.model.step.subAccount.SubAccountMS;
import com.apis.globedr.helper.Data;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.*;

public class ConsultantStep extends Data {
    AbsSubAccountBus familyBus = new FamilyBus();
    HealthBus healthBus = new HealthBus();

    ConsultSender consultUser = new ConsultUser();
    ConsultManager consultCoor = new ConsultCoor();
    ConsultRecipient consultAuditor = new ConsultAuditor();
    ConsultRecipient consultDoctor = new ConsultDoctor();

    OtherBus otherBus = new OtherBus();


    private String msgSig = "";
    private Integer postID;
    private String patientSig = "";
    private String sigSharedHealth = "";




    @And("^As coordinator, I assign question to doctor name \"([^\"]*)\"$")
    public void assignQuestion(String name, List<ConsultMS> list) {
        list.forEach(info -> {
            consultCoor.assignQuestion(name, info);
        });
    }

    @And("^As coordinator, I submit question to auditor name \"([^\"]*)\"$")
    public void submitQuestion(String name, List<ConsultMS> list) {
        list.forEach(info -> {
            consultCoor.submitQuestion(name, info);
        });
    }

    @And("^As coordinator, I close questions$")
    public void closeQuestion(List<ConsultMS> list) {
        list.forEach(consultCoor::closeQuestion);
    }

    @And("^As coordinator, I set questions are spam$")
    public void setQuestionAreSpam(List<ConsultMS> list) {
        list.forEach(consultCoor::spamQuestion);
    }

    @And("^As (doctor|auditor), I accept questions$")
    public void acceptQuestion(String role, List<ConsultMS> list) {
        list.forEach(info -> {
            ConsultFactory.initRecipient(role).acceptQuestion(info);
        });
    }


    @And("^As (doctor|auditor), I give back questions$")
    public void giveBackQuestion(String role, List<ConsultMS> list) {
        list.forEach(info -> {
            ConsultFactory.initRecipient(role).giveBackQuestion(info);
        });
    }

    @And("^As (doctor|auditor), I decline questions$")
    public void declineQuestion(String role, List<ConsultMS> list) {
        list.forEach(info -> {
            ConsultFactory.initRecipient(role).declineQuestion(info);
        });
    }


    @And("^As (doctor|auditor), I complete questions$")
    public void completeQuestion(String role, List<ConsultMS> list) {
        list.forEach(info -> {
            ConsultFactory.initRecipient(role).completeQuestion(info);
        });
    }

    @And("^As (doctor|auditor|user|coordinator), I add comment \"([^\"]*)\" into consult$")
    public void addCommnent(String role, String comment, List<ConsultMS> list) {
        list.forEach(info -> {
            ConsultFactory.init(role).addComment(comment, info);
        });
    }


    @And("^As auditor, I reject this questions$")
    public void rejectThisQuestion(List<ConsultMS> list) {
        list.forEach(consultAuditor::rejectQuestion);
    }

    @And("^As auditor, I approve this questions$")
    public void ApproveThisQuestion(List<ConsultMS> list) {
        list.forEach(consultAuditor::approveQuestion);
    }

    @Then("^Post must be sorted like following order$")
    public void postMustBeSortedLikeFollowingOrder(List<String> Postlist) {
        List<String> pathName = response.extract("data.list[*].rootMsg.msg");

        pathName = new ArrayList<>();
        String[] msgArr = new String[pathName.size()];
        msgArr = pathName.toArray(msgArr);

        String[] expectedArr = new String[Postlist.size()];
        expectedArr = Postlist.toArray(expectedArr);

        for (int i = 0; i < msgArr.length; i++) {
            Assert.assertEquals(msgArr[i], expectedArr[i]);
        }
    }

    @And("^As coordinator, I send notification to assigned doctor into consult$")
    public void iSendNotificationToAssignedDoctor(List<ConsultMS> list) {
        list.forEach(consultCoor::sendNotiToDoctor);
    }


    @And("^As (doctor|auditor|coordinator), I send notification to patient into consult$")
    public void doctorSendNotificationToPatient(String role, List<ConsultMS> list) {
        list.forEach(ConsultFactory.init(role)::sendNotiToPatient);
    }

    @And("^As coordinator, I send notification to assigned auditor into consult$")
    public void iSendNotificationToAssignedAuditor(List<ConsultMS> list) {
        list.forEach(consultCoor::sendNotiToAuditor);
    }

    @And("^As (doctor|auditor|coordinator), I get activity log$")
    public void coordinatorGetActivityLog(String role, List<ConsultMS> list) {
        list.forEach(ConsultFactory.init(role)::getActivityLog);
    }


    @And("^As (user|coordinator|doctor|auditor), I load questions$")
    public void iLoadQuestions(String role, List<ConsultMS> list) {
        list.forEach(ConsultFactory.init(role)::loadQuestions);
    }


    @And("^As user, I add file \"([^\"]*)\" into below consult$")
    public void iAddFileWithName(String file, List<ConsultMS> list) {
        list.forEach(info -> {
            consultUser.addFileIntoConsult(file, info);
        });
    }


    @And("^I search question with ID$")
    public void iSearchQuestionWithID() {
        consultCoor.loadQuestions(ConsultMS.builder().postId(postID).build());
    }


    @And("^I check msg return should be \"([^\"]*)\"$")
    public void iCheckMsgReturnShuoldBe(String content) throws Throwable {
        List<String> msg = response.extract("data.list[*].rootMsg.msg");
        Assert.assertTrue(msg.contains(content));
    }


    @And("^I load question statistic$")
    public void iLoadQuestionStatistic(List<LoadQuestionStatisticRQ> list) {
        list.forEach(consultCoor::loadQuestionStatistic);

    }

    @And("^I export question statistic$")
    public void iExportQuestionStatistic(List<LoadQuestionStatisticRQ> list) {
        list.forEach(consultCoor::exportQuestionStatistic);

    }

    @And("^I get shared account information$")
    public void iGetSharedAccountInformation() {
        SubAccountMS body = SubAccountMS.builder().userSig(patientSig).build();
        familyBus.sharedAccountInformation(body);
        if (response.isSuccess()) {
            sigSharedHealth = response.extract("data.info.userSignature");
        }
    }

    @And("^As (user|coordinator|doctor|auditor), I get actions into consult$")
    public void iGetActionForUserInQuestion(String role, ConsultMS info) {
        ConsultFactory.init(role).getActions(info);

        if (response.isSuccess()) {
            patientSig = response.extract("data.patientSig");
            userSig = patientSig;
            sigSharedHealth = patientSig;
        }
    }




    @When("^I view health dashboard$")
    public void iViewHealthDashboard() {
        HealthMS body = HealthMS.builder().userSig(sigSharedHealth).build();
        healthBus.healthDashboard(body);
    }

    @And("^I create a question to GlobeDr with below info$")
    public void iCreateAQuestion(List<ConsultMS> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            info.setIsPatient(true);

            AddCommentRS rs = consultUser.createQuestion(info);
            if (response.isSuccess()) {
                postSig = rs.getPostSig();
                postID = rs.getPostId();
            }
        });


    }



    @And("^I get connected doctor with name \"([^\"]*)\"$")
    public void iGetConnectedDoctorWithName(String name) {
        List<String> listDoctorSig = response.extract(String.format("data.connected[?(@.name=='%s')].sig", name));
        doctorSig = listDoctorSig.get(0);
    }

    @And("^I get doctor with name \"([^\"]*)\"$")
    public void iGetDoctorWithName(String name) {
        List<String> listDoctorSig = response.extract(String.format("data.other[?(@.name=='%s')].sig", name));
        doctorSig = listDoctorSig.get(0);
    }





    @And("^As (user|coordinator|doctor|auditor), I load comment of question$")
    public void iLoadCommentOfQuestion(String role, List<ConsultMS> list) {
        list.forEach(ConsultFactory.init(role)::loadComments);
    }


    @And("I rate {int} score with comment {string} into consult")
    public void iRateScoreWithCommentIntoConsult(int score, String review, List<ConsultMS> list) {
        list.forEach(info -> {
            consultUser.reviewQuestion(score, review, info);
        });
    }

    @And("^I load list review doctor$")
    public void iLoadListReviewDoctor() {
        consultCoor.questionReviews();
    }

    @And("I send document with below information into above consult")
    public void iSendDocumentWithBelowInformationIntoAboveConsult(List<HealthDocMS> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            info.setDocSig(healthBus.loadHealthDocs(info).get(0).getDocSig());
            info.setPostSig(postSig);
            consultUser.addHealthDoc(info);
        });


    }


}
