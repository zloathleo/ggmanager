package com.xz.managersystem.entity;

public class TZyInfo extends BasicEntity {

    private String content;

    private String link;

    public TZyInfo(String content, String link) {
        this.content = content;
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
