package stepdefinition;

import com.apis.globedr.business.ConnectionBus;
import com.apis.globedr.business.subAccount.FamilyBus;
import com.apis.globedr.model.request.connection.LoadFollowedOrgsRQ;
import com.apis.globedr.model.step.connection.ConnectionMS;
import com.apis.globedr.model.step.connection.InvitationMS;
import com.apis.globedr.helper.Data;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class ConnectionStep extends Data {

    ConnectionBus connectionBus = new ConnectionBus();
    FamilyBus familyBus = new FamilyBus();

    @And("^I load request connection list$")
    public void loadRequestConnectionList() {
        connectionBus.loadRequest();
    }


    @When("^I request connection to below user$")
    public void searchForUser(List<ConnectionMS> list) {
        list.forEach(connectionBus::requestConnection);
    }

    @And("^I load the followed org$")
    public void loadFollowedOrganizationList() {
        connectionBus.loadFollowOrgs();
    }


    @And("^I (FOLLOW|UNFOLLOW) business$")
    public void followOrganization(String isFollow, List<ConnectionMS> list) {

        if(isFollow.equalsIgnoreCase("FOLLOW")){
            list.forEach(connectionBus::followOrg);
        }else{
            list.forEach(connectionBus::unfollowOrg);
        }
    }



    @And("^I load my connection list$")
    public void loadConnectionList() {
        connectionBus.loads();
    }

    @And("^I (accept|decline) request connection$")
    public void loadAndAcceptDeclineConnectionList(String action, List<ConnectionMS> list) {
        if(action.equalsIgnoreCase("accept")){
            list.forEach(connectionBus::accept);
        }else{
            list.forEach(connectionBus::decline);
        }
    }

    @And("^I count my request connection$")
    public void countMyRequestConnection() {
        new ConnectionBus().countRequest();
    }


    @Then("^My connection list should contain \"([^\"]*)\"$")
    public void checkConnection( String expectedName){
        boolean match = connectionBus.loads().stream().anyMatch( con -> con.getUser().getUserName().equalsIgnoreCase(expectedName));
        Assert.assertTrue(match, "Thằng " + expectedName + " không có ở trong connection list");
    }


    @And("^I (accept|decline) all request connection$")
    public void actionOnConnectionRequest(String isAccpet) throws Exception {

        if (isAccpet.equalsIgnoreCase("ACCEPT")) {
            connectionBus.accept();
        } else {
            connectionBus.decline();
        }

    }

    @And("^I remove connection$")
    public void removeConnection(List<ConnectionMS> list) {
        list.forEach(connectionBus::remove);
    }


    @Then("^I have '(\\d+)' connections( request)?$")
    public void checkConnectionList(int expectedResult, String isRequest) throws Exception {
        if (isRequest == null) {
            Assert.assertEquals(response.extractAsList("data.list[*]").size(), expectedResult);
        } else {
            List<String> listConnectionSig = response.extract("data.list[?(@.connectionSig != null)].connectionSig");
            Assert.assertEquals(listConnectionSig.size(), expectedResult);
        }
    }

    @And("^I invitation user$")
    public void iInvitationUser(List<InvitationMS> list ) {
        list.forEach(connectionBus::invite);
    }


    @And("I load list org that i can share a account name {string} to it")
    public void iLoadListOrgThatICanShareAAccountNameToIt(String subName, LoadFollowedOrgsRQ body) {
        String subSig = familyBus.loads(subName).get(0).getUserSignature();
        connectionBus.loadFollowOrgsToShare(subSig, body);
    }

}
