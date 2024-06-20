package com.apis.globedr.pusher;


import com.apis.globedr.helper.Wait;
import com.apis.globedr.model.response.noti.PusherChannelDetails;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.util.HttpAuthorizer;
import com.rest.core.debug.CucumberReport;
import com.rest.core.debug.Logger;
import lombok.*;

import java.util.List;


@Getter
@Setter
public class PusherUtil implements Runnable {
    private List<String> channels;
    private String key;
    private String url;
    private String cluster;
    private Boolean encrypted = false;
    private List<PusherChannelDetails> pusherChannels;
    private ConnectionListener connectionListener;
    private int timeoutInSeconds;
    private SubscriptionListener listener;

    public PusherUtil() {
        listener = new SubscriptionListener();
        connectionListener = new ConnectionListener();
    }



    public void config(String url, String key, String cluster, Boolean encrypted, List<PusherChannelDetails> pusherChannels, int timeoutInSeconds) {
        this.url = url;
        this.key = key;
        this.cluster = cluster;
        this.pusherChannels = pusherChannels;
        this.encrypted = encrypted;
        this.timeoutInSeconds = timeoutInSeconds;
    }


    private PusherOptions getPusherOptions() {
        HttpAuthorizer authorizer;
        PusherOptions options;
        if (getUrl() != null && !getUrl().isEmpty()) {
            authorizer = new HttpAuthorizer(getUrl());
            options = new PusherOptions().setAuthorizer(authorizer);
        } else {
            options = new PusherOptions();
        }
        options.setEncrypted(getEncrypted());
        return options;
    }

    private Pusher getPushter() {
        PusherOptions options = getPusherOptions();
        options.setCluster(getCluster());
        return new Pusher(getKey(), options);
    }


    public void subscribe() {
        Pusher pusher = getPushter();
        pusher.connect(connectionListener, ConnectionState.ALL);
        pusherChannels.forEach(
                c -> {
                    System.out.println("subscribe channel: " + c.getName());
                    Channel channel = pusher.subscribe(c.getName());
                    c.getListEvent().forEach(eventName -> {
                        channel.bind(eventName, listener);
                    });
                }
        );

    }

    public void unsubscribe() {
        Pusher pusher = getPushter();
        pusherChannels.forEach(
                c -> {
                    System.out.println("unsubscribe channel: " + c.getName());
                    pusher.unsubscribe(c.getName());
                }
        );

    }



    @Override
    public void run() {
        subscribe();
    }


}
