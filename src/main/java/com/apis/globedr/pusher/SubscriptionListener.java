package com.apis.globedr.pusher;


import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.rest.core.observers.IObserver;
import com.rest.core.observers.IObserverApi;
import com.rest.core.observers.Subject;

import java.util.LinkedList;

public class SubscriptionListener implements SubscriptionEventListener {


    private LinkedList<com.pusher.client.channel.PusherEvent> pusherEvents = new LinkedList<>();


    public LinkedList<PusherEvent> getPusherEvents() {
        return pusherEvents;
    }

    public void setPusherEvents(LinkedList<PusherEvent> pusherEvents) {
        this.pusherEvents = pusherEvents;
    }

    @Override
    public void onEvent(PusherEvent pusherEvent) {
        System.out.println("Received event with data: " + pusherEvent.toString());
        getPusherEvents().add(pusherEvent);

    }


}
