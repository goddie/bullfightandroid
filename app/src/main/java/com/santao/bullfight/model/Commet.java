package com.santao.bullfight.model;


public class Commet {

    private MatchFight matchFight;

    private String content;

    private String title;

    /**
     * 发送方
     */
    private User from;

    /**
     * 接收方
     */
    private User reply;

    private String pic1;

    private String pic2;

    private String pic3;

    private String pic4;

    private String pic5;

    private String pic6;

    /**
     * 新闻
     */
    private Article article;

    private long createdDate;

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public MatchFight getMatchFight() {
        return matchFight;
    }

    public void setMatchFight(MatchFight matchFight) {
        this.matchFight = matchFight;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getReply() {
        return reply;
    }

    public void setReply(User reply) {
        this.reply = reply;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getPic5() {
        return pic5;
    }

    public void setPic5(String pic5) {
        this.pic5 = pic5;
    }

    public String getPic6() {
        return pic6;
    }

    public void setPic6(String pic6) {
        this.pic6 = pic6;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
