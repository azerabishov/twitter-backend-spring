package org.azerabshv.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.azerabshv.enums.TweetTypeEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tweets")
@Data
@RequiredArgsConstructor
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tweet_id")
    private long tweetId;
    @Column(name = "media_url")
    private String mediaUrl;
    @Column(name = "content")
    private String content;
    @Enumerated(EnumType.STRING)
    private TweetTypeEnum tweetType;
    @Column(name = "reply_count")
    private int replyCount;
    @Column(name = "reply_to")
    private Long replyTo;
    @Column(name = "quote_to")
    private long quoteTo;
    @Column(name = "like_count")
    private int likeCount;
    @Column(name = "retweet_count")
    private int retweetCount;
    @Column(name = "quote_count")
    private int quoteCount;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(targetEntity = User.class, mappedBy = "bookmarks", cascade = CascadeType.ALL)
    private List<User> users;

    @ManyToMany(targetEntity = User.class, mappedBy = "likes", cascade = CascadeType.ALL)
    private List<User> likedUsers;

    @ManyToMany(targetEntity = User.class, mappedBy = "retweets", cascade = CascadeType.ALL)
    private List<User> retweetedUsers;

    public Tweet(String mediaUrl, String content, Date createdAt, User user) {
        this.mediaUrl = mediaUrl;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Tweet(String mediaUrl, String content, Date createdAt, User user, Long replyTo, Long quoteTo) {
        this.mediaUrl = mediaUrl;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        if (replyTo != null) {
            this.replyTo = replyTo;
        }
        if (quoteTo != null) {
            this.quoteTo = quoteTo;
        }
    }
}
