package org.azerabshv.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    private String username;
    @Column(name = "screen_name")
    private String screenName;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String password;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "profile_background_image_url")
    private String profileBackgroundImageUrl;
    @Column(name = "follower_count", columnDefinition = "integer not null default 0")
    private int followerCount;
    @Column(name="following_count", columnDefinition = "integer not null default 0")
    private int followingCount;
    @Column(name = "is_protected", columnDefinition = "boolean not null default false")
    private boolean isProtected;
    private String bio;
    private String location;
    private String website;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Tweet> tweets;
    @ManyToMany(targetEntity = Tweet.class,  cascade = CascadeType.ALL)
    @JoinTable(name = "bookmarks",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "tweet_id", referencedColumnName = "tweet_id",
                            nullable = false, updatable = false)})
    private List<User> bookmarks;
    @ManyToMany(targetEntity = Tweet.class,  cascade = CascadeType.ALL)
    @JoinTable(name = "likes",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "tweet_id", referencedColumnName = "tweet_id",
                            nullable = false, updatable = false)})
    private List<User> likes;
    @ManyToMany(targetEntity = Tweet.class,  cascade = CascadeType.ALL)
    @JoinTable(name = "retweets",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "tweet_id", referencedColumnName = "tweet_id",
                            nullable = false, updatable = false)})
    private List<User> retweets;
    @Column(name = "forgot_password_token")
    private String forgotPasswordToken;
    @Column(name = "forgot_password_token_created_at")
    private Date forgotPasswordTokenCreatedAt ;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;



    public User() {

    }

    public User(String username, String screenName, String email, String phoneNumber, Date birthdate, String password, Date createdAt) {
        this.username = username;
        this.screenName = screenName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.password = password;
        this.createdAt = createdAt;
    }

    public User(String username,  String email, String phoneNumber, String password, String avatarUrl, String screenName, String profileBackgroundImageUrl, int followerCount, int followingCount, boolean isProtected, String bio, String location, String website, Date birthdate, Timestamp createdAt, String forgotPasswordToken, Timestamp forgotPasswordTokenCreatedAt) {
        this.username = username;
        this.screenName = screenName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.isProtected = isProtected;
        this.bio = bio;
        this.location = location;
        this.website = website;
        this.forgotPasswordToken = forgotPasswordToken;
        this.forgotPasswordTokenCreatedAt = forgotPasswordTokenCreatedAt;
        this.birthdate = birthdate;
        this.createdAt = createdAt;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public List<User> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<User> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getForgotPasswordToken() {
        return forgotPasswordToken;
    }

    public void setForgotPasswordToken(String forgotPasswordToken) {
        this.forgotPasswordToken = forgotPasswordToken;
    }

    public Date getForgotPasswordTokenCreatedAt() {
        return forgotPasswordTokenCreatedAt;
    }

    public void setForgotPasswordTokenCreatedAt(Date forgotPasswordTokenCreatedAt) {
        this.forgotPasswordTokenCreatedAt = forgotPasswordTokenCreatedAt;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
