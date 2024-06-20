package stepdefinition;


import com.apis.globedr.business.other.OtherBus;
import com.apis.globedr.helper.Common;
import com.apis.globedr.model.general.Address;
import com.apis.globedr.services.ipGeo.IpGeo;
import com.apis.globedr.helper.Data;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import org.testng.Assert;

import java.util.Map;

public class OtherSteps extends Data {
    OtherBus otherBus = new OtherBus();

    @And("^I get regions of country (VN|US)$")
    public void iGetRegionsOfCountryVN(String country) {
        otherBus.getRegions(country);
    }

    @When("^I load meta data$")
    public void iLoadMetaData() {
        otherBus.loadMetaData();
    }

    @When("^I get download app$")
    public void iGetDownloadApp() {
        otherBus.getDownloadApp();
    }


    @When("^I get landing page$")
    public void iGetLandingPage() {
        otherBus.getLandingPage(1);
    }

    @When("^I check phone \"([^\"]*)\"$")
    public void iCheckPhone(String phone) {
        otherBus.checkPhone(phone);
    }


    @When("I detect my location")
    public void iDetectMyLocation() {
        otherBus.detectLocation();
    }

    @And("Response has country is match with my location")
    public void responseHasCountryIsMatchWithMyLocation() {
        IpGeo ipGeo = new IpGeo();
        Assert.assertEquals(response.extract("data.country"), ipGeo.detectMyCountry());
    }

    @When("I get host")
    public void iGetHost(Map<String, Object> dataTable) {
        otherBus.getHosts(Common.getMap(dataTable));
    }


    @When("I get countries")
    public void iGetCountries() {
        otherBus.getCountries();
    }

    @When("I get cities")
    public void iGetCities(Address info) {
        otherBus.getCity(info);
    }

    @When("I get districts")
    public void iGetDistricts(Address info) {
        otherBus.getDistrict(info);
    }


    @When("I get wards")
    public void iGetWards(Address info) {
        otherBus.getWards(info);
    }

}
