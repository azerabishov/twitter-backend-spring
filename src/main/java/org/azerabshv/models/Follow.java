package org.azerabshv.models;


import javax.persistence.*;

@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "follower_id")
    private long followerId;
    @Column(name = "following_id")
    private long followingId;

    public Follow() {
    }

    public Follow(long followerId, long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }

    public long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(long followingId) {
        this.followingId = followingId;
    }
}
