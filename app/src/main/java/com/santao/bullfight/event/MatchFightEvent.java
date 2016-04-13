package com.santao.bullfight.event;

/**
 * Created by goddie on 16/4/12.
 */
public class MatchFightEvent extends BaseEvent {

    public  static  final String MATCHE_FILTER = "MATCHE_FILTER";

    public MatchFightEvent(String eventName)
    {
        this.setEventName(eventName);
    }
}
