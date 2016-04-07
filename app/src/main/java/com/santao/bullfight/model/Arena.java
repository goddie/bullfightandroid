package com.santao.bullfight.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by goddie on 16/3/7.
 */
public class Arena implements Serializable {


    private UUID id;

    private String name;

    private String address;

    /**
     * 经度
     */
    private double lon;
    /**
     * 纬度
     */
    private double lat;

    private int status;

    private User user;

    /**
     * 单价
     */
    private float price;

    /**
     * 1 团队 2野战
     */
    private int matchType;


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getMatchType() {
        return matchType;
    }

    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }
}
