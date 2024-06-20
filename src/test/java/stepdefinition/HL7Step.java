package stepdefinition;

import com.apis.globedr.apis.*;

import io.cucumber.java.en.When;
import com.apis.globedr.helper.Data;

public class HL7Step extends Data {

    HL7Api hl7Api = HL7Api.getInstant();


    @When("^I get information from below insurance card \"([^\"]*)\"$")
    public void iGetInformationFromBelowInsurcanceCard(String strFromQRCodeOnInsuranceCard) {
        hl7Api.infoInsuranceCard(strFromQRCodeOnInsuranceCard);
    }


}
