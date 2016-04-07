package com.santao.bullfight.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by goddie on 16/3/11.
 */
public class User implements Serializable {


    private UUID id;
    
    private String username;

    private String password;

    
    private String nickname;

    
    private String email;

    
    private String avatar;

    /**
     * 球龄
     */
    
    private int age;

    /**
     * 电话
     */
    
    private String phone;

    /**
     * 微信
     */
    
    private String weiChat;

    /**
     * 微博
     */
    
    private String weibo;

	/*---------------------*/

    
    private float height;

    
    private float weight;

    
    private long birthday;

    
    private String position;
    /**
     * 总得分
     */
    
    private float scoring;

    /**
     * 场均得分
     */
    
    private float scoringAvg;

    /**
     * 命中率
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



    /**
     * 篮板
     */
    
    private float rebound;

    
    private float block;

    
    private float steal;


    /**
     * 助攻
     */
    
    private float assist;

    /**
     * 胜利
     */
    
    private float win;

    /**
     * 平局
     */
    
    private float draw;

    /**
     * 比赛场数
     */
    
    private float playCount;

    /**
     * 失败
     */
    
    private float lose;

    /**
     * 关注他人人数
     */
    
    private int follows;

    /**
     * 自己粉丝数
     */
    
    private int fans;

    
    private String city;


    /**
     * 银行卡号
     * 存银行名称，帐号JSON
     */
    
    private String bankNo;

    /**
     * 支付密码
     */
    
    private String payPassword;

    /**
     * 犯规次数
     */
    
    private float foul;

    /**
     * 失误
     */
    
    private float turnover;

    /**
     * 完成注册
     */
    
    private int finishReg;


    public String getUsername() {
        return username;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeiChat() {
        return weiChat;
    }

    public void setWeiChat(String weiChat) {
        this.weiChat = weiChat;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public float getRebound() {
        return rebound;
    }

    public void setRebound(float rebound) {
        this.rebound = rebound;
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

    public float getAssist() {
        return assist;
    }

    public void setAssist(float assist) {
        this.assist = assist;
    }

    public float getWin() {
        return win;
    }

    public void setWin(float win) {
        this.win = win;
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

    public float getLose() {
        return lose;
    }

    public void setLose(float lose) {
        this.lose = lose;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public float getFoul() {
        return foul;
    }

    public void setFoul(float foul) {
        this.foul = foul;
    }

    public float getTurnover() {
        return turnover;
    }

    public void setTurnover(float turnover) {
        this.turnover = turnover;
    }

    public int getFinishReg() {
        return finishReg;
    }

    public void setFinishReg(int finishReg) {
        this.finishReg = finishReg;
    }
}
