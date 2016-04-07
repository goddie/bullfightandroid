package com.santao.bullfight.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by goddie on 16/3/7.
 */
public class MatchFight implements Serializable {


    private UUID id;

    /**
     * 1 团队 2野战 3联赛
     */
    private int matchType;

    /**
     * 1 未开始 0待应战 2已结束 
     */
    
    private int status;

    /**
     * 开始时间
     */
    
    private long start;

    /**
     * 结束时间
     */
    
    private long end;

    
    private String weather;


    private Team guest;


    private Team host;

    
    private float guestScore;

    
    private float hostScore;

    
    private String guestMember;

    
    private String hostMember;



    /**
     * 1 1on1
     * 2 2on2
     * 3 3on3
     * 4 4on4
     * 5 5on5
     */
    
    private int teamSize;


    private Arena arena;


    /**
     * 裁判人数
     */
    
    private int judge;


    /**
     * 数据员人数
     */
    
    private int dataRecord;

    /**
     * 是否平局
     */
    
    private int draw;

    /**
     * 胜队
     */

    private Team winner;

    /**
     * 败队
     */

    private Team loser;

    /**
     * 是否支付
     */
    
    private int isPay;

    /**
     * 城市
     */
    
    private String city;

    /**
     * 比赛费用
     */
    
    private float fee;

    /**
     * 对战说明
     */

    private String content;


    private League league;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getMatchType() {
        return matchType;
    }

    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Team getGuest() {
        return guest;
    }

    public void setGuest(Team guest) {
        this.guest = guest;
    }

    public Team getHost() {
        return host;
    }

    public void setHost(Team host) {
        this.host = host;
    }

    public float getGuestScore() {
        return guestScore;
    }

    public void setGuestScore(float guestScore) {
        this.guestScore = guestScore;
    }

    public float getHostScore() {
        return hostScore;
    }

    public void setHostScore(float hostScore) {
        this.hostScore = hostScore;
    }

    public String getGuestMember() {
        return guestMember;
    }

    public void setGuestMember(String guestMember) {
        this.guestMember = guestMember;
    }

    public String getHostMember() {
        return hostMember;
    }

    public void setHostMember(String hostMember) {
        this.hostMember = hostMember;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public int getJudge() {
        return judge;
    }

    public void setJudge(int judge) {
        this.judge = judge;
    }

    public int getDataRecord() {
        return dataRecord;
    }

    public void setDataRecord(int dataRecord) {
        this.dataRecord = dataRecord;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public Team getLoser() {
        return loser;
    }

    public void setLoser(Team loser) {
        this.loser = loser;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
