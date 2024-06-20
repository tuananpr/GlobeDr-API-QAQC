/**
 * Hook all step for info.cukes
 * Notes 31/12/2020: block all step because we change Hooks  (info.cuke.cucumber) to LoggerPlugin (io.cucumber).
 *
 *
 */


//import com.globedr.core.*;
//import com.globedr.database.PostDB;
//import com.globedr.database.ProfileDB;
//import net.masterthought.cucumber.Configuration;
//import net.masterthought.cucumber.ReportBuilder;
//import net.masterthought.cucumber.Reportable;
//import net.masterthought.cucumber.presentation.PresentationMode;
//import net.masterthought.cucumber.reducers.ReducingMethod;
//import com.apis.globedr.helper.FileHelper;
//import com.com.apis.globedr.apis.globedr.util.Data;
//import gherkin.formatter.Argument;
//import gherkin.formatter.Formatter;
//import gherkin.formatter.NiceAppendable;
//import gherkin.formatter.Reporter;
//import gherkin.formatter.model.*;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//

//public class Hooks implements Formatter, Reporter {
//    private NiceAppendable output;
//    protected LinkedList<String> logSteps;
//
//    public Hooks(Appendable appendable) {
//        output = new NiceAppendable(appendable);
//        output.println("CustomFormatter()");
//
//        Logger.getInstance()..info("Adding observers to requestUtil and sqlDB");
//        RestEvent.getInstance().attach(Logger.getInstance().);
//        RestEvent.getInstance().attach(new Data());
//        RestEvent.getInstance().attach(new Report());
//
//        //ProfileDB.getInstant().attach(new Report());
//        ProfileDB.getInstant().attach(Logger.getInstance().);
//        //PostDB.getInstant().attach(new Report());
//        PostDB.getInstant().attach(Logger.getInstance().);
//
//        RestEvent.getInstance().attach(new Assert());
//
//    }
//
//
//    @Override
//    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
//
//    }
//
//    @Override
//    public void uri(String uri) {
//
//    }
//
//    @Override
//    public void feature(Feature feature) {
//
//    }
//
//    @Override
//    public void scenarioOutline(ScenarioOutline scenarioOutline) {
//        logSteps = new LinkedList<String>();
//    }
//
//    @Override
//    public void examples(Examples examples) {
//
//    }
//
//    @Override
//    public void startOfScenarioLifeCycle(Scenario scenario) {
//        Logger.getInstance()..info("**********************************");
//        Logger.getInstance()..info("Scenario name: " + scenario.getName());
//        Logger.getInstance()..info("**********************************");
//        Logger.getInstance()..info("Clear all data into Data");
//        Data.clear();
//        logSteps = new LinkedList<String>();
//
//    }
//
//    @Override
//    public void background(Background background) {
//
//    }
//
//    @Override
//    public void scenario(Scenario scenario) {
//        logSteps = new LinkedList<String>();
//
//    }
//
//    @Override
//    public void step(Step step) {
//        //System.out.println("STEPS IN SCENARIO: " + step.getKeyword() + step.getName());
//        List<Argument> a  = step.getOutlineArgs();
//        String stepParams = "";
//        List<DataTableRow> rows = step.getRows();
//        if (rows != null) {
//            Iterator<DataTableRow> rowsIterator = rows.iterator();
//            while (rowsIterator.hasNext()) {
//                String stepDataTableLine = rowsIterator.next().getCells().toString();
//                stepParams = stepParams + stepDataTableLine;
//            }
//        }
//
//        logSteps.addLast(step.getKeyword() + step.getName() + " " + stepParams);
//
//
//    }
//
//    @Override
//    public void endOfScenarioLifeCycle(Scenario scenario) {
//
//    }
//
//    @Override
//    public void done() {
//    }
//
//    @Override
//    public void close() {
//        File reportOutputDirectory = new File("target");
//        List<String> jsonFiles = new ArrayList<>();
//        jsonFiles.add("target/result.json");
//        String projectName = "cucumberProject";
//
//        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
//        configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
//        configuration.addReducingMethod(ReducingMethod.SKIP_EMPTY_JSON_FILES);
//
//        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
//        Reportable report = reportBuilder.generateReports();
//
//    }
//
//    @Override
//    public void eof() {
//    }
//
//    @Override
//    public void before(Match match, Result result) {
//
//    }
//
//    @Override
//    public void result(Result result) {
//        if (result.getStatus() != Result.PASSED){
//            logSteps.clear();
//        }
//    }
//
//    @Override
//    public void after(Match match, Result result) {
//
//    }
//
//    @Override
//    public void match(Match match) {
//        //Print steps on logs
//        if (match.getLocation() != null) {
//            if(!logSteps.isEmpty()){
//                Logger.getInstance()..info(logSteps.pollFirst());
//            }
//        }
//    }
//
//    @Override
//    public void embedding(String mimeType, byte[] data) {
//
//    }
//
//    @Override
//    public void write(String text) {
//
//    }
//}
