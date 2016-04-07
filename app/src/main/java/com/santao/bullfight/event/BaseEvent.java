package com.santao.bullfight.event;

/**
 * Created by goddie on 16/4/1.
 */
public class BaseEvent {

    public static final String EVENT_TEAM = "EVENT_TEAM";
    public static final String EVENT_ARENA = "EVENT_ARENA";


    private Object data;
    private String eventName;


    public  BaseEvent()
    {

    }

    public  BaseEvent(Object data)
    {
        this.data = data;
    }

    public  BaseEvent(String eventName,Object data)
    {
        this.data = data;
        this.eventName = eventName;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
