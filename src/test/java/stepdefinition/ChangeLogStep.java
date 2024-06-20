package stepdefinition;

import com.apis.globedr.business.changelog.*;

import com.apis.globedr.helper.Data;
import com.apis.globedr.model.request.changelog.LogsRQ;
import com.apis.globedr.model.response.changelog.ChangelogRS;
import com.apis.globedr.model.response.changelog.NewRS;
import com.apis.globedr.model.step.changelog.ChangelogMS;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;

public class ChangeLogStep extends Data {

    private String logSig;
    private String linkSig;

    ChangelogBus changelogNormal = new ChangelogNormalBus();
    ChangelogBus changelogAppVersion = new ChangelogAppVersionBus();
    ChangelogBus changelogVoucher = new ChangelogVoucherBus();
    ChangelogBus changelogArticle = new ChangelogArticleBus();

    private void createChangelog(ChangelogBus changelog, ChangelogMS info) {
        NewRS c = changelog.create(info);
        if (c != null) {
            logSig = c.getLogSig();
            linkSig = c.getLinkSig();
        }
    }

    private void updateChangelog(ChangelogBus changelog, ChangelogMS info) {
        info.setOrgSig(orgSig);
        info.setLogSig(logSig);
        changelog.update(info);
    }

    @When("^I create new changelog normal$")
    public void iCreateNewChangelogVoucher(List<ChangelogMS> list) {
        list.forEach(info -> {
            createChangelog(changelogNormal, info);
        });
    }

    @When("^I create new changelog appVersion$")
    public void iCreateChangelogAppversion(List<ChangelogMS> list) {
        list.forEach(info -> {
            createChangelog(changelogAppVersion, info);
        });
    }

    @When("^I create new changelog voucher$")
    public void iCreateChangelogVoucher(List<ChangelogMS> list) {
        list.forEach(info -> {
            createChangelog(changelogVoucher, info);
        });
    }

    @When("^I create new changelog article$")
    public void iCreateChangelogArticle(List<ChangelogMS> list) {
        list.forEach(info -> {
            createChangelog(changelogArticle, info);
        });
    }


    @When("^I update above changelog article$")
    public void iUpdateAboveChangelogArticle(List<ChangelogMS> list) {
        list.forEach(info -> {
            updateChangelog(changelogArticle, info);
        });
    }

    @When("^I update above changelog voucher")
    public void iUpdateAboveChangelogVoucher(List<ChangelogMS> list) {
        list.forEach(info -> {
            updateChangelog(changelogVoucher, info);
        });
    }

    @When("^I update above changelog appVersion")
    public void iUpdateAboveChangelogAppVersion(List<ChangelogMS> list) {
        list.forEach(info -> {
            updateChangelog(changelogAppVersion, info);
        });
    }

    @When("^I update above changelog normal")
    public void iUpdateAboveChangelogNormal(List<ChangelogMS> list) {
        list.forEach(info -> {
            updateChangelog(changelogNormal, info);
        });
    }


    @Given("^I load list changelog$")
    public List<ChangelogRS> iLoadListChangelog(ChangelogMS info) {
        info.setOrgSig(orgSig);
        return changelogNormal.list(info);
    }


    @When("^I remove changelog$")
    public void iRemoveChangelog(List<ChangelogMS> list) {
        list.forEach(info ->{
            info.setOrgSig(orgSig);
            info.setLogSigs(changelogNormal.getChangelogSigs(info));
            changelogNormal.remove(info);
        });

    }


    @When("^I view logs of changelog$")
    public void iViewLogsOfChangelog(LogsRQ info) {
        changelogNormal.logs(info);
    }


}
