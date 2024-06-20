package com.apis.globedr.services;

import com.rest.core.debug.CucumberReport;
import com.rest.core.debug.Logger;



public class ServiceException extends RuntimeException {

    public ServiceException(final String message) {
        super(message);
        Logger.getInstance().info(message);
        CucumberReport.write(message);
    }

    public ServiceException(final String message, Throwable err) {
        super(message, err);
        Logger.getInstance().info(String.format("Error: %s, %s", err.getMessage(), message));
        CucumberReport.write(String.format("Error: %s, %s", err.getMessage(), message));
    }



}
