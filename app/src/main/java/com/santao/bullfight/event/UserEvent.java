package com.santao.bullfight.event;

/**
 * Created by goddie on 16/4/19.
 */
public class UserEvent extends BaseEvent {
    public  static final String USER_DETAIL = "USER_DETAIL";
    public UserEvent(String eventName)
    {
        this.setEventName(eventName);
    }
}
