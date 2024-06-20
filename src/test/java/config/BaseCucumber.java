package config;


import com.apis.globedr.constant.API;
import com.apis.globedr.security.AlgorithmSecurity;
import com.apis.globedr.services.es.ElasticSearchApi;
import com.rest.core.debug.CucumberReport;
import com.rest.core.DatabaseEventHandling;

import com.rest.core.RestEventHandling;
import com.rest.core.events.DatabaseEventListener;
import com.rest.core.events.RestCoreEventListener;
import com.rest.core.observers.IObserver;
import com.rest.core.security.Security;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.reducers.ReducingMethod;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import com.apis.globedr.helper.Data;
import observers.AssertDefaultResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.rest.core.debug.Logger;

public class BaseCucumber {

    @BeforeClass
    public static void beforeClass() {
        // Only run once
        Logger.getInstance().info("Adding observers for rest core");
        IObserver logger = Logger.getInstance();
        IObserver report = new CucumberReport();
        IObserver Data = new Data();
        IObserver assertDefaultResponse = new AssertDefaultResponse();
        // API event

        RestEventHandling restEvent = (RestCoreEventListener.getEvent() == null) ? new RestEventHandling() : (RestEventHandling) RestCoreEventListener.getEvent();
        restEvent.attach(logger);
        restEvent.attach(report);
        restEvent.attach(Data);
        restEvent.attach(assertDefaultResponse);


        // DB event
        DatabaseEventHandling databaseEvent = (DatabaseEventListener.getEvent() == null) ? new DatabaseEventHandling() : (DatabaseEventHandling) DatabaseEventListener.getEvent();
        databaseEvent.attach(logger);
        databaseEvent.attach(report);

        Logger.getInstance().info("Register all event listeners");
        RestCoreEventListener.register(restEvent);
        DatabaseEventListener.register(databaseEvent);

        // Security : encrypt request and decrypt response
        Logger.getInstance().info("Register Algorithm security");
        Security.register(new AlgorithmSecurity());

        // Elastic Search
        Logger.getInstance().info("Set allow delete document on elastic search");
        ElasticSearchApi es = new ElasticSearchApi();
        es.allowDeleteMode();




        Logger.getInstance().info("Features will use api version : " + API.version());

    }

    @AfterClass
    public static void afterClass() {
        // Only run once
        File reportOutputDirectory = new File("target");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/result.json");
        String projectName = "cucumberProject";

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
        configuration.addReducingMethod(ReducingMethod.SKIP_EMPTY_JSON_FILES);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        Reportable report = reportBuilder.generateReports();


    }
}
