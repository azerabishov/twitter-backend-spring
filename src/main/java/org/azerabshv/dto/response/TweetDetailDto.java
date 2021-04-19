package org.azerabshv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TweetDetailDto {
    private String screenName;
    private String username;
    private String avatarUrl;
    private String content;
    private String mediaUrl;
    private Date createdAt;
    private int likeCount;
    private int retweetCount;
    private int quoteCount;
    private int replyCount;
}
