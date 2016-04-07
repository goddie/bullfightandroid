package com.santao.bullfight.model;


import java.util.UUID;

public class LeagueRecord {

    private UUID id;

    /**
     * 队伍
     */
    private Team team;

    /**
     * 赢场
     */
    private int win;

    /**
     * 输场
     */
    private int lose;

    /**
     * 积分
     */
    private int score;


    /**
     * 总得分
     */
    private  int pointSum;


    /**
     * 场均得分
     */
    private int pointAvg;


    /**
     * 场次
     */
    private int plays;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPointSum() {
        return pointSum;
    }

    public void setPointSum(int pointSum) {
        this.pointSum = pointSum;
    }

    public int getPointAvg() {
        return pointAvg;
    }

    public void setPointAvg(int pointAvg) {
        this.pointAvg = pointAvg;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }
}
