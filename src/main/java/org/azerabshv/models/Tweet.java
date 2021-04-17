package org.azerabshv.models;

import org.azerabshv.enums.TweetTypeEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tweets")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tweet_id")
    private long tweetId;
    @Column(name = "media_url")
    private String mediaUrl;
    private String content;
    @Enumerated(EnumType.STRING)
    private TweetTypeEnum tweetType;
    @Column(name = "reply_count")
    private String replyCount;
    @Column(name = "reply_to")
    private long replyTo;
    @Column(name = "like_count")
    private String likeCount;
    @Column(name = "retweet_count")
    private String retweetCount;
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

    public Tweet() {
    }

    public Tweet(String mediaUrl, String content, Date createdAt, User user) {
        this.mediaUrl = mediaUrl;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Tweet(String mediaUrl, String content, Date createdAt, User user, Long replyTo) {
        this.mediaUrl = mediaUrl;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.replyTo = replyTo;
    }

    public Tweet(String mediaUrl, String content, TweetTypeEnum tweetType, String replyCount, long replyTo, String likeCount, String retweetCount, Date createdAt) {
        this.mediaUrl = mediaUrl;
        this.content = content;
        this.tweetType = tweetType;
        this.replyCount = replyCount;
        this.replyTo = replyTo;
        this.likeCount = likeCount;
        this.retweetCount = retweetCount;
        this.createdAt = createdAt;
    }


    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
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

    public TweetTypeEnum getTweetType() {
        return tweetType;
    }

    public void setTweetType(TweetTypeEnum tweetType) {
        this.tweetType = tweetType;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public long getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(long relpyTo) {
        this.replyTo = relpyTo;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(String retweetCount) {
        this.retweetCount = retweetCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getRetweetedUsers() {
        return retweetedUsers;
    }

    public void setRetweetedUsers(List<User> retweetedUsers) {
        this.retweetedUsers = retweetedUsers;
    }
}
