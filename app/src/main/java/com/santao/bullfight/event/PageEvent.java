package com.santao.bullfight.event;

/**
 * Created by goddie on 16/4/20.
 */
public class PageEvent extends BaseEvent {

    public  static final String PAGE_CHANGE = "PAGE_CHANGE";

    public  static final String PAGE_SWITCH  = "PAGE_SWITCH";

    public PageEvent(String eventName)
    {
        this.setEventName(eventName);
    }
}
