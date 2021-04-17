package org.azerabshv.dto.request;

public class CreateTweetRequest {
    private String content;
//    private String mediaUrl;

    public CreateTweetRequest() {
    }

    public CreateTweetRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
