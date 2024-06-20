package com.apis.globedr.helper;

import com.rest.core.debug.CucumberReport;
import com.rest.core.debug.Logger;

public class Wait {

    private static int TIME_OUT = 20;

    public static void seconds(int number) {
        try {
            Logger.getInstance().info(String.format("wait %d seconds" , number));
            CucumberReport.write(String.format("wait %d seconds" , number));
            Thread.sleep(number * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void milliSeconds(int number) {
        try {
            Logger.getInstance().info(String.format("wait %d MilliSeconds" , number));
            CucumberReport.write(String.format("wait %d MilliSeconds" , number));
            Thread.sleep(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void until(boolean condition, boolean expected){
        int currentTime = 0;
        while (condition != expected && currentTime < TIME_OUT){
            milliSeconds(500);
            currentTime++;
        }
    }
}
