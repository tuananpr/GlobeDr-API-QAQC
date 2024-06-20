package com.apis.globedr.model.response.noti;




public class AzureConfig {
    private String googleProjectId;
    private String googleAPIKey;
    private String hubName;
    private String azureConnectionString;
    private String azureConnectionStringListen;

    public AzureConfig(){}
    public AzureConfig(String googleProjectId, String googleAPIKey, String hubName, String azureConnectionString, String azureConnectionStringListen) {
        this.googleProjectId = googleProjectId;
        this.googleAPIKey = googleAPIKey;
        this.hubName = hubName;
        this.azureConnectionString = azureConnectionString;
        this.azureConnectionStringListen = azureConnectionStringListen;
    }

    public String getGoogleProjectId() {
        return googleProjectId;
    }

    public void setGoogleProjectId(String googleProjectId) {
        this.googleProjectId = googleProjectId;
    }

    public String getGoogleAPIKey() {
        return googleAPIKey;
    }

    public void setGoogleAPIKey(String googleAPIKey) {
        this.googleAPIKey = googleAPIKey;
    }

    public String getHubName() {
        return hubName;
    }

    public void setHubName(String hubName) {
        this.hubName = hubName;
    }

    public String getAzureConnectionString() {
        return azureConnectionString;
    }

    public void setAzureConnectionString(String azureConnectionString) {
        this.azureConnectionString = azureConnectionString;
    }

    public String getAzureConnectionStringListen() {
        return azureConnectionStringListen;
    }

    public void setAzureConnectionStringListen(String azureConnectionStringListen) {
        this.azureConnectionStringListen = azureConnectionStringListen;
    }
}