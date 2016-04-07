package com.santao.bullfight.model;


public class DataUser {


    private float rebound;
    private float assist;
    private float scoring;
    private float plays;
    private float avgrebound;
    private float avgassist;
    private float avgscoring;
    private User user;

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

    public float getScoring() {
        return scoring;
    }

    public void setScoring(float scoring) {
        this.scoring = scoring;
    }

    public float getPlays() {
        return plays;
    }

    public void setPlays(float plays) {
        this.plays = plays;
    }

    public float getAvgrebound() {
        return avgrebound;
    }

    public void setAvgrebound(float avgrebound) {
        this.avgrebound = avgrebound;
    }

    public float getAvgassist() {
        return avgassist;
    }

    public void setAvgassist(float avgassist) {
        this.avgassist = avgassist;
    }

    public float getAvgscoring() {
        return avgscoring;
    }

    public void setAvgscoring(float avgscoring) {
        this.avgscoring = avgscoring;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
