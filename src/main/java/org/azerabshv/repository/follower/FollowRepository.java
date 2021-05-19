package org.azerabshv.repository.follower;

import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query(value = "select * from follows where follows.follower_id = :followerId and follows.following_id = :followingId", nativeQuery = true)
    Optional<Follow> findRecord(long followerId, long followingId);

    @Query(value="select new org.azerabshv.dto.response.UserDetailDto(u.avatarUrl, u.username, u.screenName, u.bio) from Follow f  left join User u on u.userId = f.followerId where f.followerId = :followerId")
    List<UserDetailDto> findAllFriends(long followerId);

    @Query(value = "select new org.azerabshv.dto.response.UserDetailDto(u.avatarUrl, u.username, u.screenName, u.bio) from Follow f  left join User u on u.userId = f.followingId where f.followingId = :followingId")
    List<UserDetailDto> findAllFollowers(long followingId);

    @Query(value="select f.followingId from Follow f  left join User u on u.userId = f.followerId where f.followerId = :userId")
    List<Long> findFriendsIds(long userId);

    @Query(value="select f.followerId from Follow f  left join User u on u.userId = f.followingId where f.followingId = :userId")
    List<Long> findFollowersIds(long userId);

}
