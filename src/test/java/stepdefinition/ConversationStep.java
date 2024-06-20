package stepdefinition;

import com.apis.globedr.business.appointment.AptBus;
import com.apis.globedr.business.appointment.AptManageBus;
import com.apis.globedr.business.chat.*;

import com.apis.globedr.business.ConnectionBus;
import com.apis.globedr.business.medicalTest.OrderBus;
import com.apis.globedr.business.search.UserSearchBus;
import com.apis.globedr.model.general.Recipient;
import com.apis.globedr.model.request.chat.*;
import com.apis.globedr.model.step.appointment.ApptMS;
import com.apis.globedr.model.step.chat.ChatMS;
import com.apis.globedr.model.step.chat.CustomerCaresMS;
import com.apis.globedr.model.step.connection.ConnectionMS;
import com.apis.globedr.model.step.order.OrderMS;
import io.cucumber.java.en.*;
import com.apis.globedr.enums.*;
import com.apis.globedr.helper.Data;
import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.Common;
import com.apis.globedr.model.response.chat.ConversationRS;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Collectors;


public class ConversationStep extends Data {
    public String converSig;

    ConnectionBus connectionBus = new ConnectionBus();
    AbsChatBus userChatBus = new UserChatBus();
    OrgChatBus orgChatBus = new OrgChatBus();
    OrderBus orderBus = new OrderBus();
    AptBus aptBus = new AptBus();
    AptManageBus aptManageBus = new AptManageBus();
    UserSearchBus searchBus = new UserSearchBus();
    OrgChatMedicalBus orgChatMedical = new OrgChatMedicalBus();
    StaffChatMedicalBus staffChatMedical = new StaffChatMedicalBus();
    OrgChatAptBus orgChatApt = new OrgChatAptBus();

    @And("^As user, I load conversations$")
    public void userLoadConversation(ChatMS info) {
        userChatBus.loads(info);
    }

    @And("^As manager, I load conversations$")
    public void orgLoadConversation(ChatMS info) {
        info.setOrgSig(orgSig);
        orgChatBus.loads(info);
    }

    @And("^As customer care, I load conversations$")
    public void ccLoadConversation(ChatMS info) {
        orgLoadConversation(info);
    }


    @And("^As user, I load conversation details$")
    public void userLoadConversationDetail(ChatMS info) {
        userChatBus.loadConversation(info);
        converSig = response.extract("data.conversation.conversationSig");
    }

    @And("^As user, I load customer cares")
    public void userLoadCC(CustomerCaresMS info) {
        info.setOrgSig(orgSig);
        orgChatBus.customerCares(info);
    }

    @And("^As customer care, I load customer care status")
    public void ccLoadCCStatus() {
        CustomerCaresMS info = new CustomerCaresMS();
        info.setOrgSig(orgSig);
        orgChatBus.customerCareStatus(info);
    }


    @And("^As customer care, I set online$")
    public void ccOnline() {
        TurnOnCustomerCareRQ info = new TurnOnCustomerCareRQ();
        info.setOrgSig(orgSig);
        info.setIsOnline(true);
        orgChatBus.turnOnCustomerCare(info);
    }

    @And("^As customer care, I set offline$")
    public void ccOffline() {
        TurnOnCustomerCareRQ info = new TurnOnCustomerCareRQ();
        info.setOrgSig(orgSig);
        info.setIsOnline(false);
        orgChatBus.turnOnCustomerCare(info);
    }



    @And("^As manager, I load conversation details$")
    public void orgLoadConversationDetail(ChatMS info) {
        info.setOrgSig(orgSig);
        info.setViewerSig(userSig);
        orgChatBus.loadConversation(info);
        converSig = response.extract("data.conversation.conversationSig");
    }

    @And("^I open msg on conversation")
    public void openMsgOnConversation(List<ChatMS> list) {
        userChatBus.openMsg(list.get(0));
    }


    @And("^I load msg on conversation")
    public void userLoadMsgOnConversation(ChatMS info) {
        userChatBus.loadMsgs(info);
    }

    @And("^As manager, I load msg on conversation")
    public void orgLoadMsgOnConversation(List<ChatMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            info.setIsOrg(true);
            orgChatBus.loadMsgs(info);
            converSig = response.extract("data.list[0].conversationSig");
        });
    }


    @And("^I click (voucher|article|topdeal) on conversation")
    public void clickObjectOnConversation(String type, List<ChatMS> list) {
        clickMsgOnConversation(list);
    }

    @And("^I click msg on conversation")
    public void clickMsgOnConversation(List<ChatMS> list) {
        userChatBus.clickMsg(list.get(0));
    }


    @And("^The recipient list should( not)? contain$")
    public void checkRecipientInConversation(String isContain, List<String> expectedResult) {

        LoadRecipientsRQ body = new LoadRecipientsRQ();
        body.setConversationSig(converSig);

        boolean isExisted = userChatBus.loadRecipientNames(body).containsAll(expectedResult);
        if (isContain == null) {
            Assert.assertTrue(isExisted);
        } else {
            Assert.assertFalse(isExisted);
        }
    }


    @And("^I change avatar conversation$")
    public void iAddAvatarImageForConversation(List<ChatMS> list) {
        list.forEach(userChatBus::setAvatar);
    }


    @And("^The MSG return should be contain \"([^\"]*)\"$")
    public void theMSGReturn(String content) {
        List<String> msgList = response.extract("data.list[*].msg");
        Assert.assertTrue(msgList.contains(content));
    }


    @And("^As user, I create conversation with user$")
    public void createConversationsToUser(List<ChatMS> list) {
        list.forEach(info -> {
            List<Recipient> recipients = info.getRecipientName().stream()
                    .flatMap(name -> connectionBus.loads(name).stream()
                            .map(connect -> new Recipient(RecipientType.User.value(), connect.getUser().getUserSig()))
                    )
                    .collect(Collectors.toList());

            info.setSenderSig(userSig);
            info.setConversationSig(userChatBus.createConversation(recipients, ChatType.UserPersonal).getConversationSig());
            userChatBus.send(info);
        });
    }

    @And("^As user, I create conversation with org$")
    public void createConversationsToOrg(List<ChatMS> list) {
        list.forEach(info -> {
            List<Recipient> recipients = info.getRecipientName().stream()
                    .flatMap(name -> searchBus.loadOrgsByName(name).stream()
                            .map(org -> new Recipient(RecipientType.Org4Admin.value(), org.getSig()))
                    )
                    .collect(Collectors.toList());

            info.setSenderSig(userSig);
            info.setConversationSig(userChatBus.createConversation(recipients, ChatType.CustomerCare).getConversationSig());
            userChatBus.send(info);
        });

    }

    @And("As user, I create conversation to appointment with content {string}")
    public void asUserICreateConversationToAppointment(String msg, List<ApptMS> list) {
        list.forEach(info -> {
            info.setPageDashboard(PageDashboard.PageProfile);
            info.setUserMode(UserType.Patient);

            Recipient recipients = Recipient.builder()
                    .recipSig(aptBus.info(info).getToSig())
                    .type(RecipientType.Org4Admin.value()).build();

            ChatMS body = ChatMS.builder()
                    .msg(msg)
                    .senderSig(userSig)
                    .conversationSig(userChatBus.createConversation(Arrays.asList(recipients), ChatType.Appointment).getConversationSig()).build();
            userChatBus.send(body);
        });
    }

    @And("As user, I create conversation to medical test with content {string}")
    public void asUserICreateConversationToOrder(String msg, List<OrderMS> list) {
        list.forEach(info -> {
            Recipient recipients = Recipient.builder()
                    .recipSig(orderBus.orderDetail(info).getPersonOrgInfo().getSig())
                    .type(RecipientType.Org4Admin.value()).build();

            ChatMS body = ChatMS.builder()
                    .msg(msg)
                    .senderSig(userSig)
                    .conversationSig(userChatBus.createConversation(Arrays.asList(recipients), ChatType.OrderMedicalTest).getConversationSig()).build();
            userChatBus.send(body);
        });
    }


    @And("As manager, I load msg of appointment")
    public void asOrgILoadMsgOfAppointment(ApptMS info) {
        info.setOrgSig(orgSig);
        Recipient recipients = Recipient.builder()
                .recipSig(aptManageBus.appts(info).get(0).getByUserSig())
                .type(RecipientType.User.value()).build();

        ChatMS body = ChatMS.builder()
                .recipients(Arrays.asList(recipients))
                .type(ChatType.Appointment.value())
                .ownerSig(orgSig).build();

        orgChatApt.loadMsgs(body);
    }


    @And("As manager, I load msg of medical test")
    public void asOrgILoadMsgOfMedicalTest(OrderMS info) {
        info.setOrgSig(orgSig);
        Recipient recipients = Recipient.builder()
                .recipSig(orderBus.orderDetail(info).getPatientInfo().getUserSig())
                .type(RecipientType.User.value()).build();

        ChatMS body = ChatMS.builder()
                .recipients(Arrays.asList(recipients))
                .type(ChatType.OrderMedicalTest.value())
                .ownerSig(orgSig).build();

        orgChatMedical.loadMsgs(body);
    }

    @And("As staff, I send msg {string} to medical test")
    public void asOrgISendMsgOfMedicalTest(String msg, OrderMS info) {
        info.setOrgSig(orgSig);
        Recipient recipients = Recipient.builder()
                .recipSig(orderBus.orderDetail(info).getPatientInfo().getUserSig())
                .type(RecipientType.User.value()).build();




        ConversationRS rs = staffChatMedical.createConversation(Arrays.asList(recipients), ChatType.OrderMedicalTest);

        ChatMS body = ChatMS.builder()
                .senderSig(orgSig)
                .conversationSig(rs.getConversationSig())
                .createDate(Common.today(Text.FTIME_FULL))
                .msg(msg)
                .build();

        staffChatMedical.send(body);
    }


    @And("^I check name return should be \"([^\"]*)\"$")
    public void iCheckNameReturn(String expected) {
        String name = response.extract("data.conversation.name");
        Assert.assertEquals(name, expected);
    }


    @And("^The conversation should have '(\\d+)' messages$")
    public void checkNumberOfMessageInConversation(int expectedResult) {
        List<Object> messageList = response.extract("data.list[*]");
        Assert.assertEquals(messageList.size(), expectedResult);
    }

    @When("^As user, I add message into conversation with content$")
    public void addMessageIntoConversation(List<ChatMS> list) {

        list.forEach(info -> {
            List<ConversationRS> conversationRS = userChatBus.loads(info);
            info.setSenderSig(userSig);
            info.setConversationSig(conversationRS.get(0).getConversationSig());
            userChatBus.send(info);
        });
    }

    @When("^As org, I add message into conversation with content$")
    public void orgSddMessageIntoConversation(List<ChatMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            info.setIsOrg(true);
            info.setConversationSig(orgChatBus.loads(info).get(0).getConversationSig());
            info.setSenderSig(orgSig);
            orgChatBus.send(info);
        });
    }

    @When("^As org, I done conversation$")
    public void orgDoneConversation(List<ChatMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            info.setIsOrg(true);
            info.setConversationSig(orgChatBus.loads(info).get(0).getConversationSig());
            orgChatBus.doneConversation(info);
        });
    }

    @And("^I change name my conversation to \"([^\"]*)\"$")
    public void changeNameConversation(String name) {
        ConversationNameRQ body = new ConversationNameRQ();
        body.setConversationSig(converSig);
        body.setConversationName(name);
        body.setViewerType(SenderType.User.value());
        userChatBus.changeConversationName(body);
    }

    @And("As org, I get actions into conversation")
    public void asOrgIGetActionsIntoConversation(ChatMS info) {
        info.setOrgSig(orgSig);
        info.setIsOrg(true);
        info.setConversationSig(orgChatBus.loads(info).get(0).getConversationSig());
        info.setViewerSig(orgSig);
        info.setViewerType(SenderType.Org);
        orgChatBus.actions(info);
    }

    @And("^I leave conversation$")
    public void leaveGroup(List<ChatMS> list) {
        list.forEach(userChatBus::leave);
    }


    @And("^I kick a member out of conversation$")
    public void iWantToRemoveOutOfConversation(List<ChatMS> list) {
        list.forEach(userChatBus::kickMember);
    }


    @When("^(I|User|Org) send image \"(.*)\" into conversation name \"(.*)\"$")
    public void sendImageIntoConversation(String senderType, String image, String conversationName) {
        List<ConversationRS> conversationRS = orgChatBus.loads(conversationName);
        UploadMsgDocsRQ body = new UploadMsgDocsRQ();
        body.setCreateDate(Common.format(Common.today(), Text.FTIME_FULL));
        body.setFiles(image);
        body.setConversationSig(conversationRS.get(0).getConversationSig());
        userChatBus.uploadMsgDocs(body);
    }

    @And("^I find conversation$")
    public void findConversationByRecipientName(ConnectionMS connectionMS) {
        List<Recipient> recipients = connectionBus.loads(connectionMS).stream()
                .map(c -> new Recipient(RecipientType.User.value(), c.getUser().getUserSig()))
                .collect(Collectors.toList());

        userChatBus.findConversation(null, recipients, ChatType.UserPersonal);
    }

    @And("^I load user and user conversation$")
    public void loadUserAndUserConversation(UserGroupsRQ info) {
        userChatBus.userGroups(info);
    }

    @When("^I search user \"([^\"]*)\" into my connection list$")
    public void iSearchUserIntoMyConnectionList(String name) {
        connectionBus.loads(name);
    }


    @When("I get seen")
    public void iGetSeen() {
        userChatBus.getSeen();
    }


    @And("I load customer care into org")
    public void iLoadCustomerCareIntoOrg(CustomerCaresMS info) {
        info.setOrgSig(searchBus.loadOrgsByName(info.getOrgName()).get(0).getSig());
        userChatBus.customerCares(info);
    }

    @And("I view customer care statistic")
    public void iViewCustomerCareStatistic(SubConversationsRQ info) {
        info.setOrgSig(orgSig);
        orgChatBus.subConversations(info);
    }

    @And("I load conversation types")
    public void iLoadConversationTypes() {
        CustomerCaresMS info = new CustomerCaresMS();
        info.setOrgSig(orgSig);
        orgChatBus.conversationTypes(info);
    }
}
