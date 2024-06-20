package com.apis.globedr.stepdefinition;

import com.apis.globedr.services.es.ElasticSearchApi;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class ElasticSearchStep  {

    ElasticSearchApi elasticSearchApi = new ElasticSearchApi();


    @And("^I get user with name \"([^\"]*)\"$")
    public void iGetUserWithName(String name) {
        elasticSearchApi.getUser(name);
    }


    @And("^I delete org with name \"([^\"]*)\"$")
    public void iDeleteOrgWithName(String name) {
        elasticSearchApi.deleteOrg(name);
    }


    @When("allow delete elastic search")
    public void allowDeleteElasticSearch() {

        elasticSearchApi.allowDeleteMode();
    }
}
