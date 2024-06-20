package listeners;

import com.apis.globedr.services.database.influxdb.Influx;
import com.rest.core.RestEventHandling;
import com.rest.core.events.RestCoreEventListener;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import observers.InfluxDbObs;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

public class InfluxDbPlugin implements ConcurrentEventListener {

    Result resultOfPreStep = null;
    Influx influxDB = null;
    BatchPoints batchPoints = null;

    public InfluxDbPlugin() {
        influxDB = new Influx();
        batchPoints = influxDB.getBatchPoints();
        RestEventHandling restEvent = (RestCoreEventListener.getEvent() == null) ? new RestEventHandling() : (RestEventHandling) RestCoreEventListener.getEvent();
        restEvent.attach(new InfluxDbObs());
        RestCoreEventListener.register(restEvent);
    }

    public void testStepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();

            Point point = Point.measurement("steps")
                    .time(System.currentTimeMillis() - 100, TimeUnit.MILLISECONDS)
                    .tag("name", testStep.getStep().getKeyword() + testStep.getStep().getText())
                    .addField("durationInSeconds", event.getResult().getDuration().getSeconds())
                    .build();
            batchPoints.point(point);
            influxDB.write(batchPoints);
        }
    }

    public void testCaseFinished(TestCaseFinished event) {

        Point point = Point.measurement("scenarios")
                .time(System.currentTimeMillis() - 100, TimeUnit.MILLISECONDS)
                .tag("name", event.getTestCase().getName())
                .addField("tags", String.join(", ", event.getTestCase().getTags()))
                .addField("status", event.getResult().getStatus().toString())
                .addField("durationInSeconds", event.getResult().getDuration().getSeconds())
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
    }


    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {

        eventPublisher.registerHandlerFor(TestStepFinished.class, this::testStepFinished);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);

    }

}
