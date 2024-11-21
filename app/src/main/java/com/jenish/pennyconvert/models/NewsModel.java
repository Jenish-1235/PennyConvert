package com.jenish.pennyconvert.models;

public class NewsModel {

    public String category;
    public Long datetime;
    public String headline;
    public int newsId;
    public String image;
    public String related;
    public String source;
    public String summary;
    public String url;

    public NewsModel(){}

    public NewsModel(String headline, String summary, String source, String urlStr, String image) {
        this.headline = headline;
        this.summary = summary;
        this.source = source;
        this.url = urlStr;
        this.image = image;
    }
}
