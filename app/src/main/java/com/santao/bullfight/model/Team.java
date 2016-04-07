package com.santao.bullfight.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by goddie on 16/3/7.
 */
public class Team implements Serializable{


    private UUID id;
    
    private String name;


    private String info;

    /**
     * 城市
     */
    
    private String city;

    /**
     * 头像
     */
    
    private String avatar;

    /**
     * 管理员
     */
    private User admin;

    /**
     * 状态
     */
    
    private int status;

    /**
     * 得分
     */
    
    private float scoring;


    /**
     * 场均得分
     */
    
    private float scoringAvg;

    /**
     * 篮板
     */
    
    private float rebound;

    /**
     * 助攻
     */
    
    private float assist;

    /**
     * 盖帽
     */
    
    private float block;

    /**
     * 抢断
     */
    
    private float steal;

    /**
     * 失误
     */
    
    private float turnover;

    /**
     * 胜利
     */
    
    private float win;

    /**
     * 失败
     */
    
    private float lose;

    /**
     * 平局
     */
    
    private float draw;

    /**
     * 比赛场数
     */
    
    private float playCount;

    /**
     * 场均犯规
     */
    
    private float foul;


    /**
     * 投篮命中率
     */
    
    private float goalPercent;

    /**
     * 罚球命中率
     */
    
    private float freeGoalPercent;


    /**
     * 三分命中率
     */
    
    private float threeGoalPercent;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getScoring() {
        return scoring;
    }

    public void setScoring(float scoring) {
        this.scoring = scoring;
    }

    public float getScoringAvg() {
        return scoringAvg;
    }

    public void setScoringAvg(float scoringAvg) {
        this.scoringAvg = scoringAvg;
    }

    public float getRebound() {
        return rebound;
    }

    public void setRebound(float rebound) {
        this.rebound = rebound;
    }

    public float getAssist() {
        return assist;
    }

    public void setAssist(float assist) {
        this.assist = assist;
    }

    public float getBlock() {
        return block;
    }

    public void setBlock(float block) {
        this.block = block;
    }

    public float getSteal() {
        return steal;
    }

    public void setSteal(float steal) {
        this.steal = steal;
    }

    public float getTurnover() {
        return turnover;
    }

    public void setTurnover(float turnover) {
        this.turnover = turnover;
    }

    public float getWin() {
        return win;
    }

    public void setWin(float win) {
        this.win = win;
    }

    public float getLose() {
        return lose;
    }

    public void setLose(float lose) {
        this.lose = lose;
    }

    public float getDraw() {
        return draw;
    }

    public void setDraw(float draw) {
        this.draw = draw;
    }

    public float getPlayCount() {
        return playCount;
    }

    public void setPlayCount(float playCount) {
        this.playCount = playCount;
    }

    public float getFoul() {
        return foul;
    }

    public void setFoul(float foul) {
        this.foul = foul;
    }

    public float getGoalPercent() {
        return goalPercent;
    }

    public void setGoalPercent(float goalPercent) {
        this.goalPercent = goalPercent;
    }

    public float getFreeGoalPercent() {
        return freeGoalPercent;
    }

    public void setFreeGoalPercent(float freeGoalPercent) {
        this.freeGoalPercent = freeGoalPercent;
    }

    public float getThreeGoalPercent() {
        return threeGoalPercent;
    }

    public void setThreeGoalPercent(float threeGoalPercent) {
        this.threeGoalPercent = threeGoalPercent;
    }
}
