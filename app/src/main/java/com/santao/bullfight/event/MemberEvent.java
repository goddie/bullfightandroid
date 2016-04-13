package com.santao.bullfight.event;

public class MemberEvent extends BaseEvent {


    public static final String MEMBER_KICK = "MEMBER_KICK";
    public static final String MEMBER_JOIN = "MEMBER_JOIN";
    public static final String MEMBER_CAPTAIN = "MEMBER_CAPTAIN";

    public  MemberEvent(String name)
    {
        this.setEventName(name);
    }

}
