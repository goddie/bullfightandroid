package com.santao.bullfight.model;


import java.util.UUID;

public class Message {

    private UUID id;

    private MatchFight matchFight;

    private String content;

    private String title;

    private User from;

    private User sendTo;

    /**
     * 0 未读
     * 1 已读
     */
    private int status;

    /**
     * 消息类型
     * 1 通知队长
     * 2 邀请入会
     * 3 新闻回复
     * 4 约战回复
     * 5 约战评论
     */
    private int type;

    /**
     * 邀请加入的队伍
     */
    private Team team;

    /**
     * 是否推送
     */
    private int isPush;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * 评论
     */
    private Commet commet;

    private long createdDate;

    public MatchFight getMatchFight() {
        return matchFight;
    }

    public void setMatchFight(MatchFight matchFight) {
        this.matchFight = matchFight;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getSendTo() {
        return sendTo;
    }

    public void setSendTo(User sendTo) {
        this.sendTo = sendTo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getIsPush() {
        return isPush;
    }

    public void setIsPush(int isPush) {
        this.isPush = isPush;
    }

    public Commet getCommet() {
        return commet;
    }

    public void setCommet(Commet commet) {
        this.commet = commet;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
}
