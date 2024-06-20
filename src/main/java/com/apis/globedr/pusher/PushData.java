package com.apis.globedr.pusher;

import com.pusher.client.channel.PusherEvent;
import lombok.ToString;

import java.util.LinkedList;

@ToString
public class PushData {
    private LinkedList<com.pusher.client.channel.PusherEvent> pusherEvents = new LinkedList<>();


    public LinkedList<PusherEvent> getPusherEvents() {
        return pusherEvents;
    }

    public void setPusherEvents(LinkedList<PusherEvent> pusherEvents) {
        this.pusherEvents = pusherEvents;
    }
}
