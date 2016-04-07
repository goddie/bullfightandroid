package com.santao.bullfight.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by goddie on 16/3/14.
 */
public class Article {

    private UUID id;

    /**
     * 栏目 ID
     */
    private ArticleType type;

    /**
     * 副栏目 ID
     */
    private ArticleType subType;

    /**
     * 文档排序
     */
    
    private int sortRank;

    /**
     * 属性
     */
    
    private String flag;

    /**
     * 是否生成 HTML
     */
    
    private int genHTML;

    /**
     * 频道模型
     */
    
    private int channel;

    /**
     * 浏览权限
     */
    
    private int accessRank;

    /**
     * 点击次数
     */
    
    private int visits;

    /**
     * 需要消耗金币
     */
    
    private int money;

    /**
     * 文档标题
     */
    
    private String title;

    /**
     * 短标题
     */
    
    private String shortTitle;

    /**
     * 标题颜色
     */
    
    private String titleColor;

    /**
     * 作者
     */
    
    private String writer;

    /**
     * 来源
     */
    
    private String source;

    /**
     * 缩略图
     */
    
    private String thumb;

    /**
     * 发布日期
     */
    
    private Date pubDate;

    /**
     * 投稿日期
     */
    
    private Date sendDate;


    /**
     * 文档关键词
     */
    
    private String keywords;


    /**
     * 消耗积分
     */
    
    private int scores;

    /**
     * 好评
     */
    
    private int goodPost;

    /**
     * 差评
     */
    
    private int badPost;

    /**
     * 不允许回复
     */
    
    private int notPost;

    /**
     * 描述
     */
    private String description;

    /**
     * 自定义文件名
     */
    
    private String filename;


    /**
     * 自定义类别
     */
    
    private int mType;

    /**
     * 权重
     */
    
    private int weight;


    /**
     * 跳转URL
     */
    
    private String redirectUrl;


    /**
     * 用户IP
     */
    
    private String userIp;

    /**
     * 置顶
     */
    
    private int isTop;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }

    public ArticleType getSubType() {
        return subType;
    }

    public void setSubType(ArticleType subType) {
        this.subType = subType;
    }

    public int getSortRank() {
        return sortRank;
    }

    public void setSortRank(int sortRank) {
        this.sortRank = sortRank;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getGenHTML() {
        return genHTML;
    }

    public void setGenHTML(int genHTML) {
        this.genHTML = genHTML;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getAccessRank() {
        return accessRank;
    }

    public void setAccessRank(int accessRank) {
        this.accessRank = accessRank;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getGoodPost() {
        return goodPost;
    }

    public void setGoodPost(int goodPost) {
        this.goodPost = goodPost;
    }

    public int getBadPost() {
        return badPost;
    }

    public void setBadPost(int badPost) {
        this.badPost = badPost;
    }

    public int getNotPost() {
        return notPost;
    }

    public void setNotPost(int notPost) {
        this.notPost = notPost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }
}
