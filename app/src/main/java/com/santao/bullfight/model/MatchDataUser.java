package com.santao.bullfight.model;


public class MatchDataUser {


    /**
     * 比赛
     */
    private MatchFight matchFight;
    /**
     * 球员
     */
    private User user;

    /**
     * 代表队伍
     */
    private Team team;

    /**
     * 位置
     */
    private int position;

    /**
     * 得分
     */
    private float scoring;

    /**
     * 命中
     */
    private float goal;

    /**
     * 出手
     */
    private float shot;

    /**
     * 命中率
     */
    private float goalPercent;

    /**
     * 三分出手
     */
    private float threeShot;

    /**
     * 三分命中
     */
    private float threeGoal;

    /**
     * 三分命中率
     */
    private float threeGoalPercent;

    /**
     * 罚球
     */
    private float free;

    /**
     * 罚球命中
     */
    private float freeGoal;

    /**
     * 罚球命中率
     */
    private float freeGoalPercent;

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
     * 在场时间
     */
    private float playTime;

    /**
     * 失误
     */
    private float turnover;

    /**
     * 犯规次数
     */
    private float foul;


    public MatchFight getMatchFight() {
        return matchFight;
    }

    public void setMatchFight(MatchFight matchFight) {
        this.matchFight = matchFight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public float getScoring() {
        return scoring;
    }

    public void setScoring(float scoring) {
        this.scoring = scoring;
    }

    public float getGoal() {
        return goal;
    }

    public void setGoal(float goal) {
        this.goal = goal;
    }

    public float getShot() {
        return shot;
    }

    public void setShot(float shot) {
        this.shot = shot;
    }

    public float getGoalPercent() {
        return goalPercent;
    }

    public void setGoalPercent(float goalPercent) {
        this.goalPercent = goalPercent;
    }

    public float getThreeShot() {
        return threeShot;
    }

    public void setThreeShot(float threeShot) {
        this.threeShot = threeShot;
    }

    public float getThreeGoal() {
        return threeGoal;
    }

    public void setThreeGoal(float threeGoal) {
        this.threeGoal = threeGoal;
    }

    public float getThreeGoalPercent() {
        return threeGoalPercent;
    }

    public void setThreeGoalPercent(float threeGoalPercent) {
        this.threeGoalPercent = threeGoalPercent;
    }

    public float getFree() {
        return free;
    }

    public void setFree(float free) {
        this.free = free;
    }

    public float getFreeGoal() {
        return freeGoal;
    }

    public void setFreeGoal(float freeGoal) {
        this.freeGoal = freeGoal;
    }

    public float getFreeGoalPercent() {
        return freeGoalPercent;
    }

    public void setFreeGoalPercent(float freeGoalPercent) {
        this.freeGoalPercent = freeGoalPercent;
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

    public float getPlayTime() {
        return playTime;
    }

    public void setPlayTime(float playTime) {
        this.playTime = playTime;
    }

    public float getTurnover() {
        return turnover;
    }

    public void setTurnover(float turnover) {
        this.turnover = turnover;
    }

    public float getFoul() {
        return foul;
    }

    public void setFoul(float foul) {
        this.foul = foul;
    }
}
