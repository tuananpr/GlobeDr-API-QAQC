package com.apis.globedr.model.response.noti;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PusherChannelDetails implements Serializable {
    private int type;
    private List<Events> events;
    private String name;

    public PusherChannelDetails(){}
    public PusherChannelDetails(int type, List<Events> events, String name) {
        this.type = type;
        this.events = events;
        this.name = name;
    }

    public List<String> getListEvent(){
        return getEvents().stream().map(e->e.getName()).collect(Collectors.toList());
    }

    public String getListEventAsString(){
        StringBuilder builder = new StringBuilder();
        for (String eventName : getListEvent()) {
            builder.append(eventName);
            builder.append(",");
        }
        // delete the last new line separator
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
