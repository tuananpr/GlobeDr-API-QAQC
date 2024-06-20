package com.apis.globedr.model.response.noti;

import java.util.List;


public class ConfigAzurePusherRS {
    private AzureConfig azureConfig;
    private PusherConfig pusherConfig;
    private List<PusherChannelDetails> pusherChannels;

    public ConfigAzurePusherRS(){}
    public ConfigAzurePusherRS(AzureConfig azureConfig, PusherConfig pusherConfig, List<PusherChannelDetails> pusherChannels) {
        this.azureConfig = azureConfig;
        this.pusherConfig = pusherConfig;
        this.pusherChannels = pusherChannels;
    }

    public AzureConfig getAzureConfig() {
        return azureConfig;
    }

    public void setAzureConfig(AzureConfig azureConfig) {
        this.azureConfig = azureConfig;
    }

    public PusherConfig getPusherConfig() {
        return pusherConfig;
    }

    public void setPusherConfig(PusherConfig pusherConfig) {
        this.pusherConfig = pusherConfig;
    }

    public List<PusherChannelDetails> getPusherChannels() {
        return pusherChannels;
    }

    public void setPusherChannels(List<PusherChannelDetails> pusherChannels) {
        this.pusherChannels = pusherChannels;
    }
}
