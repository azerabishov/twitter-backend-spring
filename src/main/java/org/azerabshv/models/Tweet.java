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

    public Tweet() {
    }

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
        this.replyTo = replyTo;
        this.quoteTo = quoteTo;
    }

    public Tweet(String mediaUrl, String content, TweetTypeEnum tweetType, int replyCount, long replyTo, int likeCount, int retweetCount, int quoteCount, Date createdAt, User user, List<User> users, List<User> likedUsers, List<User> retweetedUsers, Long quoteTo) {
        this.mediaUrl = mediaUrl;
        this.content = content;
        this.tweetType = tweetType;
        this.replyCount = replyCount;
        this.replyTo = replyTo;
        this.likeCount = likeCount;
        this.retweetCount = retweetCount;
        this.quoteCount = quoteCount;
        this.createdAt = createdAt;
        this.user = user;
        this.users = users;
        this.likedUsers = likedUsers;
        this.retweetedUsers = retweetedUsers;
        this.quoteTo = quoteTo;
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

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public Long getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Long replyTo) {
        this.replyTo = replyTo;
    }

    public long getQuoteTo() {
        return quoteTo;
    }

    public void setQuoteTo(long quoteTo) {
        this.quoteTo = quoteTo;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(int quoteCount) {
        this.quoteCount = quoteCount;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public List<User> getRetweetedUsers() {
        return retweetedUsers;
    }

    public void setRetweetedUsers(List<User> retweetedUsers) {
        this.retweetedUsers = retweetedUsers;
    }
}
