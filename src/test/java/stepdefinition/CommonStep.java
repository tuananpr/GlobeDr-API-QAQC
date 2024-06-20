package stepdefinition;

import com.apis.globedr.helper.Wait;
import com.apis.globedr.security.AlgorithmSecurity;
import org.testng.Assert;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apis.globedr.helper.Data;
import com.apis.globedr.services.database.dao.UserVerificationDAO;
import com.apis.globedr.enums.VerifyType;
import com.apis.globedr.helper.Common;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import util.DecryptRequest;

import java.util.List;
import java.util.Map;

public class CommonStep extends Data {

    public Integer statusCode;

    @And("^I wait for '(\\d+)' seconds$")
    public void wait(int seconds) {
        Wait.seconds(seconds);
    }

    @Given("^Decimal \"([^\"]*)\" to Binary converter$")
    public void decimalToBinaryConverter(int decimal) {
        // converting to binary and representing it in a string
        String bin = Integer.toBinaryString(decimal);
        System.out.println(bin);
    }


    @When("get verify code while recovery password for number phone \"([^\"]*)\" with country \"([^\"]*)\"")
    public void getVerifyCodeWhileRecoveryPasswordForNumberPhoneWithCountry(String sdt, String country) {
        UserVerificationDAO userVerificationDAO = new UserVerificationDAO();
        String code = userVerificationDAO.get(Common.convertFullPhone(sdt, country), VerifyType.RecoveryPasswd.value()).getVerifyCode();
        System.out.println("Recoverfy password : " + code);
    }

    @And("^I get status code$")
    public void igetStatusCode() {
        statusCode = response.getStatusCode();
    }

    @And("^I get Token-Expired on Headers$")
    public void igetStatusCodeAndHeader() {
        if (response.getHeaders().get("Token-Expired") != null) {
            tokenExpired = response.getHeaders().get("Token-Expired").getValue();
        }
    }

    @And("^List \"([^\"]*)\" of response has object with below info$")
    public void listHasObjectWithBelowInfo(String path, Map<String, Object> dataTable) {
        List<Map<String, Object>> list = response.extract(path);

        Boolean isResult = null;
        for (Map<String, Object> actual : list) {
            isResult = true;
            for (Map.Entry<String, Object> entry : dataTable.entrySet()) {
                System.out.println(String.format("actual %s, expected %s, result : %s", actual.get(entry.getKey()), entry.getValue(),
                        String.valueOf(actual.get(entry.getKey())).equalsIgnoreCase(String.valueOf(entry.getValue()))));
                if (!String.valueOf(actual.get(entry.getKey())).equalsIgnoreCase(String.valueOf(entry.getValue()))) {
                    isResult = false;
                    break;
                }
            }
            if (isResult) break;
        }
        Assert.assertTrue(isResult);


    }

    @When("I encrypt content")
    public void iEncryptContent(String body) {
        AlgorithmSecurity security = new AlgorithmSecurity();

        try {
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            System.out.println("Body after encrypt :\n" + gson.toJson(security.encrypt(body)));
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @When("I decrypt content")
    public void iDecryptContent(String body) {

        System.out.println(DecryptRequest.decryptRequest(body));

    }


}
