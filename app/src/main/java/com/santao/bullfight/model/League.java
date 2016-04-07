package com.santao.bullfight.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by goddie on 16/3/7.
 */
public class League implements Serializable {


    private UUID id;

    /**
     * 名称
     */

    private String name;

    /**
     * 状态
     * 0 未开始 1未结束 2已结束
     */

    private int status;

    /**
     * 排序数字
     */

    private int orderNum;

    /**
     * 队伍总数
     */

    private int teamCount;

    /**
     * 创办人
     */

    private String founder;

    /**
     * 开始时间
     */

    private Date start;

    /**
     * 结束时间
     */

    private Date end;


    /**
     * 比赛场馆
     */
    private Arena arena;

    /**
     * 头像
     */

    private String avatar;


    /**
     * 是否公开报名
     */

    private int isOpen;

    /**
     * 报名费
     */

    private float fee;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }
}
