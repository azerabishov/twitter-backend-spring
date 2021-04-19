package org.azerabshv.repository.follower;

import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.models.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    @Query(value = "select * from followers where followers.follower_id = :followerId and followers.following_id = :followingId", nativeQuery = true)
    Optional<Follower> findRecord(long followerId, long followingId);

//    @Query(value="select users.avatar_url, users.username, users.screen_name, users.bio from followers  left join users on users.user_id = followers.follower_id where followers.follower_id = :followerId", nativeQuery=true)
    @Query(value="select new org.azerabshv.dto.response.UserDetailDto(u.avatarUrl, u.username, u.screenName, u.bio) from Follower f  left join User u on u.userId = f.followerId where f.followerId = :followerId")
    List<UserDetailDto> findAllFollowings(long followerId);

    @Query(value = "select new org.azerabshv.dto.response.UserDetailDto(u.avatarUrl, u.username, u.screenName, u.bio) from Follower f  left join User u on u.userId = f.followingId where f.followingId = :followingId")
    List<UserDetailDto> findAllFollowers(long followingId);

}
