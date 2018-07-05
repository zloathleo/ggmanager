package com.xz.managersystem.entity;

import com.xz.managersystem.entity.TZyInfo;
import java.util.ArrayList;
import java.util.List;

public class TYmInfo extends BasicEntity {

    public Integer mbId;

    public List<TZyInfo> videos = new ArrayList<>();

    public List<TZyInfo> imgs = new ArrayList<>();

    public List<TZyInfo> texts = new ArrayList<>();

    public List<TZyInfo> getVideos() {
        return videos;
    }

    public Integer getMbId() {
        return mbId;
    }

    public void setMbId(Integer mbId) {
        this.mbId = mbId;
    }

    public void setVideos(List<TZyInfo> videos) {
        this.videos = videos;
    }

    public List<TZyInfo> getImgs() {
        return imgs;
    }

    public void setImgs(List<TZyInfo> imgs) {
        this.imgs = imgs;
    }

    public List<TZyInfo> getTexts() {
        return texts;
    }

    public void setTexts(List<TZyInfo> texts) {
        this.texts = texts;
    }
}
