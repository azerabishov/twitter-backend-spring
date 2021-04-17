package org.azerabshv.dto.response;

import javax.persistence.Column;
import java.util.Date;

public class TweetDetailDto {
    private String content;
    private String mediaUrl;
    private Date createdAt;
    private int likeCount;
    private int retweetCount;
    private int quoteCount;
    private int replyCount;
}
