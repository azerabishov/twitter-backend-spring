package org.azerabshv.models;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "follows")
@Data
@RequiredArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "follower_id")
    private long followerId;
    @Column(name = "following_id")
    private long followingId;

    public Follow(long followerId, long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }
}
