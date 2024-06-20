package stepdefinition;

import com.apis.globedr.business.noti.NotiBus;
import com.apis.globedr.helper.*;
import com.apis.globedr.model.response.noti.ConfigAzurePusherRS;
import com.apis.globedr.model.response.noti.PusherTeleCall;
import com.apis.globedr.constant.API;
import com.apis.globedr.pusher.PusherUtil;
import com.rest.core.debug.CucumberReport;
import com.rest.core.debug.Logger;
import com.rest.core.response.Response;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.testng.Assert;
import io.cucumber.java.en.Given;


public class PusherStep extends Data {

    NotiBus notiBus = new NotiBus();
    io.cucumber.java.Scenario report;
    Response cfgPusherRS;
    int timeout = 240;
    int normalTime = 10;
    ConfigAzurePusherRS rs;


    @Before
    public void start(Scenario scenario) {
        report = scenario;
    }


    @Given("^On Pusher, \"(.*?)\" subscribe all channels$")
    public void OnPusherIsubscribeallchannels(String user) {
        // remove temp com.apis.globedr.pusher file

        //FileHelper.delete(String.format("%spusher%s.json", Path.TARGET, user));
        rs = notiBus.getConfigWeb();

        if (!response.isSuccess()) Assert.fail(String.format("Get config is fail : %s ", response.asString()));
        cfgPusherRS = response;

        pushers.put(user, new PusherUtil());

//        String cmd = String.format("java -cp %s main.Run %s %s %s %s %s",
//                Path.TEST_RESOURCE + "libs/Pusher-with-dependencies.jar",
//                "subscribe",
//                API.getDomain(),
//                cfgPusherRS.getBody().asString(),
//                timeout,
//                String.format("%spusher%s.json", Path.TARGET, user)
//        );
//
//
//        CmdThread runner = new CmdThread(cmd);
//        runner.start();
//        Wait.seconds(normalTime); // wait for subscribe com.apis.globedr.pusher successfully (because jenkins subscribe slowly)

        pushers.get(user).config(
                API.getDomain() + rs.getPusherConfig().getAuthEndpoint(),
                rs.getPusherConfig().getKey(),
                rs.getPusherConfig().getCluster(),
                rs.getPusherConfig().getEncrypted(),
                rs.getPusherChannels(),
                240
        );

        pushers.get(user).subscribe();
//        PusherUtil.getInstance().config(
//                API.getDomain() + rs.getPusherConfig().getAuthEndpoint(),
//                rs.getPusherConfig().getKey(),
//                rs.getPusherConfig().getCluster(),
//                rs.getPusherConfig().getEncrypted(),
//                rs.getPusherChannels(),
//                240
//        );
//        PusherUtil.getInstance().subscribe();
        Wait.seconds(normalTime);
    }

    @Given("^On Pusher, \"(.*?)\" unsubscribe all channels$")
    public void OnPusherUnubscribeallchannels(String user) {
//        String cmd = String.format("java -cp %s main.Run %s %s %s %s %s",
//                Path.TEST_RESOURCE + "libs/Pusher-with-dependencies.jar",
//                "unsubscribe",
//                API.getDomain(),
//                cfgPusherRS.getBody().asString(),
//                timeout,
//                String.format("%spusher%s.json", Path.TARGET, user)
//        );
//
//        CmdThread runner = new CmdThread(cmd);
//        runner.start();

        //  PusherUtil.getInstance().unsubscribe();
        pushers.get(user).unsubscribe();
    }

    @Given("^On Pusher, \"(.*?)\" get information")
    public void OnPusherIGetInformation(String user) {
        String content = String.format("Information on Pusher of user '%s' \n", user);
        Wait.seconds(normalTime);
//        Wait.seconds(normalTime);  // wait for subscribe com.apis.globedr.pusher successfully (because jenkins subscribe slowly)
//        String path = String.format("%spusher%s.json", Path.TARGET, user);
//        if (!FileHelper.isExistFile(path)) Assert.fail(String.format("Not found file %s", path));
//
//        String json = FileHelper.readFileToString(path);
//
//        pusherTeleCall = Common.convert(json, PusherTeleCall.class);
//
//        FileHelper.delete(path);

        //pusherTeleCall = Common.mapping(PusherUtil.getInstance().getListener().getPusherEvents().getLast(), PusherTeleCall.class);
        CucumberReport.write(content + pushers.get(user).getListener().getPusherEvents());
        Logger.getInstance().info(content + pushers.get(user).getListener().getPusherEvents());
        pusherTeleCall = Common.mapping(pushers.get(user).getListener().getPusherEvents().getLast(), PusherTeleCall.class);
    }


    @Given("^On Pusher, I not found information from \"(.*?)\"")
    public void OnPusherINotFoundInformationFrom(String user) {
        Assert.assertEquals(pushers.get(user).getListener().getPusherEvents().size(), 0, String.format("I must be not receive pusher"));
    }


}
