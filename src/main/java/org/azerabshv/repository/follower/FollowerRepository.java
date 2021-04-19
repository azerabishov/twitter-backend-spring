package org.azerabshv.repository.follower;

import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.models.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    @Query("select * from followers f where f.follower_id = ?1 and f.following_id = ?2")
    Optional<Follower> findRecord(long followerId, long followingId);

    @Query("select u.avatar_url, u.username, u.screen_name, u.bio from followers f join users u where f.follower_id = ?1")
    List<UserDetailDto> findAllFollowings(long followerId);

    @Query("select u.avatar_url, u.username, u.screen_name, u.bio from followers f join users u where f.following_id = ?1")
    List<UserDetailDto> findAllFollowers(long followingId);

}
