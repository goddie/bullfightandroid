package com.santao.bullfight.event;

/**
 * Created by goddie on 16/4/1.
 */
public class TeamEvent extends  BaseEvent {
    public  static final String TEAM_DETAIL = "TEAM_DETAIL";
    public  static final String TEAM_SELECTED = "TEAM_SELECTED";

    public TeamEvent(String eventName)
    {
        this.setEventName(eventName);
    }

}
