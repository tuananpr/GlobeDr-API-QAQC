package util;

import com.apis.globedr.helper.Common;
import com.rest.core.debug.CucumberReport;
import com.rest.core.debug.Logger;

public class CmdThread extends Thread {
    private final String cmd;
    public CmdThread(String cmd ){
        this.cmd = cmd;
        Logger.getInstance().info("CmdThread : " + cmd);
        CucumberReport.write("CmdThread : " + cmd);
    }

    public void run(){
        Common.runCmd(cmd);
    }

    public void start(){
        super.start();
    }

}
