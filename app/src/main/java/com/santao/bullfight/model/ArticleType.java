package com.santao.bullfight.model;

import java.util.UUID;

/**
 * Created by goddie on 16/3/14.
 */
public class ArticleType {


    private UUID id;

    /**
     * 父级分类
     */
    private ArticleType parent;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private int sortRank;


    /**
     * 说明
     */
    private String description;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ArticleType getParent() {
        return parent;
    }

    public void setParent(ArticleType parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortRank() {
        return sortRank;
    }

    public void setSortRank(int sortRank) {
        this.sortRank = sortRank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
