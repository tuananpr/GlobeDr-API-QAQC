package listeners;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import com.apis.globedr.helper.Data;

import java.util.List;
import com.rest.core.debug.Logger;

public class LoggerPlugin implements ConcurrentEventListener {

    Result resultOfPreStep = null;

    public void testRunStarted(TestRunStarted event) {

    }

    public void testSourceRead(TestSourceRead event) {

    }

    public void testCaseStarted(TestCaseStarted event) {
        Logger.getInstance().info("**********************************");
        Logger.getInstance().info("Scenario tags: " + String.join(", ", event.getTestCase().getTags()));
        Logger.getInstance().info("Scenario name: " + event.getTestCase().getKeyword() + " " + event.getTestCase().getName());
        Logger.getInstance().info("Scenario Id: " + event.getTestCase().getId());
        Logger.getInstance().info("**********************************");
        Logger.getInstance().info("Clear all data into Data");
        Data.clear();
        resultOfPreStep = null;

    }

    public void testStepStarted(TestStepStarted event) {

        if(resultOfPreStep == null || resultOfPreStep.getStatus().is(Status.PASSED)){
            if (event.getTestStep() instanceof PickleStepTestStep) {
                PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();

                String stepText = testStep.getStep().getKeyword() + testStep.getStep().getText();

                if (testStep.getStepArgument() != null) {
                    if (testStep.getStepArgument() instanceof DataTableArgument) {
                        stepText += "\n" + getTableString((DataTableArgument) testStep.getStepArgument());
                    }

                    if (testStep.getStepArgument() instanceof DocStringArgument) {
                        DocStringArgument data = (DocStringArgument) testStep.getStepArgument();
                        stepText += "\n" + data.getContent() + "\n";

                    }
                }
                Logger.getInstance().info(stepText);

            }
        }
    }

    public void stepDefinedEvent(StepDefinedEvent event) {

    }

    public void testStepFinished(TestStepFinished event) {
        resultOfPreStep = event.getResult();

    }


    public void testCaseFinished(TestCaseFinished event) {
        // Duration
        // Status

    }

    public void testRunFinished(TestRunFinished event) {
        // Duration
        // Status
    }

    public void embedEvent(EmbedEvent event) {

    }

    public void writeEvent(WriteEvent event) {

    }

    public void snippetsSuggestedEvent(SnippetsSuggestedEvent event) {

    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {


        eventPublisher.registerHandlerFor(TestRunStarted.class, this::testRunStarted);
        eventPublisher.registerHandlerFor(TestSourceRead.class, this::testSourceRead);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);

        eventPublisher.registerHandlerFor(TestStepStarted.class, this::testStepStarted);
        eventPublisher.registerHandlerFor(TestStepFinished.class, this::testStepFinished);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);

        eventPublisher.registerHandlerFor(TestRunFinished.class, this::testRunFinished);

        eventPublisher.registerHandlerFor(EmbedEvent.class, this::embedEvent);
        eventPublisher.registerHandlerFor(WriteEvent.class, this::writeEvent);
        eventPublisher.registerHandlerFor(StepDefinedEvent.class, this::stepDefinedEvent);
        eventPublisher.registerHandlerFor(SnippetsSuggestedEvent.class, this::snippetsSuggestedEvent);
    }



    private int getMaxLengthContent(List<List<String>> table, int collumnIndex) {
        int max = 0;
        for (List<String> row : table) {
            if (max < row.get(collumnIndex).length()) {
                max = row.get(collumnIndex).trim().length();
            }
        }

        return max;
    }

    private String contentWithPadRight(String content, int paddingOffset) {
        return String.format(" %-" + paddingOffset + "s|", content.trim());
    }

    private String getTableString(DataTableArgument dataTableArgument) {
        StringBuilder table = new StringBuilder();
        for (List<String> row : dataTableArgument.cells()) {
            String r = "|";
            for (int index = 0; index < row.size(); index++) {
                int rangePadding = getMaxLengthContent(dataTableArgument.cells(), index) + 5;
                r += String.format(contentWithPadRight(row.get(index).replace("%","%%"), rangePadding));
            }
            table.append(r + "\n");
        }
        return table.toString();
    }




}
