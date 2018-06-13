package com.xz.managersystem.entity;

public class TResource extends BasicEntity {

    private String url;

    private String fileName;

    public TResource() {
    }

    public TResource(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
