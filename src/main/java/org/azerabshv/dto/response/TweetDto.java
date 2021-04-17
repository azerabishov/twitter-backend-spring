package org.azerabshv.dto.response;

import org.azerabshv.models.Tweet;

import java.util.List;

import java.util.Date;
public class TweetDto {
    private String mediaUrl;
    private String content;
    private Date createdAt;

    public TweetDto() {
    }

    public TweetDto(String mediaUrl, String content, Date createdAt) {
        this.mediaUrl = mediaUrl;
        this.content = content;
        this.createdAt = createdAt;
    }


    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}