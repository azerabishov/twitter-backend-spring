package org.azerabshv.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    private String website;
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
    @Column(name="friend_count", columnDefinition = "integer not null default 0")
    private int friendCount;
    @Column(name = "is_protected", columnDefinition = "boolean not null default false")
    private boolean isProtected;
    private String bio;
    private String location;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Tweet> tweets;
    @ManyToMany(targetEntity = Tweet.class,  cascade = CascadeType.ALL)
    @JoinTable(name = "bookmarks",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "tweet_id", referencedColumnName = "tweet_id",
                            nullable = false, updatable = false)})
    private List<Tweet> bookmarks;
    @ManyToMany(targetEntity = Tweet.class,  cascade = CascadeType.ALL)
    @JoinTable(name = "likes",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "tweet_id", referencedColumnName = "tweet_id",
                            nullable = false, updatable = false)})
    private List<Tweet> likes;
    @ManyToMany(targetEntity = Tweet.class,  cascade = CascadeType.ALL)
    @JoinTable(name = "retweets",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "tweet_id", referencedColumnName = "tweet_id",
                            nullable = false, updatable = false)})
    private List<Tweet> retweets;
    @Column(name = "forgot_password_token")
    private String forgotPasswordToken;
    @Column(name = "forgot_password_token_created_at")
    private Date forgotPasswordTokenCreatedAt ;
    @Column(name = "is_email_verified", columnDefinition = "boolean not null default false")
    private boolean isEmailVerified;
    @Column(name = "email_verification_code")
    private Integer emailVerificationCode;
    @Column(name = "email_verification_code_created_at")
    private Date emailVerificationCodeExpireIn ;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;





    public User(String username, String screenName, String email, String phoneNumber, Date birthdate, String password, Date createdAt) {
        this.username = username;
        this.screenName = screenName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.password = password;
        this.createdAt = createdAt;
    }



}
